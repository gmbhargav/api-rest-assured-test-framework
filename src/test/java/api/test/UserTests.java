package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayload;

    @BeforeClass
    public void setup(){
        faker = new Faker();
        userPayload = new User();
//        userPayload.setId(faker.idNumber().hashCode());
//        userPayload.setUsername(faker.name().username());
//        userPayload.setEmail(faker.internet().emailAddress());
//        userPayload.setPassword(faker.internet().password(5,10));
//        userPayload.setFirstName(faker.name().firstName());
//        userPayload.setLastName(faker.name().lastName());
//        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }

    @Test(priority = 1)
    public void createUserTest(){
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        Response rs= UserEndpoints.createUser(userPayload);
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
    }

    @Test(priority = 2)
    public void getUserTest(){
        Response rs= UserEndpoints.getUser(userPayload.getUsername());
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
        Assert.assertEquals(rs.getBody().jsonPath().getString("username"),userPayload.getUsername());
        Assert.assertEquals(rs.getBody().jsonPath().getString("email"),userPayload.getEmail());
    }

    @Test(priority = 3)
    public void setUserPayloadTest(){
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        Response rs = UserEndpoints.updateUser(userPayload.getUsername(),userPayload);
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
        Response rs2= UserEndpoints.getUser(userPayload.getUsername());
        rs2.then().log().all();
        Assert.assertEquals(rs2.getStatusCode(),200);
        Assert.assertEquals(rs2.getBody().jsonPath().getString("firstName"),userPayload.getFirstName());
        Assert.assertEquals(rs2.getBody().jsonPath().getString("lastName"),userPayload.getLastName());


    }
    @Test(priority = 4)
    public void deleteUserTest(){
        Response rs= UserEndpoints.deleteUser(userPayload.getUsername());
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
//        Response rs2= UserEndpoints.getUser(userPayload.getUsername());
//        rs2.then().log().all();
//        Assert.assertEquals(rs2.getStatusCode(),200);
//        Assert.assertEquals(rs2.getBody().jsonPath().getString("username"),userPayload.getUsername());
    }
}
