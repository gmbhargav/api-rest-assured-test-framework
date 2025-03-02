package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.ResourceBundle;
import static io.restassured.RestAssured.given;

// Reading Urls from property file
// Create, read, update, delete requests for User API
public class UserEndpointsFromProperties {
     static ResourceBundle getURL(){
        return ResourceBundle.getBundle("routes");
    }


   public static Response createUser(User payload){
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(getURL().getString("post_url"));
        return response;
    }
    public static Response getUser(String username){
       return given()
               .pathParam("username",username)
               .when()
               .get(getURL().getString("get_url"));
    }
    public static Response updateUser(String username, User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username",username)
                .body(payload)
                .when()
                .put(getURL().getString("update_url"));
    }
    public static Response deleteUser(String username){
       return  given()
               .pathParam("username",username)
               .when()
               .delete(getURL().getString("delete_url"));
    }
}
