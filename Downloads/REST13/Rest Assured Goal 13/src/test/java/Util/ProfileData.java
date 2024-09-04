package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import APITestCase.ManageProfile;
import io.restassured.RestAssured;

public class ProfileData {
	//static String Doj=ManageProfile.Doj;
	static String number;
	
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}
	
	//Get User Profile
	@Test
	public static String GetUserProfile()
	{
		String file = "{ "
				+ "\"id\": 1 "
				+ "}";
		
		return file;
	}

	//Upload profile image
	@Test
	public static String UploadProfileImage()
	{
		 String filePath = "C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageUsers\\tile1.jpg";
		 
		 return filePath;
	}
	
	//Remove profile image
	@Test
	public static String RemoveProfileImage()
	{
		String requestBody = "{"
				+ " \"id\": 1 "
				+ "}";
		
		return requestBody;
	}
	
	//Update User Profile
	@Test(dependsOnMethods="GetUserProfile")
	public static String UpdateUserProfile() throws ParseException
	{
		Faker faker = new Faker();
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String email = faker.internet().emailAddress();      
        
     // Remove any extraneous characters from Doj
        //if(Doj!=null)
        String cleanDoj = ManageProfile.Doj.replaceAll("[\\[\\]]", "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dojDate = dateFormat.parse(cleanDoj);
        String formattedDoj = dateFormat.format(dojDate);
        
        String requestBody = String.format("{\n" +
                "    \"id\": 1,\n" +
                "    \"first_name\": \"%s\",\n" +
                "    \"last_name\": \"%s\",\n" +
                "    \"user_email\": \"%s\",\n" +
                "    \"date_of_birth\": \"2013-01-01\",\n" +
                "    \"date_of_joining\": \"%s\",\n" +
                "    \"phone_number\": \"%s\",\n" +
                "    \"change_password\": {}\n" +
                "}", randomFirstName, randomLastName, email,formattedDoj,number);
        
        return requestBody;
	}
	
	private static String generateUniquePassword() {
        String basePassword = "Test" + "@";
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // Generate a random number between 100 and 999
        return basePassword + randomNumber;
    }
	
	//Change password
	@Test
	public static String ChangePassword()
	{
		Faker faker = new Faker();
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String newPassword = generateUniquePassword();

        String requestBody = String.format("{\n" +
                "    \"id\": 1,\n" +
                "    \"first_name\": \"%s\",\n" +
                "    \"last_name\": \"%s\",\n" +
                "    \"user_email\": \"%s\",\n" +
                "    \"date_of_birth\": \"\",\n" +
                "    \"date_of_joining\": \"\",\n" +
                "    \"change_password\": {\n" +
                "        \"new_password\": \"%s\",\n" +
                "        \"confirm_password\": \"%s\"\n" +
                "    }\n" +
                "}", randomFirstName, randomLastName, email, newPassword, newPassword);
        
        return requestBody;
	}
}

