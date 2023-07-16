package resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ExpenseTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://172.23.176.1:8006/money-backend";
	}
	
	public final String baseResource = "/expense";
	
	@Test
	public void testGetExpenses(){
		RestAssured.given()
		.when()
			.get(baseResource)
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
		
	}
	
	@Test
	public void testFindExpense(){
		RestAssured.given()
		.when()
			.get(baseResource + "/1")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
		;
		
	}
	@Test
	public void testNotFoundExpense(){
		RestAssured.given()
		.when()
			.get(baseResource + "/255050505050")
		.then()
			.statusCode(404)
		;
		
	}
	@Test
	public void testPostExpense() {
		RestAssured.given()
			.body("{\r\n"
					+ "    \"description\": \"Salário trimestral\",\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymentDate\": null,\r\n"
					+ "    \"amount\": 6500.00,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 1\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(201)
			.contentType(ContentType.JSON)
		;
	}
	@Test
	public void testBadRequestExpense() {
		String userMessage = RestAssured.given()
			.body("{\r\n"
					+ "    \"description\": \"Salário trimestral\",\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymenttDate\": null,\r\n"
					+ "    \"amount\": 6500.00,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 1\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(400)
			.extract().path("userMessage")
			
		;
		assertEquals(userMessage, "message fail");
	}
	@Test
	public void testBadRequestPersonInativeExpense() {
		List<?> userMessage = RestAssured.given()
			.body("{\r\n"
					+ "    \"description\": \"Salário trimestral\",\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymentDate\": null,\r\n"
					+ "    \"amount\": 6500.00,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 2\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(400)
			.extract().path("userMessage")
			
		;
		assertEquals(userMessage.get(0), "Person no active or no exist");
	}
	
	@Test
	public void testBadRequestPersonNotExistsExpense() {
		List<?> userMessage = RestAssured.given()
			.body("{\r\n"
					+ "    \"description\": \"Salário trimestral\",\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymentDate\": null,\r\n"
					+ "    \"amount\": 6500.00,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 202200000\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(400)
			.extract().path("userMessage")
			
		;
		assertEquals(userMessage.get(0), "Person no active or no exist");
	}
	
	@Test
	public void testBadRequestNotNullDescriptionExpense() {
		List<?> userMessage = RestAssured.given()
			.body("{\r\n"
					+ "    \"description\": null,\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymentDate\": null,\r\n"
					+ "    \"amount\": 6500.00,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 2\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(400)
			.extract().path("userMessage")
			
		;
		assertEquals(userMessage.get(0), "Description is required");
	}
	
	@Test
	public void testBadRequestNotNullAmountExpense() {
		List<?> userMessage = RestAssured.given()
			.body("{\r\n"
					+ "    \"description\":\"Salário trimestral\",\r\n"
					+ "    \"dueDate\": \"10/06/2023\",\r\n"
					+ "    \"paymentDate\": null,\r\n"
					+ "    \"amount\": null,\r\n"
					+ "    \"observation\": \"Distribuição de lucros\",\r\n"
					+ "    \"type\": \"REVENUE\",\r\n"
					+ "    \"category\": {\r\n"
					+ "        \"cod\": 5\r\n"
					+ "    },\r\n"
					+ "    \"person\": {\r\n"
					+ "        \"cod\": 2\r\n"
					+ "    }\r\n"
				+ "}")
		  .contentType(ContentType.JSON)
		.when()
			.post(baseResource)
		.then()
			.statusCode(400)
			.extract().path("userMessage")
			
		;
		assertEquals(userMessage.get(0), "Amount is required");
	}
	
	@Test
	public void testBadRequestNotNullPersonExpense() {
		List<?> userMessage = RestAssured.given()
				.body("{\r\n"
						+ "    \"description\": \"Salário trimestral\",\r\n"
						+ "    \"dueDate\": \"10/06/2023\",\r\n"
						+ "    \"paymentDate\": null,\r\n"
						+ "    \"amount\": 6500.00,\r\n"
						+ "    \"observation\": \"Distribuição de lucros\",\r\n"
						+ "    \"type\": \"REVENUE\",\r\n"
						+ "    \"category\": {\r\n"
						+ "        \"cod\": 5\r\n"
						+ "    },\r\n"
						+ "    \"person\": {\r\n"
						+ "        \"cod\": null\r\n"
						+ "    }\r\n"
						+ "}")
			  .contentType(ContentType.JSON)
			.when()
				.post(baseResource)
			.then()
				.statusCode(400)
				.extract().path("userMessage")
				
			;
		assertEquals(userMessage.get(0), "The given argument not be null");
	}
}
