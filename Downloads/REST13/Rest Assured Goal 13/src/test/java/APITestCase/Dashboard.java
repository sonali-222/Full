package APITestCase;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Util.DashboardData;
import Util.LoginToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Dashboard {
	String token;
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
	 
	 //GET DASHBOARD DATA
	 @Test
	    public void GetDashboardData() throws IOException {
	        String file=DashboardData.GetDashboardData();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/getDashboardData")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of get dashboard data :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Get Quotation Analytics
	 @Test
	    public void GetQuotationAnalytics() throws IOException {
		 String file=DashboardData.GetQuotationAnalytics();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/getQuotationAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of get quotation analytics :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully."));
	    }
	 
	 //Export Quotation Analytics
	 @Test
	    public void ExportQuotationAnalytics() throws IOException {
	       String file=DashboardData.ExportQuotationAnalytics();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportQuotationAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export quotation analytics :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export quotation sales executive 
	 @Test
	    public void ExportSalesExecutiveQuotationAnalytics() throws IOException {
	        String file=DashboardData.ExportSalesExecutiveQuotationAnalytics();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportSalesExecutiveAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export sales executive quotation analytics :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export not yet converted quotes QUOTATION
	 @Test
	    public void ExportNotYetConvertedQuotation() throws IOException {
	        String file=DashboardData.ExportNotYetConvertedQuotes();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportNotConvertedSalesExecutiveAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export not yet converted quotation :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Lead Analytics Report
	 @Test
	    public void GetLeadAnalaytics() throws IOException {
	        String file=DashboardData.GetLeadAnalytics();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/getLeadAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of lead analytics :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully."));
	    }
	 
	 //Get overdue task  lead analytics
	 @Test
	    public void GetOverDueTask() throws IOException {
		 String file=DashboardData.GetOverdueTask();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/getOverDueTask")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of leads overdue tasks :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully."));
	    }
	 
	 //Lead Conversion
	 @Test
	    public void LeadConversionReport() throws IOException {
	       String file=DashboardData.LeadConversionReport();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/leadConversation")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of lead conversion report :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export lead generation by sources
	 @Test
	    public void ExportLeadGenerationSources() throws IOException {
	       String file=DashboardData.ExportLeadGeneration();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportLeadGenerationBySources")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export lead generation by sources :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export Lead overdue tasks
	 @Test
	    public void ExportLeadOverDueTask() throws IOException {
		 String file=DashboardData.ExportLeadOverdueTask();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportOverdueTasks")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export lead overdue tasks :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export lead conversion report
	 @Test
	    public void ExportLeadConversionReport() throws IOException {
	      String file=DashboardData.ExportLeadConversionReport();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportLeadConversionReport")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export lead conversion :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	
	 //Stock Check Analytics
	 @Test
	    public void StockCheckAnalytics() throws IOException {
		 String file=DashboardData.StockCheckAnalytics();
		 
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/stockCheckAnalytics")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of stock check analytics :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export get Vendor quick response
	 @Test
	    public void ExportVendorQuickResponse() throws IOException {
		 String file=DashboardData.ExportVendorQuickResponse();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportVendorQuickResponses")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export vendor quick response :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export vendor poor response
	 @Test
	    public void ExportVendorPoorResponse() throws IOException {
		 String file=DashboardData.ExportVendorPoorResponse();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportVendorPoorResponses")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export vendor poor response :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
	 
	 //Export vendor strike rate
	 @Test
	    public void ExportVendorStrikeRate() throws IOException {
		String file=DashboardData.ExportVendorStrikeRate();
	        
	         response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .header("Authorization", "Bearer " + token)
	                .body(file)
	                .when()
	                .post("/exportVendorStrikeRate")
	                .andReturn();

	        String res = response.getBody().asString();
	        System.out.println("Response body of export vendor strike rate :"+res);
	        response.then().assertThat().body("message", equalTo("Record found successfully"));
	    }
}
