package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import APITestCase.ManageInventory;
import io.restassured.RestAssured;

public class InventoryData {
	 int productId;
	    static Faker faker = new Faker();
	    static int randomCode = 10000000 + faker.number().numberBetween(0, 90000000); // Ensures the number is 6 digits
	    static String randomSKU = faker.lorem().characters(15, true, true);
	    static String randomProductName = faker.commerce().productName();
	    //static List<Integer> vendorCompanyId;
	    
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI="https://mytyles.website:3133/api/v1";
	}
	
	//Add inventory
	@Test(dependsOnMethods = "GetProductDetails")
	public static Map<String, Object> AddProduct()
	{
		String[] uom = {"Sq.ft", "Set", "Piece", "Kg"};
        String selectedUom = uom[1];
        
		Map<String, Object> file = new HashMap<>();
        file.put("product_name", randomProductName);
        file.put("product_sku", randomSKU);
        file.put("brand_id", 1);
        file.put("unit_of_measurement", selectedUom);
        file.put("country_of_manufacture", 1);
        file.put("product_uses", new int[]{1});
        file.put("product_category", new int[]{1});
        file.put("product_code", randomCode);
        file.put("product_hsn_code", 1220);
        file.put("product_packing", "new");
        file.put("vendor_company_name", 160);
        file.put("product_size", 1);
        file.put("product_finish", 1);
        file.put("product_material_type", 1);
        file.put("product_weight", "");
        file.put("product_quality", 1);
        file.put("web_url", "");
        file.put("final_price", 1200);
        file.put("mrp", 2000);
        file.put("discount", "");
        file.put("gst_rate", 1);
        file.put("purchase_rate", "");
        file.put("inventory", "10");
        file.put("coverage", "");
        file.put("delivery_time", "");
        file.put("product_images", "");
        file.put("product_image_previews", "");
		
        return file;
	}
	
	//Get product details
	@Test
	public static Map<String, Object> GetProductDetails()
	{
		String[] data= {"brand", "country_of_manufacture", "uses", "category", "finish", "material", "quality", "delivery_time", "vendor_company_name", "product_size"};
        
    	Map<String, Object> file = new HashMap<>();
        file.put("dropdown_data", data[8]);
        file.put("search", "");
        
        return file;
	}
	
	//Get inventory
	@Test(dependsOnMethods = "GetProductDetails")
	public static  Map<String, Object> GetInventory()
	{
		 int vendor_Company = ManageInventory.vendorCompanyId.get(1); // Vendor company access via id
	        
	        Map<String, Object> file = new HashMap<>();
	        file.put("pageNumber", 1);
	        file.put("pageSize", 10);
	        file.put("search", "");
	        file.put("sort", "");
	        file.put("product_status",  new int[]{});
	        file.put("brand",new int[]{});
	        file.put("product_material_type", new int[]{});
	        file.put("product_quality", new int[]{});
	        file.put("product_finish", new int[]{});
	        file.put("product_category", new int[]{});
	        file.put("product_uses", new int[]{});
	        file.put("country_of_manufacture", new int[]{});
	        file.put("vendor_company_name", new int[]{vendor_Company});
	        file.put("unit_of_measurement",  new int[]{});
	        file.put("gst_rate", new int[]{});
	        
	        return file;
	}
	
	//Update product
	@Test
	public static Map<String,Object> UpdateProduct()
	{
		 Map<String, Object> file = new HashMap<>();
         file.put("id", "187");
         file.put("product_name", randomProductName);
         file.put("product_sku", randomSKU);
         file.put("brand_id", new int[]{1});
         file.put("unit_of_measurement", new int[]{1});
         file.put("country_of_manufacture", new int[]{1});
         file.put("product_uses", new int[]{1});
         file.put("product_category", "");
         file.put("product_code", randomCode);
         file.put("product_hsn_code", 1220);
         file.put("product_packing", "");
         file.put("vendor_company_name", new int[]{2});
         file.put("product_size", new int[]{1});
         file.put("product_finish", new int[]{1});
         file.put("product_material_type", new int[]{1});
         file.put("product_weight", "");
         file.put("product_quality", new int[]{1});
         file.put("web_url", "");
         file.put("final_price", 1200);
         file.put("mrp", 2000);
         file.put("discount", "");
         file.put("gst_rate", new int[]{1});
         file.put("purchase_rate", "");
         file.put("inventory", "10");
         file.put("coverage", "");
         file.put("delivery_time", "");
         file.put("product_images", "");
         file.put("product_image_previews", "");
         
         return file;
	}
	
	//Add product image
	@Test
	public static String[] AddProductImage() throws IOException
	{		 
		 return new String[] {"C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageInventory\\tile1.jpg","C:\\Users\\Admin\\eclipse-workspace\\REST ASSURED GOAL\\src\\main\\java\\ManageInventory\\Image.jpg"};
	}
	
	@Test
	public static String DeleteImage()
	{
		String file="{ \r\n"
				+ "	\"id\":97\r\n"
				+ "}";
		
		return file;
	}
}

