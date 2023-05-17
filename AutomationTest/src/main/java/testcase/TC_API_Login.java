package testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import utils.PropertyReader;

public class TC_API_Login {
	String baseURL = PropertyReader.applicationConfigReader("baseurl");
	String loginPath = PropertyReader.applicationConfigReader("loginPath");
	String account = PropertyReader.applicationConfigReader("account");
	String password = PropertyReader.applicationConfigReader("password");
	
	private Response response; 
	private ResponseBody<?> resBody; 
	private JsonPath jsonBody; 
	private String token;
	
	@BeforeClass
	public void init() {
		RestAssured.baseURI = baseURL;
		Map<String, Object> acc = new HashMap<String, Object>();
		acc.put("account", account);
		acc.put("password", password);
		
		RequestSpecification request = RestAssured.given();
		request.header("content-type","application/json");
		request.body(acc);
		
		response = request.post(loginPath);
		resBody = response.body();
		jsonBody = resBody.jsonPath();
		
//		Save token
		token = jsonBody.getString("token");
		PropertyReader.saveToken("token", token);
		
		System.out.println(" " + resBody.asPrettyString());
		}
	
	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		assertEquals(200,response.getStatusCode() ,"Status Check Failed!");	
	}
	
	@Test(priority = 1)
	public void TC02_ValidateMessage() {
		assertTrue(resBody.asString().contains("message"), "Message check failed");
		assertEquals("Đăng nhập thành công",jsonBody.getString("message") ,"Message content check failed");
	}
	
	@Test(priority = 2)
	public void TC03_ValidateToken() {
		assertTrue(resBody.asString().contains("token"), "token check failed");
	}
	
	@Test(priority = 3)
	public void TC05_ValidateUserType() {
		assertTrue(resBody.asString().contains("user"), "User check failed");
		assertTrue(resBody.asString().contains("type"), "Type check failed");
		assertEquals("UNGVIEN",jsonBody.getString("user.type") ,"Type content check failed");
	}
	
	@Test(priority = 4)
	public void TC06_ValidateAccount() {
		assertTrue(resBody.asString().contains("user"), "User check failed");
		assertEquals("testerFunix",jsonBody.getString("user.account") ,"Account content check failed");
		assertEquals("Abc13579",jsonBody.getString("user.password") ,"Account content check failed");
	}
	
}
