package api.endpoints;

import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

//Create, read, update, delete requests for User API
public class UserEndpoints {

   public static Response createUser(User payload){
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);
        return response;
    }
    public static Response getUser(String username){
       return given()
               .pathParam("username",username)
               .when()
               .get(Routes.get_url);
    }
    public static Response updateUser(String username, User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username",username)
                .body(payload)
                .when()
                .put(Routes.update_url);
    }
    public static Response deleteUser(String username){
       return  given()
               .pathParam("username",username)
               .when()
               .delete(Routes.delete_url);
    }
}
