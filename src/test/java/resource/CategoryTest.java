package resource;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CategoryTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8006/money-backend";
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
				.statusCode(204)
		
		;
	}

	@Test
	public void testPostCategory() {
		RestAssured
			.given()
				.body("{\"name\":\"Amazon-prime\"}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(201)
				.header("Location",RestAssured.baseURI  + baseResource + "/1" )
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
		RestAssured
			.given()
				.body("{\"name\":\"A\"}")
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(400)
				.contentType(ContentType.JSON)
				
			;
	}
	
}
