package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTestsInGWT {


    @Autowired
    User userPayload;

//    public Logger logger;
    Faker faker;

    @BeforeClass
    public void setup(){
        faker = new Faker();
        RestAssured.baseURI =  "https://petstore.swagger.io/v2";
        userPayload = new User();
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
        Response rs= RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userPayload)
                .when()
                .post("/user");
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
    }

    @Test(priority = 2)
    public void getUserTest(){

        Response rs= RestAssured.given()
                .pathParam("username",userPayload.getUsername())
                .when()
                .get("/user/{username}");
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
        Response rs = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username",userPayload.getUsername())
                .body(userPayload)
                .when()
                .put("/user/{username}");

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
        Response rs= RestAssured.given()
                .pathParam("username",userPayload.getUsername())
                .when()
                .delete("/user/{username}");
        rs.then().log().all();
        Assert.assertEquals(rs.getStatusCode(),200);
    }
}
