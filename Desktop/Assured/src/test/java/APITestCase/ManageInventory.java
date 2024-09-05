package APITestCase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import Util.InventoryData;
import Util.LoginToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ManageInventory {
    String token;
    int productId;
    public static List<Integer> vendorCompanyId = new ArrayList<Integer>();
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
    public void AddInventory() throws IOException {
        Map<String, Object> file=InventoryData.AddProduct();
        
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/addProductInventory")
                .then()
                .body("message", equalTo("Product Added Successfully"))
                .extract().response();

        String res = response.getBody().asString();
        System.out.println("Response body of add product: " + res);
        // Extract productId from response
        this.productId = JsonPath.from(res).get("data.product_id");
    }

    @Test
    public void GetProductDetails() throws IOException {
    	Map<String, Object> file=InventoryData.GetProductDetails();

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getProductDetails")
                .then()
                .body("message", equalTo("Record found successfully"))
                .extract().response();

        String res = response.getBody().asString();
        System.out.println("Response body of vendor data: " + res);
        
        // Extract vendorCompanyId from response
        this.vendorCompanyId = JsonPath.from(res).getList("data.records.id", Integer.class);
        
        // Debugging statement to verify extraction
        System.out.println("Extracted vendorCompanyId: " + this.vendorCompanyId);

        // Check if vendorCompanyId is null or empty
        if (this.vendorCompanyId == null || this.vendorCompanyId.isEmpty()) {
            throw new RuntimeException("vendorCompanyId list is empty or null");
        }
    }

    @Test(dependsOnMethods="GetProductDetails")
    public void GetInventory() throws IOException {
    	Map<String, Object> file=InventoryData.GetInventory();
        
    	response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getProductsInventory")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of product list: " + res);
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    }

    @Test(dependsOnMethods = "AddInventory")
    public void AddProductImage() throws IOException {    	
    	 String[] filePaths = InventoryData.AddProductImage();

    	    if (filePaths.length != 2) {
    	        throw new IllegalArgumentException("Expected two file paths, but got " + filePaths.length);
    	    }

    	    File file = new File(filePaths[0]);
    	    File file1 = new File(filePaths[1]);

    	    // Ensure the files exist
    	    if (!file.exists() || !file1.exists()) {
    	        throw new IllegalArgumentException("File not found: " + filePaths[0] + " or " + filePaths[1]);
    	    }

    	    response = given()
    	            .header("Authorization", "Bearer " + token)
    	            .multiPart("id", productId) // Replace with actual id
    	            .multiPart("file", file, "image/jpeg")
    	            .multiPart("file", file1, "image/jpeg")
    	            .header("Accept", "*/*")
    	            .header("Content-Type", "multipart/form-data")
    	            .when()
    	            .post("/addUploadMultipleImage");

    	    String res = response.getBody().asString();
    	    System.out.println("Response body of add product image: " + res);
    	}
    
    // Delete product
    @Test
    public void DeleteImage()
    {
    	String file=InventoryData.DeleteImage();
    	
    	response=given()
    			.contentType(ContentType.JSON)
    			.header("Authorization","Bearer "+token)
    			.body(file)
    			.when()
    			.post("/deleteProductImage")
    			.andReturn();
    }
    //Update product
    @Test
    public void UpdateProduct() throws IOException {    	
    	 Map<String, Object> file=InventoryData.UpdateProduct();

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/updateProductsInventory")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of updated product: " + res);
    }
}
