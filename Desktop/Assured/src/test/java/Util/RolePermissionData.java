package Util;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import APITestCase.RolesAndPermissions;
import io.restassured.RestAssured;

public class RolePermissionData {
	static int roleId;
	static Faker faker = new Faker();
    static String randomRoleName = faker.job().title();
    
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}
	
	@Test
	public static String CreateRole()
	{
		 String file = "{\r\n" +
                 "    \"roleName\": \"" + randomRoleName + "\"\r\n" +
                 "}";
		 
		 return file;
	}
	
	@Test(dependsOnMethods="CreateRole")
	public static String EditRoleName()
	{
		roleId=RolesAndPermissions.roleId1;
		 String file = "{\r\n" +
                 "    \"roleId\": \"" + roleId + "\",\r\n" +
                 "    \"roleName\": \"" + randomRoleName + "\"\r\n" +
                 "}";
		return file;
	}
	
	@Test
	public static String DeleteRole()
	{
		String file = "{\r\n" +
                "    \"roleId\": 93,\r\n" +
                "    \"change_role_id\": \"" + roleId + "\"\r\n" +
                "}";
		return file;
	}
	
	@Test
	public static String GetRoleDetails()
	{
		String file = "{\r\n" +
                "    \"roleId\":\"1\"\r\n" +
                "}";
		return file;
		
	}
	
	@Test
	public static String EditRole()
	{
		 String file = "{\r\n" +
                 "    \"role_name\": \"Principal Marketing Specialist\",\r\n" +
                 "    \"role_id\": \"80\",\r\n" +
                 "    \"permissions\": [\r\n" +
                 "        {\r\n" +
                 "            \"module_id\": 29,\r\n" +
                 "            \"modulePermission\": [\r\n" +
                 "                {\r\n" +
                 "                    \"name\": \"read\",\r\n" +
                 "                    \"displayName\": \"Read\",\r\n" +
                 "                    \"isSelected\": true\r\n" +
                 "                }\r\n" +
                 "            ]\r\n" +
                 "        }\r\n" +
                 "    ]\r\n" +
                 "}";
		 
		 return file;
	}
	
	@Test
	public static String RoleList()
	{
		String[] roleType= {"system", "custom"};
		 
        String file = "{\r\n"
        		+ "    \"pageNumber\":\"\",\r\n"
        		+ "    \"pageSize\":\"\",\r\n"
        		+ "    \"role_type\":\"\"\r\n"					//roleType
        		+ "}";
        
        return file;
	}
}
