package APITestCase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Util.InventoryData;
import Util.LoginToken;
import Util.QuotationData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ManageQuotation {
	String token;
    public static int leadId;
    static Response response;
    static Response response1=QuotationData.response;
    public static int Quotation_Id;
    static List<Integer> ids;
    String res;
    List<Integer> productIds = new ArrayList<>();
    List<Integer> openQuotationIds = new ArrayList<>();
    
    @BeforeClass
    public void setup()
    {
    	RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
    	LoginToken.testLoginWithPassword();
	    token=LoginToken.authToken;
    }
    @AfterClass
    public static void close() {
    	Assert.assertEquals(response.getStatusCode(), 200);
    }
	
	// Add quotation
	@Test
    public void AddQuotation() throws IOException
    {
		Map<String, Object> file=QuotationData.AddQuote();
		
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/createQuotation")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of add quotation : "+res);
        response.then().assertThat().body("message", equalTo("Added successfully"));
        Quotation_Id=JsonPath.from(res).get("data.quotation_id");
        //System.out.println(Quotation_Id);
    } 
	
	//Get Quotations
	@Test
    public void GetQuotation() throws IOException
    {
       String file=QuotationData.GetQuotation("");
       
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getQuotations")
                .then()
                .body("message", equalTo("Record found successfully."))
                .extract().response();

        String res = response.getBody().asString();
        System.out.println("Response body of get quotations :"+res);

    	List<Map<String , Object>> value = JsonPath.from(res).get("data.records");
        //System.out.println(value);
        
        //Getting all ids;
        this.ids = new ArrayList<>();
        for (Map<String, Object> record : value) {
            ids.add((Integer) record.get("id"));
        }

        // Print the list of ids
        System.out.println("Quotation ids: " + ids);
    } 
	
	//Update Quotations
	@Test
    public void UpdateQuotation() throws IOException
    {
        Map<String, Object> file=QuotationData.UpdateQuote();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/updateQuotation")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of update quotation :"+res);
        response.then().assertThat().body("message", equalTo("Updated successfully."));
    } 
	
	//Export
	@Test
    public void ExportQuotation() throws IOException
    {
        String file=QuotationData.ExportQuotation();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/exportQuotations")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of export quotation :"+res);
        response.then().assertThat().body("message", equalTo("Record found successfully"));
    } 
	
	//Delete Quotation
	@Test
    public void DeleteQuotation() throws IOException
    {
        String file= QuotationData.DeleteQuotation();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/deleteQuotation")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of delete quotation :"+res);
       
        if (res.contains("Please provide valid id.")) {
            // Handle case where the quotation is already deleted
            Assert.assertEquals(response.getStatusCode(), 403);
            response.then().assertThat().body("message", equalTo("Please provide valid id."));
        } else {
            response.then().assertThat().body("message", equalTo("Quotation deleted successfully."));
        }
    } 
	
	//Quotation Detail
	@Test
    public void QuotationDetail() throws IOException
    {
       String file=QuotationData.QuotationDetail();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/getQuotations")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of quotation detail :"+res);
        response.then().assertThat().body("message", equalTo("Record found successfully."));
    } 
	
	//Download quotation
	@Test
    public void DownloadQuotation() throws IOException
    {
        String file=QuotationData.DownloadQuotation();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(file)
                .when()
                .post("/downloadQuote")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of download quote :"+res);
        response.then().assertThat().body("message", equalTo("Record found successfully."));
    } 
	
	//Convert to Order
	@Test
    public void ConvertToOrder() throws IOException
    {
		String[] filePaths = QuotationData.ConvertToOrder();

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
                .basePath("/orderDispatched")
                .multiPart("id", 57) 
                .multiPart("comment", "New comment") 
                .multiPart("links", "abc.com") 
                .multiPart("proof", file, "image/png") 
                .multiPart("delivery_type", "Pick Up") 
                .multiPart("delivery_date",2024-06-13)
                .multiPart("sales_person",50)
                .multiPart("direct_ready_for_pickup",true)
                .multiPart("quote_order_amount",450)
                .multiPart("customer_phone_number","2345678910")
                .multiPart("sale_order_no",10056)
               .multiPart("instructions", file1, "image/png")
                .multiPart("order_type","shortage_order")
                .header("Accept", "*/*")
                .header("Content-Type", "multipart/form-data")
                .when()
                .post();

        String res = response.getBody().asString();
        System.out.println("Response body of convert to order :"+res);
        response.then().assertThat().body("message", equalTo("Order converted successfully"));
    } 
   
   //ADD LEAD
    @Test
    public void AddLead() throws IOException
    {   
 	   Map<String, Object> data=QuotationData.AddLead();
        
    	response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Pass token in the header
                .body(data)
                .when()
                .post("/createLead")
                .andReturn();
        String exp = response.getBody().asString();
        System.out.println("Response body of add lead : " + exp);
        this.leadId = JsonPath.from(exp).get("data.lead_id");
        System.out.println("Added lead Id : " + leadId);
    }
     
    
    
    private static String generateSixDigitPincode() {
        return faker.number().digits(6); // Ensure it generates a 6-digit pincode
    } 
    static Faker faker=new Faker();
    String Address1 = faker.address().streetAddress();
    String Address2 = faker.address().streetAddress();
    
    @Test(dependsOnMethods="AddLead")
    public void AddBillingAddress() throws IOException
    {
    	Response getResponse = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/getBillingAddress/" + leadId)
                .andReturn();

    	//System.out.println(leadId);
        
    	if (getResponse.getStatusCode() == 403 && getResponse.getBody().asString().contains("Please add billing address")) { //No address found
        	String pinCode = generateSixDigitPincode();
            
            Map<String, Object> addressPayload = new HashMap<>();
            addressPayload.put("lead_id", leadId);
            addressPayload.put("address_line_1", Address1);
            addressPayload.put("address_line_2", Address2);
            addressPayload.put("gst_number", "");
            addressPayload.put("state_id", 1);
            addressPayload.put("city_id", 5);
            addressPayload.put("pincode", pinCode);
            
            Response postResponse = given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(addressPayload)
                    .when()
                    .post("/addBillingAddress")
                    .andReturn();

            String res = postResponse.getBody().asString();
            System.out.println("Response body of add billing address: " + res);
            Assert.assertEquals(postResponse.getStatusCode(), 200);
            postResponse.then().assertThat().body("message", equalTo("Address added successfully."));
        } else {
            System.out.println("Address already added to this lead");
        }
    }  
    
	//Update billing address
	@Test
    public void UpdateBillingAddress() throws IOException
    {
		Map<String, Object> data=QuotationData.UpdateBillingAddress();
		// System.out.println(data);
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(data)
                .when()
                .post("/updateBillingAddress")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of update billing address :"+res);
        response.then().assertThat().body("message", equalTo("Address updated successfully."));
    } 
	
	//Add Shipping Address
	@Test
    public void AddShippingAddress() throws IOException
    {
		Map<String, Object> payload=QuotationData.AddShippingAddress();
        
         response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(payload)
                .when()
                .post("/addMultipleShippingAddresses")
                .andReturn();

        String res = response.getBody().asString();
        System.out.println("Response body of add shipping address :"+res);
        response.then().assertThat().body("message", equalTo("Address added successfully."));
    }
	
	//Update Shipping address
	@Test
    public void UpdateShippingAddress() throws IOException
    {
		Map<String, Object> payload=QuotationData.UpdateShippingAddress();

	        // Send the POST request and get the response
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(payload)
	                .when()
	                .post("/addMultipleShippingAddresses")
	                .andReturn();

	        // Print the response body
	        String res = response.getBody().asString();
	        System.out.println("Response body of update shipping address: " + res);
        response.then().assertThat().body("message", equalTo("Address added successfully."));
    }   
	
	//RECHECK STOCK
	@Test(dependsOnMethods="GetQuotation")
	public void RecheckStock() throws IOException {
		String file = QuotationData.RecheckStock();

	    String res1 = response.getBody().asString();

	    for (int i = 0; i < ids.size(); i++) {
	        response = given()
	            .contentType(ContentType.JSON)
	            .header("Authorization", "Bearer " + token)
	            .body(QuotationData.GetQuotation(ids.get(i).toString()))
	            .when()
	            .post("/getQuotations")
	            .andReturn();

	        String res = response.getBody().asString();
	        Integer id = ids.get(i);
	        
	        // Parse the response using JsonPath
	        String quotationStatus = JsonPath.from(res).getString("data.records.quotation_status");
	        if ("Open".equalsIgnoreCase(quotationStatus)) {
	            Integer quotationId = JsonPath.from(res).getInt("data.records.id");
	            List<Integer> productIds = JsonPath.from(res).getList("data.records.products_items.id");

	            System.out.println("Quotation ID: " + quotationId);
	            System.out.println("Product IDs: " + productIds);
	        } else {
	            //System.out.println("Quotation status is not Open for ID: " + id);
	        }
	    }
	       
//	        if (value.equalsIgnoreCase("Open")) {
//	            openQuotationIds.add(id);
//	            //System.out.println("Quotation ID with status 'Open': " + openQuotationIds);
//	        }
//	        else {
//	        	//System.out.println("Quotation ID with other status : " + id);
//	        }
//	    }
//	    
//	    System.out.println("Open quotation Ids : "+openQuotationIds);
//	    
//	  //fetching product ids
//        for (int j = 0; j < openQuotationIds.size(); j++) {
//        	response = given()
//    	            .contentType(ContentType.JSON)
//    	            .header("Authorization", "Bearer " + token)
//    	            .body(QuotationData.GetQuotation(openQuotationIds.get(j).toString()))
//    	            .when()
//    	            .post("/getQuotations")
//    	            .andReturn();
//
//    	        String res = response.getBody().asString();
//    	        Integer id = ids.get(j);
//    	        
//    	        JsonPath jsonPath = JsonPath.from(res);
//    	        List<Map<String, Object>> productItems = jsonPath.getList("data.records.products_items");
//
//    	        // Iterate over the product items and extract the ids
//    	        for (Map<String, Object> item : productItems) {
//    	            Integer id1 = (Integer) item.get("id");
//    	            productIds.add(id1);
//    	        }    	    
//       	        }
//        
//        System.out.println("Product ids: " + productIds);  
        
//        String payload = "{\r\n"
//                + "    \"quotation_id\":\""+openQuotationIds.get(0)+"\",\r\n"
//                + "    \"id\":[\""+productIds.get(0)+"\"]\r\n"
//                + "}\r\n"
//                + "";
//                
//                System.out.println("ABBBB : "+payload);
//                
//                 response = RestAssured.given()
//                        .contentType(ContentType.JSON)
//                        .header("Authorization", "Bearer " + token)
//                        .body(file)
//                        .when()
//                        .post("/reStockCheck")
//                        .then()
//                        .body("message", equalTo("Quotation status updated successfully."))
//                        .extract().response();
//
//                String res2 = response.getBody().asString();
//                System.out.println("Response body of recheck stock :"+res2);
	} 	    
}
