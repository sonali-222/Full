package HrmsAPI;

import org.hamcrest.core.Is;
import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.LogStatus;
import com.reports.ExtentTestNGITestListener;

import Util.LoginData;
import Util.LoginToken;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.text.ParseException;

public class Login extends ExtentTestNGITestListener
{
   String authToken;
   static Faker faker = new Faker();
   Response response;
   int userID;
   String userName;
   String nameInCamelCase;
  // static String randomText = faker.lorem().characters(5, true, true);

   @BeforeClass
   public void setup() {
       RestAssured.baseURI = "https://be.eazyagile.com";
   }

   // Test case for login with password
   @Test
   public void Login() throws IOException {
       String file="{\r\n"
       		+ "  \"url\": \"ss1\",\r\n"
       		+ "  \"email\": \"shweta12@gmail.com\",\r\n"
       		+ "  \"password\": \"ss1@1234\",\r\n"
       		+ "  \"gToken\": \"\",\r\n"
       		+ "  \"type\": \"self\"\r\n"
       		+ "}";
	  
        response = given()
               .contentType(ContentType.JSON)
               .body(file)
               .when()
               .post("/login")
               .then()
               .statusCode(200)
               .body("message", equalTo("Loggedin Successfully"))
               .extract().response();

        System.out.println("        ******** PRE LOGIN ********");
        
       String res = response.getBody().asString();
       System.out.println("Response of Login with Password : "+res);
       authToken = JsonPath.from(res).get("token");
       System.out.println(authToken);
       
       String[] chunks = authToken.split("\\.");
       Base64.Decoder decoder = Base64.getUrlDecoder();
       String payload = new String(decoder.decode(chunks[1]));
       String signature=new String(decoder.decode(chunks[2]));
       System.out.println("Payload :"+ payload);
       System.out.println("Signature :"+signature);
       
//       String[] chunks = authToken.split("\\.");
//       Base64.Decoder decoder = Base64.getUrlDecoder();
//       String payload = new String(decoder.decode(chunks[1]));
//       this.userID = JsonPath.from(payload).get("userId");
//       int roleID = JsonPath.from(payload).get("roleId");
//       userName = JsonPath.from(payload).get("firstName");
//       nameInCamelCase = userName.substring(0, 1).toUpperCase() + userName.substring(1);
   }

   // Test case for login with OTP
   @Test(dependsOnMethods="Login")
   public void TestCycle() throws IOException {

	   for (int i = 0; i < 50; i++) {
	        //System.out.println("Execution #" + (i + 1));
	        String randomText = faker.lorem().characters(5, true, true);
	        
       response = given()
               .header("Authorization", "Bearer " + authToken)
               .basePath("/auth/createTestCycle")
               .multiPart("url", "ss1") 
               .multiPart("projectId", "12a4e7944266ef643d948b9e1f6e8578") 
               .multiPart("testcycleTitle", randomText) 
               .multiPart("assignee", "") 
               .multiPart("issueids", "Proj-10") 
               .header("Accept", "*/*")
               .header("Content-Type", "multipart/form-data")
               .when()
               .post();

       System.out.println("Response : "+response.getBody().asString());
	   }
   }
   
   
  // @Test
   public void TestCase() throws IOException {

	   for (int i = 0; i < 1; i++) {
	        //System.out.println("Execution #" + (i + 1));
	        String randomText = faker.lorem().characters(5, true, true);
	        
       response = given()
               .header("Authorization", "Bearer " + authToken)
               .basePath("/auth/createIssue")
               .multiPart("url", "testing06") 
               .multiPart("projectId", "d0280107cd39bde9c0a98a08f60dbc97") 
               .multiPart("template", 2) 
               .multiPart("issueTypeId", 4) 
               .multiPart("issueSummary", randomText) 
               .multiPart("priority", 2) 
               .multiPart("precondition", "g") 
               .multiPart("stepsToExecute", "h") 
               .multiPart("expectedResult", "j") 
               .multiPart("sprintId", 0) 
               .multiPart("issueDescription", "") 
               .multiPart("assignee", 0) 
               .multiPart("assigneeName", 0) 
               .multiPart("issueestimation", "") 
               .multiPart("testSuiteId", 0)
               .multiPart("component", 0)
               .multiPart("tags", "")
//               .multiPart("customFieldJson", "[]")
//               .multiPart("relationJson", "[{\"id\":\"3\",\"name\":\"Blocks\",\"issues\":[]},{\"id\":\"0\",\"name\":\"Child of\",\"issues\":[]},{\"id\":\"4\",\"name\":\"Is blocked by\",\"issues\":[]},{\"id\":\"1\",\"name\":\"Parent of\",\"issues\":[]},{\"id\":\"2\",\"name\":\"Relates to\",\"issues\":[]}]")
//               
               .header("Accept", "*/*")
               .header("Content-Type", "multipart/form-data")
               .when()
               .post();

       System.out.println("Response : "+response.getBody().asString());
       
	   }
   }
}

