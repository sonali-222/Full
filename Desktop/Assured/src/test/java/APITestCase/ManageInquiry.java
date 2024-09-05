package APITestCase;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import Util.InquiryData;
import Util.LoginToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ManageInquiry {
	String token;
	Response response;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
		LoginToken.testLoginWithPassword();
		token = LoginToken.authToken;
	}

	@AfterClass
	public void close() {
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	// Get all inquiry with filters
	@Test
	public void GetVendorInquiry() throws IOException {
		String file = InquiryData.GetVendorInquiry();

		response = RestAssured.given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)
				.body(file).when().post("/getVendorQuotations").andReturn();

		String res = response.getBody().asString();
		System.out.println("Response body of get vendor inquiry :" + res);

		response.then().assertThat().body("message", equalTo("Record found successfully."));

	}

	// Update Inquiry status
	@Test
	public void UpdateInquiryStatus() throws IOException {
		String file = InquiryData.UpdateProductStatus();

		response = RestAssured.given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)
				.body(file).when().post("/updateQuotationProductStatus").andReturn();

		String res = response.getBody().asString();
		System.out.println("Response body of update inquiry status :" + res);

		response.then().assertThat().body("message", equalTo("Updated successfully."));
	}

	// EXPORT
	@Test
	public void ExportInquiry() throws IOException {
		String file = InquiryData.ExportInquiry();

		response = RestAssured.given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)
				.body(file).when().post("/exportVendorQuotations").andReturn();

		String res = response.getBody().asString();
		System.out.println("Response body of export inquiry :" + res);

		response.then().assertThat().body("message", equalTo("Record found successfully."));
	}
}
