package Util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserData {
	private static final Faker faker=new Faker();
	static String randomFirstName = faker.name().firstName();
	public static String selectedStatus;
	
	 @BeforeClass
	   public void setup() {
	       RestAssured.baseURI = "https://mytyles.website:3133/api/v1";
	   }
	 
	 // Generating random phone number
	    private static String generateRandomIndianPhoneNumber() {
	        // Ensure it generates a 10-digit number
	        return faker.number().digits(10); // Start with '9' to ensure a valid 10-digit Indian number
	    }
	    public static Date generateRandomDateOfBirth(int minAge, int maxAge) {
	        return faker.date().birthday(minAge, maxAge);
	    }  
	    public static Date generateDateOfJoining(Date dateOfBirth, int minYearsGap) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(dateOfBirth);
	        calendar.add(Calendar.YEAR, minYearsGap);
	        return calendar.getTime();
	    }
	   
	   // Add user
	   @Test
	   public static String AddUser() {
		   String randomFirstName = faker.name().firstName();
	         String randomLastName = faker.name().lastName();
	         String randomPhoneNumber = generateRandomIndianPhoneNumber();
	         String email=faker.internet().emailAddress();
	         
	         Date dob = generateRandomDateOfBirth(18, 60);
	         Date doj = generateDateOfJoining(dob, 16);
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	         String formattedDob = dateFormat.format(dob);
	         String formattedDoj=dateFormat.format(doj);
	    	String  payload="{\r\n"
	    			+ "    \"first_name\": \""+randomFirstName+"\",\r\n"
	    			+ "    \"last_name\": \""+randomLastName+"\",\r\n"
	    			+ "    \"user_email\": \""+email+"\",\r\n"
	    			+ "    \"phone_number\": \""+randomPhoneNumber+"\",\r\n"
	    			+ "    \"date_of_birth\": \""+formattedDob+"\",\r\n"
	    			+ "    \"date_of_joining\": \""+formattedDoj+"\",\r\n"
	    			+ "    \"role\": \"6\",\r\n"
	    			+ "    \"branch\": \"\",\r\n"
	    			+ "    \"vendor_company_name\": \"\",\r\n"
	    			+ "    \"vendor_company_admin_id\": \"\"\r\n"
	    			+ "}";
		return payload;
	   }
	   
	   //Update User
	   @Test
	   public static String UpdateUser()
	   {
		   String randomFirstName = faker.name().firstName();
	       String randomLastName = faker.name().lastName();
	       String randomPhoneNumber = generateRandomIndianPhoneNumber();
	       
	       String payload = "{\r\n"
	               + "    \"id\":\"123\",\r\n"
	               + "    \"first_name\":\"" + randomFirstName + "\",\r\n"
	               + "    \"last_name\":\"" + randomLastName + "\",\r\n"
	               + "    \"user_email\":\"\",\r\n"
	               + "    \"phone_number\":\"" + randomPhoneNumber + "\",\r\n"
	               + "    \"date_of_birth\":\"\",\r\n"
	               + "    \"date_of_joining\":\"\",\r\n"
	               + "    \"role\":\"5\",\r\n"
	               + "    \"vendor_company_name\":\"\",\r\n"
	               + "    \"branch\":\"2\",\r\n"
	               + "    \"profile_image\":\"\"\r\n"
	               + "}";
		return payload;
	   }
	   
	   //Get user
	   @Test
	   public static String GetUser()
	   {
		   String[] sort= {"userPhoneDesc", "userPhoneAsc","userRoleAsc","userRoleDesc", "userNameAsc","userNameDesc","createdDateAsc","createdDateDesc","userStatusAsc","userStatusDesc"};
	       String sortValue=sort[0];
	       
	       String[] user_status = {"active", "inactive", "added"};        
	       String selectedStatus=user_status[0];
	       
	       String file="{\r\n"
	       		+ "    \"pageSize\":\"\",\r\n"
	       		+ "    \"role\":\"\",\r\n"
	       		+ "    \"user_status\":\"\",\r\n"
	       		+ "    \"search\":\"\",\r\n"
	       		+ "    \"sort\":\"\"\r\n"
	       		+ "}";
	       
	       return file;
	   }
	   
	   //Get user count
	   @Test
	   public static String UserCount()
	   {
		   String file="{\r\n"
		   		+ "    \"pageSize\":\"\",\r\n"
		   		+ "    \"role\":\"\",\r\n"
		   		+ "    \"user_status\":\"\",\r\n"
		   		+ "    \"search\":\"\",\r\n"
		   		+ "    \"sort\":\"\"\r\n"
		   		+ "}";
		   
		   return file;
	   }
	   
	   //Upload profile image
	   @Test
	   public static String UploadProfileImage() throws IOException
	   {
		   String filePath = "C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageUsers\\Image.jpg";
			
	        return filePath;
	   }
	   
	   //Export User
	   @Test
	  public static String ExportUser()
	  {
		   String[] status= {"active","inactive","added"};
	        String selectedStatus=status[0];
	        
	        String file="{\r\n"
	        		+ "    \"role\":\"\",\r\n"
	        		+ "    \"user_status\":\"\",\r\n"
	        		+ "    \"search\":\"\"\r\n"
	        		+ "}";
		   return file;
	  }
	   
	   //Role List
	   @Test
	   public static String RoleList()
	   {
		   String file="{\r\n"
		   		+ "    \"pageNumber\":\"\",\r\n"
		   		+ "    \"pageSize\":\"\",\r\n"
		   		+ "    \"role_type\":\"\"\r\n"
		   		+ "}";
		   return file;
	   }
	   
	 //GENERATE UNIQUE PASSWORD
	    private static String generateUniquePassword() {
	        String basePassword = randomFirstName +"@0";
	        Random random = new Random();
	        int randomNumber = 100 + random.nextInt(900); // Generate a random number between 100 and 999
	        return basePassword + randomNumber;
	    }
	    
	    //Reset Password
	    @Test
	    public static String ResetPassword()
	    {
	    	String newPassword=generateUniquePassword();
	    	
	    	String file="{\r\n"
	    			+ "    \"new_password\": \""+newPassword+"\",\r\n"
	    			+ "    \"confirm_password\": \""+newPassword+"\",\r\n"
	    			+ "    \"phone_number\": \"8750695670\"\r\n"
	    			+ "}\r\n"
	    			+ "\r\n"
	    			+ "";
	    	return file;
	    }
	    
	    //Update user status
	    @Test
	    public static String UpdateUserStatus()
	    {
	    	String[] statuses = {"active", "inactive", "deactivated"};        
	        selectedStatus=statuses[0];
	            
	        String requestBody="{\r\n"
	        		+ "    \"id\":\"180\",\r\n"
	        		+ "    \"user_status\":\"" + selectedStatus + "\"\r\n"
	        		+ "}";
	        
			return requestBody;
	    }
	    
	    //Resend password
	    @Test
	    public static String ResendPassword()
	    {
	    	String file="{\r\n"
	        		+ "    \"id\":223\r\n"
	        		+ "}";
	    	return file;
	    }
	    
}
