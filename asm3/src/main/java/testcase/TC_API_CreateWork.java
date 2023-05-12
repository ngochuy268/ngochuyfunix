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

public class TC_API_CreateWork {
	private Response response;
	private ResponseBody<?> responseBody;
	private JsonPath jsonBody;
	
	String baseURL = PropertyReader.applicationConfigReader("baseurl");
	String createWorkPath = PropertyReader.applicationConfigReader("createWorkPath");
	String token = PropertyReader.tokenConfigReader("token");
	
	private String myWork = "Tester";
	private String myExperience = "1 year";
	private String myEducation = "university";
	
	@BeforeClass
	public void init() {
		RestAssured.baseURI = baseURL;
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nameWork", myWork);
		body.put("experience", myExperience);
		body.put("education", myEducation);
		
		RequestSpecification request = RestAssured.given();
		request.header("content-type","application/json");
		request.headers("token",token);
		request.body(body);
		
		response = request.post(createWorkPath);
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		
		System.out.println(" " + responseBody.asPrettyString());
	}
	
	@Test(priority = 0)
	public void TC01_Validate201Created() {
		assertEquals(201,response.getStatusCode() ,"Status Check Failed!");	
	}
	
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
		assertTrue(responseBody.asString().contains("id"), "Id check failed");
	}
	
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
		assertTrue(responseBody.asString().contains("nameWork"), "Name work check failed");
		assertEquals(myWork, jsonBody.getString("nameWork"), "Name work content is not right");
	}
	
	@Test(priority = 3)
	public void TC03_ValidateExperienceMatched() {
		assertTrue(responseBody.asString().contains("experience"), "Experience check failed");
		assertEquals(myExperience, jsonBody.getString("experience"), "Experience content is not right");
	}
	@Test(priority = 4)
	public void TC03_ValidateEducationMatched() {
		assertTrue(responseBody.asString().contains("education"), "Education check failed");
		assertEquals(myEducation, jsonBody.getString("education"), "Education content is not right");
	}
}
