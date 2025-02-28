package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenTests {

    @Test(priority = 1,dataProvider = "data", dataProviderClass = DataProviders.class)
    public void createUserTest(String userID,String userName,String firstName,String lastName,String email,	String password, String phone){
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setPhone(phone);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        Response rs= UserEndpoints.createUser(userPayload);
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
    }
    @Test(priority = 2,dataProvider = "Usernames",dataProviderClass = DataProviders.class)
    public void getUserTest(String userName){
        Response rs= UserEndpoints.getUser(userName);
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
        Assert.assertEquals(rs.getBody().jsonPath().getString("username"),userName);
    }

    @Test(priority = 3,dataProvider = "Usernames",dataProviderClass = DataProviders.class)
    public void deleteUserTest(String userName){
        Response rs= UserEndpoints.deleteUser(userName);
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
    }
}
