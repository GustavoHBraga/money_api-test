package resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PersonTest {

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
	@Test
	public void testPostPerson(){
		String headerLocation = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo H BRAGA\",\r\n"
						+ "    \"active\": true,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(201)
					.extract().header("Location")
					;
		
		System.out.println(headerLocation);
		
		RestAssured.given()
		.when()
			.delete(headerLocation)
		.then()
			.statusCode(204);
			
	}
	@Test
	public void testPostPersonBadRequestActive() {
		List<?> userMessage = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo H BRAGA\",\r\n"
						+ "    \"active\": null,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(400)
					.extract().path("userMessage")
					;
		
		assertEquals(userMessage.get(0), "Active is required");
		
			
	}
	@Test
	public void testPostPersonBadRequestName() {
		List<?> userMessage = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": null,\r\n"
						+ "    \"active\": null,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(400)
					.extract().path("userMessage")
					;
		
		assertEquals(userMessage.get(0), "Name is required");
	}
	@Test
	public void testUpdatePerson() {
		String locationFistPerson = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo H BRAGA\",\r\n"
						+ "    \"active\": true,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(201)
					.extract().header("Location")
					;
		
		System.out.println(locationFistPerson);
		
		String nameUpdate = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo Super Braga\",\r\n"
						+ "    \"active\": true,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.put(locationFistPerson)
				.then()
					.statusCode(200)
					.extract().path("name")
					;
		
		assertEquals(nameUpdate, "Gustavo Super Braga");
	}
	@Test
	public void testUpdateActivePerson() {
		String locationFistPerson = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo H BRAGA\",\r\n"
						+ "    \"active\": true,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(201)
					.extract().header("Location")
					;
		
		
		boolean activeUpdate = RestAssured.given()
				.body("false")
				.contentType(ContentType.JSON)
				.when()
					.put(locationFistPerson + "/active")
				.then()
					.statusCode(200)
					.extract().path("active")
					;
		
		assertEquals(activeUpdate, false);
	}
	@Test
	public void testUpdateAddressPerson() {
		String locationFistPerson = RestAssured.given()
				.body("{\r\n"
						+ "   \"name\": \"Gustavo H BRAGA\",\r\n"
						+ "    \"active\": true,\r\n"
						+ "    \"informationAdress\": {\r\n"
						+ "        \"address\": \"Rua das palmeiras\",\r\n"
						+ "        \"number\": 145,\r\n"
						+ "        \"complement\": \"Apartamento 95\",\r\n"
						+ "        \"district\": \"IAPO\",\r\n"
						+ "        \"cep\": \"0000011\",\r\n"
						+ "        \"city\": \"OSASCO\",\r\n"
						+ "        \"state\": \"SAO PAULO\"\r\n"
						+ "    }\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.post(baseResource)
				.then()
					.statusCode(201)
					.extract().header("Location")
					;
		
		System.out.println(locationFistPerson);
		String address = RestAssured.given()
				.body("{\r\n"
						+ "    \"address\": \"Chaves e encanto\",\r\n"
						+ "    \"number\": 555,\r\n"
						+ "    \"cep\": \"1895-6\",\r\n"
						+ "    \"city\": \"Osasco\",\r\n"
						+ "    \"district\": \"IAPI\",\r\n"
						+ "    \"state\": \"Sao Paulo\"\r\n"
						+ "}")
				.contentType(ContentType.JSON)
				.when()
					.put(locationFistPerson + "/adress")
				.then()
					.statusCode(200)
					.extract().path("informationAdress.address")
					;
		assertEquals(address, "Chaves e encanto");
	}
}

