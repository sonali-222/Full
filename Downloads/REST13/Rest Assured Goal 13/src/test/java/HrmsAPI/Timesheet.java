package HrmsAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Timesheet {
	Response response;
	String authToken;

	   @BeforeClass
	   public void setup() {
	       RestAssured.baseURI = "https://dentalhut.in/api/v1";
	      
	   }

	   //@AfterClass
	   public void close()
	   {
	 	  Assert.assertEquals(response.getStatusCode(), 200);
	   }
	   
	   // LOGIN
	   @Test
	   public void Login() throws IOException {
	       String file="{\r\n"
	       		+ "    \"email\": \"shweta.tyagi+2@devstringx.com\",\r\n"
	       		+ "    \"password\": \"Test@123\",\r\n"
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

	   @Test(dependsOnMethods="Login")
	   public void AddTimesheet() throws IOException {
	       String file="{\r\n"
	       		+ "    \"date\": \"2024-08-29\",\r\n"
	       		+ "    \"projectId\": \"2\",\r\n"
	       		+ "    \"taskId\": \"3\",\r\n"
	       		+ "    \"taskTypeId\": \"7\",\r\n"
	       		+ "    \"hours\": \"5\",\r\n"
	       		+ "    \"minutes\": \"\",\r\n"
	       		+ "    \"description\": \"gfh\"\r\n"
	       		+ "}";
		  
	        response = given()
	               .contentType(ContentType.JSON)
	               .header("Authorization", "Bearer " + authToken)
	               .body(file)
	               .when()
	               .post("/addTaskAndTimeInTimesheet")
	               .then()
	               .body("message", equalTo("Timesheet added successfully"))
	               .extract().response();
	        
	       String res = response.getBody().asString();
	       System.out.println("Response of add timesheet : "+res);
	       
	    }
}
