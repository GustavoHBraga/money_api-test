package resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CategoryTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://172.23.176.1:8006/money-backend";
	}
	
	public final String baseResource = "/category";
	
	@Test
	public void testGetCategories() {
		RestAssured
			.given()
				.log().all()
			.when()
				.get(baseResource)
			.then()
				.statusCode(200)
		
		;
	}

	@Test
	public void testPostCategory() {
		
		// Cria uma nova categoria e obtem o codigo gerado
		int cod = RestAssured.given()
				.body("{\"name\":\"Amazon-prime\"}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(201)
				.extract().path("cod")
			
		;
		
		// Deleta a cateogia baseado no codigo gerado
		RestAssured.given()
			.when()
				.delete(baseResource + '/' + cod)
			.then()
				.statusCode(204)
				
		;
		
	}
	
	@Test
	public void testPostCategoryMessageError() {
		RestAssured
			.given()
				.body("{\"name\":null}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(400)
				.contentType(ContentType.JSON)
				
			;
	}

	@Test
	public void testPostCategoryMessageErrorBylength() {
		List<?> userMessage = RestAssured
			.given()
				.body("{\"name\":\"A\"}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(400)
				.extract().path("userMessage")
			
				
			;
		assertEquals(userMessage.get(0), "Name must be min 3 and max 50 characters");
	}
	
	@Test
	public void testPostCategoryMessageNotNull() {
		List<?> userMessage = RestAssured
			.given()
				.body("{\"name\":null}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(400)
				.extract().path("userMessage")
			
				
			;
		assertEquals(userMessage.get(0), "Name is required");
	}
}
