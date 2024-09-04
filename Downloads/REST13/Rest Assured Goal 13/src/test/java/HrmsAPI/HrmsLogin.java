package HrmsAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Util.LoginData;
import Util.LoginToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class HrmsLogin {
	Response response;
	private int userID;
	String authToken;
	String newpassword;

	   @BeforeClass
	   public void setup() {
	       RestAssured.baseURI = "https://dentalhut.in/api/v1";
	      
	   }

	   @AfterClass
	   public void close()
	   {
	 	  Assert.assertEquals(response.getStatusCode(), 200);
	   }
	   
	   // LOGIN
	   @Test
	   public void Login() throws IOException {
	       String file="{\r\n"
	       		+ "    \"email\": \"shweta.tyagi+1@devstringx.com\",\r\n"
	       		+ "    \"password\": \"Test@132\",\r\n"
	       		+ "    \"userType\": \"\",\r\n"
	       		+ "    \"userTypeToken\": null,\r\n"
	       		+ "    \"loginType\": \"self\",\r\n"
	       		+ "    \"url\": \"srish\",\r\n"
	       		+ "    \"authType\": \"login\"\r\n"
	       		+ "}";
		  
	        response = given()
	               .contentType(ContentType.JSON)
	               .body(file)
	               .when()
	               .post("/login")
	               .then()
	               .body("message", equalTo("Login successfully"))
	               .extract().response();
	        
	       String res = response.getBody().asString();
	       System.out.println("Response of Login : "+res);
	       this.authToken = JsonPath.from(res).get("data.token");
	       
	    }
	   
	   //Forgot Password
	   @Test
	   public void ForgotPassword() throws IOException
	   {
		   String file="{\r\n"
		   		+ "  \"email\": \"shweta.tyagi+1@devstringx.com\",\r\n"
		   		+ "  \"url\": \"srish\"\r\n"
		   		+ "}";
		   
		   response=given()
				   .contentType(ContentType.JSON)
	               .body(file)
	               .when()
	               .post("/forgetPassword")
	               .then()
	               .body("message", equalTo("Check your email for the password reset link."))
	               .extract().response();
		   
		   String res=response.getBody().asString();
		   System.out.println("Response of forgot password : "+res);
	   }
	   
	   private String generateUniquePassword() {
	       String basePassword = "Test" + "@";
	       Random random = new Random();
	       int randomNumber = 100 + random.nextInt(900); // Generate a random number between 100 and 999
	       return basePassword + randomNumber;
	   }
	   
	   //Reset password
	  // @Test(dependsOnMethods="Login")
	   public void ResetPassword()
	   {
		   newpassword=generateUniquePassword();
		   
		   String file="{\r\n"
		   		+ "    \"newPassword\": \""+newpassword+"\",\r\n"
		   		+ "    \"confirmNewPassword\": \""+newpassword+"\"\r\n"
		   		+ "}";
		   
		   response=given()
				   .contentType(ContentType.JSON)
				   .header("Authorization", "Bearer " + authToken)
				   .body(file)
				   .when()
				   .post("/resetPassword")
				   .then()
				   .body("message",equalTo("Password changed successfully"))
				   .extract().response();
		   
		   String res=response.getBody().asString();
		   System.out.println("Response of Reset password : "+res);
	   }      
	   
	   //Logout
	   @Test(dependsOnMethods="Login")
	   public void Logout()
	   {
		   response=given()
				   .contentType(ContentType.JSON)
				   .header("Authorization", "Bearer " + authToken)
				   .when()
				   .get("/logout")
				   .then()
				   .body("message",equalTo("Logout successfully"))
				   .extract().response();
		   
		   String res=response.getBody().asString();
		   System.out.println("Response of logout : "+res);
	   }
}


