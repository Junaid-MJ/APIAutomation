package api.endpoints;

import static io.restassured.RestAssured.*;

import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//User Endpoints.java
// CRUD operations
public class UserEndPoints {



	public static Response createUser(User payload){
		Response response=  given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		  .body(payload)
		.when()
		  .post(Routes.post_url);

		
		return response; 
	}
	
	public static Response readUser(String Username){
		Response response= given().pathParam("username", Username)
                .when()
                .get(Routes.get_url);
		
		return response; 
	}
	
	public static Response updateUser(String Username, User payload){
		Response response=  given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username",Username)
				.body(payload)
				.when()
				  .put(Routes.update_url);
				
				return response; 
	}
	
	public static Response deleteUser(String Username){
		Response response=  given()
				.pathParam("username",Username)
				.when()
				  .delete(Routes.delete_url);
				
				return response; 
	}


}
