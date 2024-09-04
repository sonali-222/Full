package APITestCase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import Util.LoginToken;
import Util.ProfileData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ManageProfile {
    private String token;
    Response response;
    public static String Doj;
	String number;

    @BeforeClass
    public void setup() 
    {
        RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
        LoginToken.testLoginWithPassword();
	    token=LoginToken.authToken;
    }
    
    @AfterClass
    public void close()
    {
    	Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testGetUserProfile() {
        String file=ProfileData.GetUserProfile();
   
         response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getUserProfile")
                .then()  
                .body("message", equalTo("Record found successfully"))
                .extract().response();

        this.Doj = response.jsonPath().getString("data.date_of_joining");
        this.number=response.jsonPath().getString("data.phone_number");
       System.out.println(response.jsonPath().getString("data.date_of_joining"));
        System.out.println("Response body of User Profile : "+response.getBody().asString());
        Assert.assertNotNull(Doj, "Date of Joining should not be null");
    }

    @Test
    public void testUploadProfileImage() throws IOException {
       String filePath=ProfileData.UploadProfileImage();
        File file = Paths.get(filePath).toFile();

         response = given()
                .header("Authorization", "Bearer " + token)
                .multiPart("id", 1)
                .multiPart("file", file, "image/jpeg")
                .when()
                .post("/uploadProfileImage")
                .then()
                .body("message",equalTo("Added successfully"))
                .extract().response();

        JSONObject responseBody = new JSONObject(response.getBody().asString());
        System.out.println("Response body of upload profile image: " + responseBody.toString(4));
    }

    @Test
    public void testRemoveProfileImage() {
        String file=ProfileData.RemoveProfileImage();

         response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/RemoveProfileImage")
                .then()
                .body("message", equalTo("Profile Image removed successfully."))
                .extract().response();

        System.out.println("Response body of remove profile image: " + response.getBody().asString());
    }

    @Test(dependsOnMethods = "testGetUserProfile")
    public void testUpdateUserProfile() throws ParseException {
       String file=ProfileData.UpdateUserProfile();

       //System.out.println("Request JSON: " + file);
       
         response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/updateUserProfile")
                .then()
                .body("message", equalTo("User Updated Successfully"))
                .extract().response();

        System.out.println("Response body of edit user profile: " + response.getBody().asString());
    }

    @Test
    public void testChangePassword() {
        String requestBody=ProfileData.ChangePassword();

         response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/updateUserProfile")
                .then()
                .body("message", equalTo("User Updated Successfully"))
                .extract().response();

        System.out.println("Response body of change password: " + response.getBody().asString());
    }
}
