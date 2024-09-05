package APITestCase;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import Util.LoginToken;
import Util.RolePermissionData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RolesAndPermissions {
    String token;
   public static int roleId1;
    Response response;
     
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
        LoginToken.testLoginWithPassword();
	    token=LoginToken.authToken;
    }

    @AfterClass
    public void close() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }
     
    @Test
    public void getRoles() throws IOException {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/getRoles")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of get roles: " + res);
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    }
    
    @Test
    public void createRole() throws IOException {
        String file = RolePermissionData.CreateRole();

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/createRole")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of create role: " + res);
        this.roleId1 = JsonPath.from(res).get("data.roleId");
        System.out.println("Role Id: " + roleId1);
        
        int statusCode = response.getStatusCode();
        if (statusCode == 201) {
            response.then().assertThat().body("message", equalTo("Role created successfully"));
        } else if (statusCode == 409) {
            response.then().assertThat().body("message", equalTo("Role name already exists"));
        } else if (statusCode == 403) {
            response.then().assertThat().body("message", equalTo("roleName is not allowed to be empty"));
        }
    }
    
    @Test(dependsOnMethods="createRole")
    public void editRoleName() throws IOException {
        String file = RolePermissionData.EditRoleName();

        System.out.println("Request JSON: " + file);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/editRoleName")
                .andReturn();
    	
        String res = response.getBody().asString();
        System.out.println("Response body of edit role name: " + res); 	
        response.then().assertThat().body("message", equalTo("Updated successfully"));   	
    }
    
    @Test
    public void deleteRole() throws IOException {
        String file=RolePermissionData.DeleteRole();

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/deleteRole")
                .andReturn();
    	
        String res = response.getBody().asString();
        System.out.println("Response body of delete role: " + res);
    	
        if (res.contains("provide valid role id")) {
            Assert.assertEquals(response.getStatusCode(), 401);
            response.then().assertThat().body("message", equalTo("Please provide valid role id"));
        } else if (res.contains("Deleted successfully")) { 
            response.then().assertThat().body("message", equalTo("Deleted successfully"));
        } else if (res.contains("Please Provide Other role")) {
            Assert.assertEquals(response.getStatusCode(), 404);
            response.then().assertThat().body("message", equalTo("Please Provide Other role to update before deleting this Role"));
        }
    }
    
     @Test(dependsOnMethods = "createRole")
    public void getRoleDetail() throws IOException {
        String file=RolePermissionData.GetRoleDetails();
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getRoleDetails")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of get role details: " + res);
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    }
    
    @Test
    public void editRole() throws IOException {
        String file = RolePermissionData.EditRole();

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/editRole")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of edit role: " + res);
        response.then().assertThat().body("message", equalTo("Updated successfully"));
    }
    
    //Role list
    @Test
    public void RoleList() throws IOException {
    	String file=RolePermissionData.RoleList();
    	
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/roleList")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of role list: " + res);
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    }   
}
