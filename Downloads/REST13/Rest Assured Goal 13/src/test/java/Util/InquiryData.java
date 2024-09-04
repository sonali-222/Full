package Util;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class InquiryData {
	
	static String[] dateType= {"all", "today", "yesterday", "this_week", "last_week", "this_month", "last_month", "this_year", "custom"};
    static String selectedDateType=dateType[7];
    String[] enquiryStatus= {"New inquiry","Old inquiry"};
    String[] inquiryAction= {"Available","Not Available"};
	
	 @BeforeClass
	   public void setup() {
	       RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
	   }
	 
	 //Get vendor inquiry
	 @Test
	 public static String GetVendorInquiry()
	 {
		 String[] inquiryTab= {"new_inquiry","responded_inquiry","order_placed_inquiry"};   
	       String selectedValue=inquiryTab[1];
	       
	       String[] sort= {"createdDateDesc","createdDateAsc","productNameAsc","productNameDesc","enquiryAsc","enquiryDesc","quantityDesc","quantityAsc","enquiryStatusAsc","enquiryStatusDesc"};
	       String selectedSortValue=sort[0];
	       
	       String file = "{\r\n"
	    		    + "    \"pageNumber\": 1,\r\n"
	    		    + "    \"pageSize\": 10,\r\n"
	    		    + "    \"enquiry_tab\": \"" + selectedValue + "\",\r\n"
	    		    + "    \"search\": \"\",\r\n"
	    		    + "    \"sort\": \"" + selectedSortValue + "\",\r\n"
	    		    + "    \"dateRange\": {\r\n"
	    		    + "        \"type\": \"" + selectedDateType + "\",\r\n"
	    		    + "        \"startDate\": \"\",\r\n"
	    		    + "        \"endDate\": \"\"\r\n"
	    		    + "    },\r\n"
	    		    + "    \"action\": \"\",\r\n"
	    		    + "    \"quantity\": \"\",\r\n"
	    		    + "    \"enquiry_status\": \"\"  \r\n"        						// For new inquiry tab only
	    		    + "}";
	       
	       return file;
	 }
	 
	 //Update Inquiry status
	 @Test
	 public static String UpdateProductStatus()
	 {
		 String[] inquiryTab= {"Not Available","Available Multiple Batches","Available"};   
	       String selectedValue=inquiryTab[2];
	       
	        String file="{\r\n"
	        		+ "    \"id\": 118,\r\n"
	        		+ "    \"stock_check_status\": \""+selectedValue+"\",\r\n"
	        		+ "    \"not_available_quantity\": \"\",\r\n"
	        		+ "    \"not_available_in_days\": \"\"\r\n"
	        		+ "}";
	        
	        return file;
	 }
	 
	 //Export Inquiry
	 @Test
	 public static String ExportInquiry()
	 {
		 String[] inquiryTab= {"new_inquiry", "order_placed_inquiry", "responded_inquiry"};
	       String selectedValue=inquiryTab[0];
	      
	       String file="{\r\n"
	       		+ "    \"dateRange\": {\r\n"
	       		+ "        \"type\": \""+selectedDateType+"\",\r\n"
	       		+ "        \"startDate\": \"\",\r\n"
	       		+ "        \"endDate\": \"\"\r\n"
	       		+ "    },\r\n"
	       		+ "    \"action\": \"\",\r\n"						//inquiryAction
	       		+ "    \"quantity\": \"1\",\r\n"
	       		+ "    \"enquiry_status\": \"\",\r\n"				//enquiryStatus
	       		+ "    \"enquiry_tab\": \""+selectedValue+"\",\r\n"
	       		+ "    \"search\": \"\"\r\n"
	       		+ "}";
	       
	       return file;
	 }
	 

}
