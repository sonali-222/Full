package Util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Base64;
import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LoginData {
	static String newPassword;
	static int userID;
	static String token;
	
	   @BeforeClass
	   public void setup() {
	       RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
	   }
	   
	   //Login Payload
	   @Test
	   public static String LoginWithPassword() {
	       String file ="{\r\n"
	       		+ "     \"phone_number\": \"9999999999\",\r\n"
	       		+ "     \"password\": \"Test@123\",\r\n"
	       		+ "     \"login_type\": \"password\"\r\n"
	       		+ "}";
	       
	       Response response = given()
	               .contentType(ContentType.JSON)
	               .body(file)
	               .when()
	               .post("/login")
	               .then()
	               .body("message", equalTo("Login successfully"))
	               .extract().response();
	        
	       String res = response.getBody().asString();
	       token = JsonPath.from(res).get("data.token");
	    
	       String[] chunks = token.split("\\.");
	       Base64.Decoder decoder = Base64.getUrlDecoder();
	       String payload = new String(decoder.decode(chunks[1]));
	       userID = JsonPath.from(payload).get("userId");
		return file;
	   }
	   
	   //OTP payload
	   @Test
	   public static String LoginWithOtp()
	   {
		   String file="{\r\n"
		   		+ "    \"phone_number\": \"9999999999\",\r\n"
		   		+ "    \"password\": \"\",\r\n"
		   		+ "    \"login_type\": \"otp\"\r\n"
		   		+ "}";
		   return file;
	   }
	   
	   //Otp verification
	   @Test
	   public static String OtpVerification()
	   {
		   String file="{\r\n"
		   		+ "    \"phone_number\": \"9999999999\",\r\n"
		   		+ "    \"verify_otp\": \"444444\",\r\n"
		   		+ "    \"verification_type\": \"login\"\r\n"
		   		+ "    }";
		   return file;
	   }
	   
	   
	   private static String generateUniquePassword() {
	       String basePassword = "Test" + "@";
	       Random random = new Random();
	       int randomNumber = 100 + random.nextInt(900); // Generate a random number between 100 and 999
	       return basePassword + randomNumber;
	   }
	   
	   //Reset password
	   @Test
	   public static String ResetPassword()
	   {
		 newPassword = generateUniquePassword();
		   
		   String file="{\r\n"
		   		+ "    \"new_password\": \""+newPassword+"\",\r\n"
		   		+ "    \"confirm_password\": \""+newPassword+"\",\r\n"
		   		+ "    \"phone_number\": \"9999999999\"\r\n"
		   		+ "}";
		   return file;
	   }
	   
	   //Check reset password is done
	   @Test(dependsOnMethods="ResetPassword")
	   public static String CheckResetPassword()
	   {
	        String file = "{\r\n"
	                + "     \"phone_number\": \"9999999999\",\r\n"
	                + "     \"password\": \"" + newPassword + "\",\r\n"
	                + "     \"login_type\": \"password\"\r\n"
	                + "}";
		return file;
	   }
	   
	   //UserLogin
	   @Test(dependsOnMethods="LoginWithPassword")
	   public static String UserProfile()
	   {
		   String file=" {\r\n"
		   		+ "		   \"id\":\""+userID+"\"\r\n"
		   		+ "	   }";
		return file;
	   }
	   
	   //Logout
	   @Test
	   public static String Logout()
	   {
		   String file="{\r\n"
		   		+ "    \"users_device_token\":\""+token+"\"\r\n"
		   		+ "}";
		return file;
	   }
}
