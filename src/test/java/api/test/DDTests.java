package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DDTests {

    User userpayload;



    @Test(priority=1, dataProvider = "Data", dataProviderClass=DataProviders.class)
    public void testPostUser(String UserID,String UserName,String FName,String LName,String Email,String Password,String Phone) {
        userpayload= new User();

        userpayload.setId(Integer.parseInt(UserID));
        userpayload.setUsername(UserName);
        userpayload.setFirstname(FName);
        userpayload.setLastname(LName);
        userpayload.setEmail(Email);
        userpayload.setPassword(Password);
        userpayload.setPhone(Phone);


        Response response= UserEndPoints.createUser(userpayload);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority=2, dataProvider = "UserNames", dataProviderClass= DataProviders.class)
    public void testGetUserByName(String UserName) {
        Response response= UserEndPoints.readUser(UserName);

        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority=3, dataProvider = "UserNames", dataProviderClass= DataProviders.class)
    public void DeleteUserByName(String UserName){
        Response response = UserEndPoints.deleteUser(UserName);
        Assert.assertEquals(response.getStatusCode(), 200);
    }


}
