package APITestCase;

import java.io.File;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import com.github.javafaker.Faker;

import APITestCase.Login;
import Util.LoginToken;
import Util.UserData;

public class ManageUser {
	String token;
	 int userId;
	 int id;
	 int getUserId;
	 List<Integer>  roleID;
	 String branchName;
	 Iterator<Integer> iterator;
	 private String payload;
	 Response response;
	 
	 @BeforeClass
	   public void setup() {
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
   public void AddUser()  {
    	 String payload=UserData.AddUser();
       
        response = RestAssured.given()
               .contentType(ContentType.JSON)
               .header("Authorization", "Bearer " + token) // Pass token in the header
               .body(payload)
               .when()
               .post("/createUser")
               .then()
               .body("message", equalTo("User Added Successfully"))
               .extract().response();
       
       String res1 = response.getBody().asString();
       System.out.println("Response body of Add User : " + res1);

       // Extract user_id from response
       this.userId = JsonPath.from(res1).get("data.user_id");
       //System.out.println("After adding user id :" + userId);
   }

   //UPDATE USER
   @Test
   public void UpdateUser() throws IOException {       
       String payload=UserData.UpdateUser();

        response = RestAssured.given()
               .contentType(ContentType.JSON)
               .header("Authorization", "Bearer " + token) // Pass token in the header
               .body(payload)
               .when()
               .post("/updateUser")
               .then()
               .body("message", equalTo("User Updated Successfully"))
               .extract().response();

       String exp = response.getBody().asString();
       System.out.println("Response body of Update User: " + exp);     
   }
   
   @Test
   public void GetUser() throws IOException {
       String file=UserData.GetUser();
       
       response = RestAssured.given()
               .contentType(ContentType.JSON)
               .header("Authorization", "Bearer " + token) // Pass token in the header
               .body(file)
               .when()
               .post("/getUsers")
               .andReturn();

       String responseBody = response.getBody().asString();
       System.out.println("Response body of get User: " + responseBody);

       JsonPath jsonPath = response.jsonPath();
       
          List<Integer> ids = jsonPath.getList("data.records.id");
          List<String> Name= jsonPath.getList("data.records.first_name");
          Assert.assertTrue(Name!=null);
          Assert.assertNotNull("user_status");
}

   //User count
	@Test
    public void UsersCount() throws IOException
    {
        String file=UserData.UserCount();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(file)
                .when()
                .post("/getUsers")
                .andReturn();

        String exp = response.getBody().asString();
        int count = JsonPath.from(exp).get("data.count");
        System.out.println("Total Users : "+exp);
        
        String contentType = response.getContentType();
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    }
   
    // UPLOAD IMAGE
	@Test
    public void UploadUserProfileImage() throws IOException {
        String filePath = UserData.UploadProfileImage();
        File file = Paths.get(filePath).toFile();
        byte[] fileContent = FileUtils.readFileToByteArray(file);

        // Construct the multipart form data request
         response = given()
                .basePath("/uploadProfileImage")
                .multiPart("id", 369) // Replace with actual id
                .multiPart("file", file, "image/jpeg")
                .header("Accept", "*/*")
                .header("Content-Type", "multipart/form-data")
                .when()
                .post()
                .andReturn();

        // Parse the response body as JSON
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        System.out.println("Response body of upload user profile : " + responseBody.toString(4));
        
        response.then().assertThat().body("message", equalTo("Added successfully"));
    }
   
    //EXPORT USERS
    @Test
    public void ExportUser() throws IOException
    {
    	 String file=UserData.ExportUser();
    	 
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(file)
                .when()
                .post("/exportUsers")
                .then()
                .body("message", equalTo("Record found successfully"))
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of Export User : " + exp);
    }
    
    //ROLE LIST
    @Test
    public void RoleList() throws IOException
    {
    	String file=UserData.RoleList();
    	
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(file)
                .when()
                .post("/roleList")
                .then()
                .body("message", equalTo("Record found successfully"))
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of role list : " + exp);        
        JsonPath jsonPath = response.jsonPath();
        // Fetch Role IDs
           this.roleID = jsonPath.getList("data.records.roleId");    
    }
    
    //RESET PASSWORD
    @Test
    public void ResetPassword() throws IOException
    {
        String file=UserData.ResetPassword();
       
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(file)
                .when()
                .post("/resetPassword")
                .then()
                .body("message", equalTo("Password changed successfully"))
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of reset password : " + exp);
    }
    
    //RESEND PASSWORD
    @Test
    public void ResendPassword() throws IOException
    {
        String file=UserData.ResendPassword();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(file)
                .when()
                .post("/resendPassword")
                .then()
                .body("message", equalTo("Password sent successfully"))
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of Resend password : " + exp);
    }
    
    //UPDATE USER STATUS
    @Test
    public void UpdateUserStatus() throws IOException
    {
    	UserData.UpdateUserStatus();
    	String UserStatus=UserData.selectedStatus;
        String payload=UserData.UpdateUserStatus();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(payload)
                .when()
                .post("/updateStatus")
                .then()
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of Update User Status : " + exp);
        
     // Conditional validation based on the selected status
        if (UserStatus.equals("active")) {
            response.then().assertThat().body("message", equalTo("Activated Successfully"));
        } else if (UserStatus.equals("inactive")) {
            response.then().assertThat().body("message", equalTo("Deactivated Successfully"));
        } else if (UserStatus.equals("deactivated")) {
            response.then().assertThat().body("message", equalTo("Deleted Successfully"));
        }else {
            Assert.fail("Unknown status: " + UserStatus);
        }
    }
    
    //GET BRANCH NAME
    @Test
    public void GetBranches() throws IOException
    {
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) 
                .when()
                .post("/getBranchName")
                .then()
        		.body("message", equalTo("Record found successfully"))
        		.extract().response();
        
        String res = response.getBody().asString();
        System.out.println("Response body of branches : " + res);
        
        JsonPath jsonPath = response.jsonPath();
        
        // Fetch the list of IDs
        List<Integer> ids = jsonPath.getList("data.id");
    }     
    
    //GET VENDOR COMPANY NAMES
    @Test
    public void GetVendorCompany() throws IOException
    {     
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .when()
                .post("/getVendorCompanyNames")
                .then()
                .body("message", equalTo("Record found successfully"))
                .extract().response();
        
        String exp = response.getBody().asString();
        System.out.println("Response body of Vendor company : " + exp);

        JsonPath jsonPath = response.jsonPath();
        
        // Fetch the list of IDs
           List<Integer> ids = jsonPath.getList("data.vendor_company_admin_id");
           // Fetch vendor company names
           List<String> vendor = jsonPath.getList("data.vendor_company_name");
           }
    }