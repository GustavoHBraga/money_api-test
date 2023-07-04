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
	
	public final String baseResource = "/people";
	
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
	
	@Test
	public void testCreatePeople() {
		String peopleTest = "{\r\n"
				+ "    \"name\": \"Gustavo Ninja gaiden 3\",\r\n"
				+ "    \"informationAdress\": {\r\n"
				+ "        \"adress\": \"Rua rio paranapanema\",\r\n"
				+ "        \"number\": 147,\r\n"
				+ "        \"cep\": \"1234-56\",\r\n"
				+ "        \"district\": \"IAPI\",\r\n"
				+ "        \"city\": \"Osasco\",\r\n"
				+ "        \"state\": \"Sao Paulo\"\r\n"
				+ "    },\r\n"
				+ "    \"isActive\": true\r\n"
				+ "}";
		
		RestAssured
			.given()
				.body(peopleTest)
				.contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(201)
				.header("Location",RestAssured.baseURI  + baseResource + "/1" )
			;
	}
}

