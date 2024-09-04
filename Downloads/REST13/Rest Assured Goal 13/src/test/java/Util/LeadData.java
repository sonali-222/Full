package Util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import APITestCase.ManageLead;
import io.restassured.RestAssured;

public class LeadData {
	
	static Faker faker=new Faker();
	static String randomName = faker.name().fullName();
    static String randomPhoneNumber = generateRandomIndianPhoneNumber();
    static String email=faker.internet().emailAddress();
    static String secondaryPhone=generateRandomIndianPhoneNumber();
    static String randomText = faker.lorem().characters(15, true, true);
    static String streetAddress = faker.address().streetAddress();
    static String streetAddress1 = faker.address().streetAddress();
    String city = faker.address().city();
    String state = faker.address().state();
    String country = faker.address().country();
    static String[] lead_type= {"Call","Walkin","Website Signups"};
	static String[] lead_source= {"Website","Google Business","Offline","Instagram","Friend Referral","Web Signups","Zopim Chat","Interior/Architect Ref"};

	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}
	
	// Generating random phone number
    private static String generateRandomIndianPhoneNumber() {
        // Ensure it generates a 10-digit number
        return "8" + faker.number().digits(9); // Start with '9' to ensure a valid 10-digit Indian number
    }
    
	@Test 
	public static Map<String, Object> GetLeadOwner()
	{
		Map<String, Object> data = new HashMap<>();
        data.put("pageSize", "");
        data.put("role", 6);
        data.put("user_status", "active");
        data.put("search", "");
        data.put("sort", "");
		
		return data;
	}

	// Generate 6 digit pincode
    private static String generateSixDigitPincode() {
        return faker.number().digits(6); // Ensure it generates a 6-digit pincode
    }
   
	
	@Test
	public static Map<String, Object> AddLead()
	{
		 String pinCode = generateSixDigitPincode();
    	 Map<String, Object> billingAddress = new HashMap<>();
         billingAddress.put("address_line_1", streetAddress);
         billingAddress.put("address_line_2", streetAddress1);
         billingAddress.put("city", 4);
         billingAddress.put("state", 1);
         billingAddress.put("pincode", pinCode);
         billingAddress.put("landmark", "new");		
         billingAddress.put("gst_number", "12WERTY1234WEZ2");
         
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
	public static String GetLeads()
	{
		String file="{\r\n"
				+ "    \"id\":\"\",\r\n"
				+ "   \"pageNumber\": \"\",\r\n"
				+ "   \"pageSize\": \"\",\r\n"
				+ "   \"search\": \"\",\r\n"
				+ "   \"lead_stage\":[],\r\n"
				+ "   \"lead_source\":[],\r\n"
				+ "   \"lead_owner_id\":[],\r\n"
				+ "   \"lead_activity\":[],\r\n"
				+ "   \"dateFilter\":{\r\n"
				+ "       \"type\":\"\" ,\r\n"
				+ "       \"startDate\": \"\", \r\n"
				+ "       \"endDate\": \"\"\r\n"
				+ "   },\r\n"
				+ "  \"is_star_marked\":\"\",\r\n"
				+ "  \"no_task_lead\":\"\", \r\n"
				+ "  \"sort\":\"\"\r\n"
				+ "}\r\n"
				+ "";
		
		return file;
	}
	
	@Test
	public static Map<String, Object> UpdateLead()
	{
		String pinCode = generateSixDigitPincode();
		   String randomPhoneNumber = generateRandomIndianPhoneNumber();
		   
	       Map<String, Object> billingAddress = new HashMap<>();
	       billingAddress.put("address_line_1", streetAddress);
	       billingAddress.put("address_line_2", streetAddress1);
	       billingAddress.put("city_id", 4);
	       billingAddress.put("state_id", 1);
	       billingAddress.put("pincode", pinCode);
	       billingAddress.put("landmark", "new");		
	       billingAddress.put("gst_number", "12WERTY1234WEZ2");
	       
	       Map<String, Object> data = new HashMap<>();
	       data.put("id", 215);
	       data.put("full_name", randomName);
	       data.put("primary_phone", randomPhoneNumber);
	       data.put("secondary_phone", "");
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
	public static String[] AddLeadAttachment()
	{
		return new String[] {"C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageUsers\\tile1.jpg"};
	}
	
	@Test
	public static String ExportLead()
	{
		String file="{\r\n"
				+ "    \"search\": \"\",\r\n"
				+ "    \"lead_stage\": [],\r\n"
				+ "    \"lead_source\": [],\r\n"
				+ "    \"lead_owner_id\": [],\r\n"
				+ "    \"lead_activity\": [],\r\n"
				+ "    \"is_star_marked\": false,\r\n"
				+ "    \"no_task_lead\": false,\r\n"
				+ "    \"leadActivityDateFilter\": {\r\n"
				+ "        \"type\": \"\",\r\n"
				+ "        \"startDate\": \"\",\r\n"
				+ "        \"endDate\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"dateFilter\": {\r\n"
				+ "        \"type\": \"\",\r\n"
				+ "        \"startDate\": \"\",\r\n"
				+ "        \"endDate\": \"\"\r\n"
				+ "    }\r\n"
				+ "}";
		
		return file;
	}
	
	@Test
	public static Map<String, Object> AddLeadTask()
	{
		Map<String, Object> payload = new HashMap<>();
		   payload.put("lead_id", 185);
	       payload.put("subject", "Follow up with " + faker.name().fullName());
	       payload.put("follow_up_date", new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(10, TimeUnit.DAYS)));
	       payload.put("follow_up_time", new SimpleDateFormat("HH:mm").format(new Date()));
	       payload.put("task_details", faker.lorem().sentence());
	       payload.put("reminder", faker.bool().bool());
	       
	       return payload;
	}
	
	@Test
	public static String GetLeadTask()
	{
		String file="{\r\n"
				+ "    \"pageSize\": \"\",\r\n"
				+ "    \"pageNumber\": \"\",\r\n"
				+ "    \"filter\":\"\", \r\n"
				+ "    \"sort\":\"\" \r\n"
				+ "}";
		
		return file;
	}
	
	@Test
	public static Map<String, Object> GetLeadHistory()
	{
		Map<String, Object> payload = new HashMap<>();
	       payload.put("lead_id", 23);
	       
	       return payload;
	}
	
	@Test
	public static Map<String, Object> AddLeadActivity()
	{
		Map<String, Object> payload = new HashMap<>();
	       payload.put("lead_id", 37);
	       payload.put("activity_type", "Phone Call Positive");
	       payload.put("notes", randomText);
	       
	       return payload;
	}
	
	@Test
	public static Map<String, Object> GetCity()
	{
		 Map<String, Object> payload = new HashMap<>();
	       payload.put("state_id", 5);
	       
	       return payload;
	}
	
	@Test
	public static Map<String, Object> getLeadDetails()
	{
		 Map<String, Object> payload = new HashMap<>();
	       payload.put("id", 63);
	       
	       return payload;
	}
	
	@Test
	public static Map<String, Object> CheckLeadNumbers()
	{
		Map<String, Object> payload = new HashMap<>();
	       payload.put("primary_phone", "9891022072");
	       payload.put("secondary_phone", "");
	       
	       return payload;
	}
	
	@Test(dependsOnMethods="AddLead")
	public static String UpdateLeadStage() throws IOException
	{
		 String[] stages = {"New Lead", "Follow Up", "Showroom Visit", "Quotation", "Order Placed", "General Enquiry", "Lost", "Invalid"};
	       
	       // Ensure leadId is valid (non-zero or non-negative depending on your application logic)
	       if (ManageLead.leadId <= 0) {
	           throw new IOException("Invalid leadId: " + ManageLead.leadId);
	       }

	       String file ="{\r\n"
	       		+ "    \"id\": \"" + ManageLead.leadId + "\",\r\n"
	       		+ "    \"lead_stage\": \"" + stages[1] +"\",\r\n"
	       		+ "    \"comment\": \"\"\r\n"
	       		+ "}";
	       
	       return file;
	}	
	
	@Test
	public static Map<String, Object> UpdateLeadStage1()
	{
		String[] stages = {"New Lead", "Follow Up", "Showroom Visit", "Quotation", "Order Placed", "General Enquiry", "Lost", "Invalid"};
	       
		String newStage = stages[0];
        if (newStage.equals(ManageLead.currentStage)) {
            newStage = stages[1];  // Choose a different stage if they are the same
        } 
        
		Map<String, Object> payload = new HashMap<>();
        payload.put("id", ManageLead.leadId);
        payload.put("lead_stage", newStage);
        payload.put("comment", "");
        
        return payload;
	}
	
	@Test
	public static String DeleteAttachment()
	{
		String file="{\r\n"
		   		+ "    \"id\":[1]\r\n"
		   		+ "}";
		
		return file;
	}
	
	@Test 
	public static Map<String, Object> RescheduleTask()
	{
		Map<String, Object> payload = new HashMap<>();
	       payload.put("task_id", faker.number().randomDigit());
	       payload.put("subject", "Follow up with " + faker.name().fullName());
	       payload.put("follow_up_date", new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(10, TimeUnit.DAYS)));
	       payload.put("follow_up_time", new SimpleDateFormat("HH:mm").format(new Date()));
	       payload.put("task_details", faker.lorem().sentence());
	       payload.put("reminder", faker.bool().bool());
	       
	       return payload;
	}
	
	@Test
	public static String MarkTaskAsCompleted()
	{
		 String file="{\r\n"
			   		+ "    \"task_id\":78\r\n"
			   		+ "}\r\n"
			   		+ "";
		 
		 return file;
	}
	
	@Test
	public static String GetTaskByLeadId()
	{
		  String file="{\r\n"
			   		+ "    \"lead_id\":101\r\n"
			   		+ "}";
		  
		  return file;
	}
	
	@Test
	public static String MarkLeadAsStar()
	{
		String file="{\r\n"
		   		+ "\"lead_id\": 101,\r\n"
		   		+ "\"is_star_marked\":false \r\n"
		   		+ "}";
		
		return file;
	}
}
