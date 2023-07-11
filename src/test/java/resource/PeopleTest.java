package resource;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PeopleTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8006/money-backend";
	}
	
	public final String baseResource = "/person";
	
	@Test
	public void testGetPeople() {
		RestAssured
			.given()
				.log().all()
			.when()
				.get(baseResource)
			.then()
				.statusCode(200);
	}
	
}

