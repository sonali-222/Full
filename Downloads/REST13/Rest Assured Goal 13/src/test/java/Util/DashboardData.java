package Util;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class DashboardData {
	
	static String[] toplimit= {"5","10","20","30","40","50"};
	static String[] filterData= {"This Week","Today","This Month","This Year"};
	
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}

	//Get Dashboard data
	@Test
	public static String GetDashboardData()
	{
		String file="{\r\n"
        		+ "    \"filter\":\""+filterData[3]+"\" \r\n"
        		+ "}";
		return file;
	}
	
	//Get Quotation Analytics
	@Test
	public static String GetQuotationAnalytics()
	{
		String[] sort= {"salesExecutiveDesc", "salesExecutiveAsc", "convertedQuoteDesc", "convertedQuoteAsc", "totalDesc", "totalAsc", "quoteNumberDesc", "quoteNumberAsc", "salesExecutiveNcDesc", "salesExecutiveNcAsc", "customerNameNcDesc", "customerNameNcAsc", "amountNcDesc", "amountNcAsc"};
		 String file="{\r\n"
	        		+ "    \"year\": 2024,\r\n"
	        		+ "    \"month\": 6,\r\n"
	        		+ "    \"user_id\": \"\",\r\n"							//sales executive id
	        		+ "    \"top_ten\": "+toplimit[1]+",\r\n"
	        		+ "    \"sort\": \"\"\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export Quotation Analytics
	@Test
	public static String ExportQuotationAnalytics()
	{
		String file="{\r\n"
        		+ "    \"year\": 2024,\r\n"
        		+ "    \"month\": 6\r\n"
        		+ "}";
		
		return file;
	}
	
	//Export sales executive quottaion analytics
	@Test
	public static String ExportSalesExecutiveQuotationAnalytics()
	{
		String file="{\r\n"
        		+ "    \"year\": 2024,\r\n"
        		+ "    \"month\": 6,\r\n"
        		+ "    \"user_id\": \"\"\r\n"
        		+ "}";
		
		return file;
	}
	
	//Export not yet converted quotes
	@Test
	public static String ExportNotYetConvertedQuotes()
	{
		String file="{\r\n"
        		+ "    \"year\": 2024,\r\n"
        		+ "    \"month\": 6,\r\n"
        		+ "    \"limit\": "+toplimit[1]+"\r\n"
        		+ "}";
		
		return file;
	}
	
	//Get lead analytics
	@Test
	public static String GetLeadAnalytics()
	{
		String file="{\r\n"
        		+ "    \"year\": 2024\r\n"
        		+ "}";
		
		return file;
	}
	
	//GEt overdue task
	@Test
	public static String GetOverdueTask()
	{
		String[] sortValues= {"salesExecutiveDesc", "salesExecutiveAsc", "taskDesc", "taskAsc", "overDueTaskDesc", "overDueTaskAsc"};
		 
	     String file="{\r\n"
	        		+ "    \"user_id\": \"\",\r\n"
	        		+ "    \"sort\": \""+sortValues[1]+"\"\r\n"
	        		+ "}";
	     
	     return file;
	}
	
	//Lead conversion report
	@Test
	public static String LeadConversionReport()
	{
		 String file="{\r\n"
	        		+ "    \"month\": 6\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export lead generation sources
	@Test
	public static String ExportLeadGeneration()
	{
		 String file="{\r\n"
	        		+ "    \"year\": 2024\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export lead overdue task
	@Test
	public static String ExportLeadOverdueTask()
	{
		String[] sortValue= {"salesExecutiveDesc", "salesExecutiveAsc", "taskDesc", "taskAsc", "overDueTaskDesc", "overDueTaskAsc"};
		 
		 String file="{\r\n"
	        		+ "    \"user_id\": \"\",\r\n"
	        		+ "    \"sort\": \""+sortValue[1]+"\"\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export lead conversion report
	@Test
	public static String ExportLeadConversionReport()
	{
		  String file="{\r\n"
	        		+ "    \"year\": 2024,\r\n"
	        		+ "    \"month\": 6\r\n"
	        		+ "}";
		  
		  return file;
	}
	
	//Stock check analytics
	@Test
	public static String StockCheckAnalytics()
	{
		String[] sortValue= {"vendorDesc", "vendorAsc", "avgQuickDesc", "avgQuickAsc", "avgTimeDesc", "avgTimeAsc", "strikeRateDesc", "strikeRateAsc", "vendorNamesPrDesc", "vendorNamesSrDesc", "vendorNamesPrAsc", "vendorNamesSrAsc"};
		 
		 String file="{\r\n"
	        		+ "    \"filter\": \""+filterData[3]+"\",\r\n"
	        		+ "    \"sort\": \"\",\r\n"
	        		+ "    \"quick_response_limit\": "+toplimit[1]+",\r\n"
	        		+ "    \"poor_response_limit\": "+toplimit[1]+",\r\n"
	        		+ "    \"strike_rate_limit\": "+toplimit[1]+"\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export vendor quick response
	@Test
	public static String ExportVendorQuickResponse()
	{
		String[] sortValue= {"vendorDesc", "vendorAsc", "avgQuickDesc", "avgQuickAsc"};
		 
        String file="{\r\n"
        		+ "    \"filter\": \""+filterData[3]+"\",\r\n"
        		+ "    \"sort\": \""+sortValue[1]+"\",\r\n"
        		+ "    \"limit\": "+toplimit[1]+"\r\n"
        		+ "}";
        
        return file;
	}
	
	//Export vendor poor response
	@Test
	public static String ExportVendorPoorResponse()
	{
		String[] sortValue= {"vendorNamesPrDesc", "vendorNamesPrAsc", "avgTimeDesc", "avgTimeAsc"};
	     
		 String file="{\r\n"
	        		+ "    \"filter\": \""+filterData[3]+"\",\r\n"
	        		+ "    \"sort\": \""+sortValue[1]+"\",\r\n"
	        		+ "    \"limit\": "+toplimit[1]+"\r\n"
	        		+ "}";
		 
		 return file;
	}
	
	//Export vendor strike rate
	@Test
	public static String ExportVendorStrikeRate()
	{
		 String[] sortValue= {"vendorNameSrDesc", "vendorNameSrAsc", "strikeRateDesc", "strikeRateAsc"};
	        String file="{\r\n"
	        		+ "    \"filter\": \""+filterData[3]+"\",\r\n"
	        		+ "    \"sort\": \""+sortValue[1]+"\",\r\n"
	        		+ "    \"limit\": "+toplimit[1]+"\r\n"
	        		+ "}";
	        
	        return file;
	}
}
