package api.test;

import api.utilities.DataProviders;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userpayload;
	public Logger logger;


	
	@BeforeClass
	public void setupData(){

		faker = new Faker();
		userpayload= new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());

		///for logs
		logger= LogManager.getLogger(this.getClass());
	}
	
	
	@Test(priority=1)
	public void testPostUser() {
		logger.info("************ Creating User.... *************");
		Response response= UserEndPoints.createUser(userpayload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************ User Created *************");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("************ Reading User Info *************");
		Response response= UserEndPoints.readUser(this.userpayload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************ User Info is displayed *************");
	}

	@Test(priority=3)
	public void testUpdateUserByName() {

		logger.info("************ Updating User.... *************");
		userpayload.setFirstname(faker.name().firstName());
		userpayload.setLastname(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());

		Response response= UserEndPoints.updateUser(this.userpayload.getUsername(),userpayload);

		response.then().log().all();

		//response.then().statusCode(200); //Another way of asserting Status Code(chai Assertion)
		Assert.assertEquals(response.getStatusCode(), 200); //TestNG Assertion


		//Checking Updated Data
		Response updatedDataResponse= UserEndPoints.readUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		updatedDataResponse.then().log().all();
		//Getting specific values from jsonpath to verify data entered is correct

		JsonPath js= updatedDataResponse.jsonPath();
		/*//for Firstname
		String ActualFirstName= js.get("firstName");
		String ExpectedFirstName = this.userpayload.getFirstname();
		Assert.assertEquals(ActualFirstName,ExpectedFirstName);

		//for LastName
		String ActualLastName = js.get("lastName");
		String ExpectedLastName = this.userpayload.getLastname();
		Assert.assertEquals(ActualLastName,ExpectedLastName);*/

		//for email
		String ActualEmail = js.getString("email");
		String ExpectedEmail = this.userpayload.getEmail();
		Assert.assertEquals(ActualEmail,ExpectedEmail);

		logger.info("************ User is Updated *************");

	}

	@Test(priority=3)
	public void DeleteUserByName(){
		logger.info("************ Deleting User.... *************");
		Response response = UserEndPoints.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************  User Deleted *************");
	}


}
