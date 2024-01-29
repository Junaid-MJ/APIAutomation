package api.test;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests2 {


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
        Response response= UserEndPoints2.createUser(userpayload);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("************ User Created *************");
    }

    @Test(priority=2)
    public void testGetUserByName() {
        logger.info("************ Reading User Info *************");
        Response response= UserEndPoints2.readUser(this.userpayload.getUsername());

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

        Response response= UserEndPoints2.updateUser(this.userpayload.getUsername(),userpayload);

        response.then().log().all();

        //response.then().statusCode(200); //Another way of asserting Status Code(chai Assertion)
        Assert.assertEquals(response.getStatusCode(), 200); //TestNG Assertion


        //Checking Updated Data
        Response updatedDataResponse= UserEndPoints2.readUser(this.userpayload.getUsername());
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
        Response response = UserEndPoints2.deleteUser(this.userpayload.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("************  User Deleted *************");
    }


}
