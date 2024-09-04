package Util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import APITestCase.ManageQuotation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class QuotationData {
	
	static Faker faker=new Faker();
	static String randomName = faker.name().fullName();
	static String randomPhoneNumber = generateRandomIndianPhoneNumber();
    static String email=faker.internet().emailAddress();
    static  String secondaryPhone=generateRandomIndianPhoneNumber();
    static String randomText = faker.lorem().characters(15, true, true);
    static String Address1 = faker.address().streetAddress();
    static String Address2 = faker.address().streetAddress();
    static String token;
    static int leadId;
    public static Response response;
    
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}
	
	@Test
	   public void LoginWithPassword() throws IOException {
	       String file=LoginData.LoginWithPassword();
		  
	        Response response = given()
	               .contentType(ContentType.JSON)
	               .body(file)
	               .when()
	               .post("/login")
	               .then()
	               .body("message", equalTo("Login successfully"))
	               .extract().response();

	        System.out.println("        ******** PRE LOGIN ********");
	        
	       String res = response.getBody().asString();
	       System.out.println("Response of Login with Password : "+res);
	       token = JsonPath.from(res).get("data.token");
	       
	}
	
	@Test
	public static Map<String, Object> AddQuote()
	{		
		Map<String, Object> product_items=new HashMap<>();
		product_items.put("product_id", 31);
		product_items.put("description", "");
		product_items.put("hsn_code", 25609);
		product_items.put("quantity",13 );
		product_items.put("gst_rate", 18);
		product_items.put("discount", 15);
		product_items.put("unit_of_measurement", "Piece");
		product_items.put("price", 2000);
		product_items.put("total_amount", 26000);
		
		List<Map<String, Object>> product_item = new ArrayList<>();
	    product_item.add(product_items);
	            
		Map<String, Object> file=new HashMap<>();
		file.put("customer_id", 22);
        file.put("any_other_reference", "");
        file.put("billing_address", "");
        file.put("po_number", "");
        file.put("quotation_type", "");
        file.put("valid_until", "");
        file.put("quote_date", "2024-05-14");
        file.put("sales_executive_user", 5);
        file.put("transportation_charges", "");
        file.put("unloading_charges", "");
        file.put("products_items", product_item);
        file.put("terms_conditions", "");
        file.put("total_taxable", 22100);
        file.put("final_taxable", 2222);
        file.put("cgst", 1989);
        file.put("sgst", 1989);
        file.put("igst", 0);
        file.put("final_total", 26078);
        file.put("total_discount", "");
        file.put("is_transportation", false);
        file.put("is_unloading_charges", false);
        file.put("is_discount_charges", false);
        file.put("total_discount_val", 0);
        file.put("unloading_charges_val", 0);
        file.put("transportation_charges_val", 0);
        file.put("bank_details", "Bank Name:- Kotak Mahindra Bank, IFSC:- KKBK001007, Branch:- Meera Bagh");
        file.put("stock_check_type", "Draft");
        
        file.put("state", "KARNATAKA");
        file.put("billing_address_line_1", "aaq 999999");
        file.put("billing_address_line_2", "ss qwdefrgthyjukiloqwdefrgthyjukilo");
        file.put("billing_address_pincode", "879898");
        file.put("billing_city_name", "Davanagere");
        file.put("billing_gst_number", "");
        file.put("billing_address_phone", "7654444444/");
        file.put("billing_state_name", "KARNATAKA");
        
        file.put("shipping_address_line_1", "aa 00000000000");
        file.put("shipping_address_line_2", "ss");
        file.put("shipping_address_pincode", "879898");
        file.put("shipping_gst_number", "");
        file.put("shipping_state_name", "DELHI");
        file.put("shipping_city_name", "Central Delhi");
        file.put("shipping_address_phone", "7654444444");
        
		return file;
	}
	
	@Test
	public static String GetQuotation(String id)
	{
		 String file="{\r\n"
	        		+ "    \"pageNumber\": 1,\r\n"
	        		+ "    \"pageSize\": 10,\r\n"
	        		+ "    \"search\": \"\",\r\n"
	        		+"     \"id\": \""+id+"\",\r\n"
	        		+ "    \"sort\": \"\"\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	@Test
	public static String ExportQuotation()
	{
		String file ="{\r\n"
        		+ "    \"search\": \"\"\r\n"
        		+ "}";
		
		return file;
	}
	
	@Test
	public static String DeleteQuotation()
	{
		String file ="{\r\n"
        		+ "    \"id\": 67\r\n"
        		+ "}";
		
		return file;
	}
	
	@Test
	public static String QuotationDetail()
	{
		 String file ="{\r\n"
	        		+ "	    \"pageNumber\": 1,\r\n"
	        		+ "	    \"pageSize\": \"\",\r\n"
	        		+ "	    \"id\": \"75\"\r\n"
	        		+ "	}";
		 
		 return file;
	}
	
	@Test
	public static String DownloadQuotation()
	{
		String file ="{\r\n"
        		+ "    \"quotation_id\": \"75\"\r\n"
        		+ "}";
		
		return file;
	}
	
	@Test
	public static String[] ConvertToOrder()
	{
		 return new String[] {"C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageQuotation\\Image.png","C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageQuotation\\Screenshot (26).png"};
	}
	
	@Test
	public static Map<String, Object> UpdateQuote()
	{
		Map<String, Object> product_items=new HashMap<>();
		product_items.put("id", 107);
		product_items.put("product_id", 31);
		product_items.put("description", "");
		product_items.put("hsn_code", 25609);
		product_items.put("quantity",13 );
		product_items.put("gst_rate", 18);
		product_items.put("discount", 15);
		product_items.put("unit_of_measurement", "Piece");
		product_items.put("price", 2000);
		product_items.put("total_amount", 22100);
		
		List<Map<String, Object>> product_item = new ArrayList<>();
	    product_item.add(product_items);
	            
		Map<String, Object> file=new HashMap<>();
		file.put("id", 73);
		file.put("customer_id", 22);
        file.put("any_other_reference", "");
        file.put("billing_address", "");
        file.put("po_number", "");
        file.put("quotation_type", "");
        file.put("valid_until", "");
        file.put("quote_date", "2024-05-14");
        file.put("sales_executive_user", 5);
        file.put("transportation_charges", "");
        file.put("unloading_charges", "");
        file.put("products_items", product_item);
        file.put("terms_conditions", "");
        file.put("total_taxable", 22100);
        file.put("final_taxable", 2222);
        file.put("cgst", 1989);
        file.put("sgst", 1989);
        file.put("igst", 0);
        file.put("final_total", 26078);
        file.put("total_discount", "");
        file.put("is_transportation", false);
        file.put("is_unloading_charges", false);
        file.put("is_discount_charges", false);
        file.put("total_discount_val", 0);
        file.put("unloading_charges_val", 0);
        file.put("transportation_charges_val", 0);
        file.put("bank_details", "Bank Name:- Kotak Mahindra Bank, IFSC:- KKBK001007, Branch:- Meera Bagh");
        file.put("stock_check_type", "Stock Check");
        
        file.put("state", "KARNATAKA");
        file.put("billing_address_line_1", "aaq 999999");
        file.put("billing_address_line_2", "ss qwdefrgthyjukiloqwdefrgthyjukilo");
        file.put("billing_address_pincode", "879898");
        file.put("billing_city_name", "Davanagere");
        file.put("billing_gst_number", "");
        file.put("billing_address_phone", "7654444444/");
        file.put("billing_state_name", "KARNATAKA");
        
        file.put("shipping_address_line_1", "aa 00000000000");
        file.put("shipping_address_line_2", "ss");
        file.put("shipping_address_pincode", "879898");
        file.put("shipping_gst_number", "");
        file.put("shipping_state_name", "DELHI");
        file.put("shipping_city_name", "Central Delhi");
        file.put("shipping_address_phone", "7654444444");
		
		    return file;
	}
	
	// Generating random phone number
    private static String generateRandomIndianPhoneNumber() {
        // Ensure it generates a 10-digit number
        return "9" + faker.number().digits(9); // Start with '9' to ensure a valid 10-digit Indian number
    }
    
 // Generate 6 digit pincode
    private static String generateSixDigitPincode() {
        return faker.number().digits(6); // Ensure it generates a 6-digit pincode
    } 
    
	@Test
	public static Map<String, Object> AddLead()
	{
		String pinCode = generateSixDigitPincode();
	 	   String[] lead_type= {"Call","Walkin","Website Signups"};
	 	   String[] lead_source= {"Website","Google Business","Offline","Instagram","Friend Referral","Web Signups","Zopim Chat","Interior/Architect Ref"};

	    	 Map<String, Object> billingAddress = new HashMap<>();
	         billingAddress.put("address_line_1", "");
	         billingAddress.put("address_line_2", "");
	         billingAddress.put("city", "");
	         billingAddress.put("state", "");
	         billingAddress.put("pincode", "");
	         billingAddress.put("landmark", "");		
	         billingAddress.put("gst_number", "");
	         
	         Map<String, Object> data = new HashMap<>();
	         data.put("full_name", randomName);
	         data.put("primary_phone", randomPhoneNumber);
	         data.put("secondary_phone", secondaryPhone);
	         data.put("email", "");
	         data.put("lead_type", lead_type[0]);
	         data.put("lead_source", lead_source[0]);
	         data.put("notes", randomText);        
	         data.put("lead_owner_id", 2);  
	         data.put("requirements", new int[]{1});
	         data.put("billing_address", billingAddress);
	         
	         return data;
	}
	
	@Test
	public static Map<String, Object> AddBillingAddress()
	{
		response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/getBillingAddress/" + ManageQuotation.leadId)
                .andReturn();

        	String pinCode = generateSixDigitPincode();
            
            Map<String, Object> addressPayload = new HashMap<>();
            addressPayload.put("lead_id", leadId);
            addressPayload.put("address_line_1", Address1);
            addressPayload.put("address_line_2", Address2);
            addressPayload.put("gst_number", "");
            addressPayload.put("state_id", 1);
            addressPayload.put("city_id", 5);
            addressPayload.put("pincode", pinCode);
        
		return addressPayload;
	}
	
	@Test
	public static Map<String, Object> UpdateBillingAddress()
	{
		String pinCode = generateSixDigitPincode();
		
		Map<String, Object> data = new HashMap<>();
        data.put("lead_id", 137);
        data.put("address_line_1", Address1);
        data.put("address_line_2", Address2);
        data.put("gst_number", "");
        data.put("state_id", 1);
        data.put("city_id", 4);
        data.put("pincode", pinCode);
        
		return data;
	}
	
	@Test
	public static Map<String, Object> AddShippingAddress()
	{
		String pinCode = generateSixDigitPincode();
		
		Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("id", "");
        shippingAddress.put("address_line_1", Address1);
        shippingAddress.put("address_line_2", Address2);
        shippingAddress.put("state_id", 1);
        shippingAddress.put("city_id", 4);
        shippingAddress.put("pincode", pinCode);
        shippingAddress.put("gst_number", "");
        shippingAddress.put("site_in_charge_mobile_number", "");
        shippingAddress.put("landmark", "");
        
        // Create a list of shipping addresses
        List<Map<String, Object>> shippingAddresses = new ArrayList<>();
        shippingAddresses.add(shippingAddress);

        // Create the outer map
        Map<String, Object> payload = new HashMap<>();
        payload.put("lead_id", 61);
        payload.put("shipping_addresses", shippingAddresses);
		
        return payload;
	}
	
	@Test
	public static Map<String, Object> UpdateShippingAddress()
	{
		String pinCode = generateSixDigitPincode();
		
		Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("id", "12");
        shippingAddress.put("address_line_1", Address1);
        shippingAddress.put("address_line_2", Address2);
        shippingAddress.put("state_id", 1);
        shippingAddress.put("city_id", 4);
        shippingAddress.put("pincode", pinCode);
        shippingAddress.put("gst_number", "");
        shippingAddress.put("site_in_charge_mobile_number", "");
        shippingAddress.put("landmark", "");

        // Create a list of shipping addresses
        List<Map<String, Object>> shippingAddresses = new ArrayList<>();
        shippingAddresses.add(shippingAddress);

        // Create the outer map
        Map<String, Object> payload = new HashMap<>();
        payload.put("lead_id", 61);
        payload.put("shipping_addresses", shippingAddresses);
        
        return payload;
	}
	
	//Recheck stock
	@Test(dependsOnMethods="GetQuotation")
	public static String RecheckStock()
	{
		String file="{\r\n"
				+ "    \"pageNumber\":1,\r\n"
				+ "    \"pageSize\":10,\r\n"
				+ "    \"id\":\"\",\r\n"
				+ "    \"sort\":\"\",\r\n"
				+ "    \"search\":\"\"\r\n"
				+ "}";
		
		return file;
	}
	
	@Test
	public static String RecheckStock1()
	{
		 String file ="{\r\n"
					+ "    \"quotation_id\": \"88\",\r\n"
					+ "    \"id\": [\r\n"
					+ "        \"123\"\r\n"
					+ "    ]\r\n"
					+ "}";
		 
		 return file;
	}	
}


