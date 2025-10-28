package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateBooking {
    File bookingBody = new File("src/test/resources/newbooking.json");
    public static int createdBookingId;
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test(groups = "Create")
    public void testCreateBooking() {
        System.out.println("Running testCreateBooking...");

        CreateBooking.createdBookingId=
                given().auth().basic("admin", "password123")
                .body(bookingBody)
        .when()
                .post("/booking")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("booking.firstname", equalTo("Youssef"))
                .body("booking.lastname", equalTo("Bassem"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.additionalneeds", equalTo("Breakfast"))
                .body("booking.bookingdates.checkin", equalTo("2024-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2024-01-05"))
                .extract().path("bookingid");
    }
    @Test
    public void verifyInvalidCreationFirstNameNotString() {
        String badBody = "{\n" +
                "    \"firstname\" : 123,\n" +
                "    \"lastname\" : \"Bassem\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-05\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        System.out.println("Running verifyInvalidCreationFirstNameNotString...");

        given()
                .auth().basic("admin", "password123")
                .body(badBody)
                .when()
                .post("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(500);
    }

    @Test
    public void verifyInvalidCreationLastNameNotString() {
        String badBody = "{\n" +
                "    \"firstname\" : \"Youssef\",\n" +
                "    \"lastname\" : false,\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-05\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        System.out.println("Running verifyInvalidCreationLastNameNotString...");

        given()
                .auth().basic("admin", "password123")
                .body(badBody)
                .when()
                .post("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(500);
    }

    @Test
    public void verifyInvalidCreationTotalPriceBoolean() {
        String badBody = "{\n" +
                "    \"firstname\" : \"Youssef\",\n" +
                "    \"lastname\" : \"Bassem\",\n" +
                "    \"totalprice\" : \"One Hundred Eleven\",\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-05\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        System.out.println("Running verifyInvalidCreationTotalPriceBoolean...");

        given()
                .auth().basic("admin", "password123")
                .body(badBody)
                .when()
                .post("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(500);
    }

    @Test
    public void verifyInvalidCreationDateInteger() {
        String badBody = "{\n" +
                "    \"firstname\" : \"Youssef\",\n" +
                "    \"lastname\" : \"Bassem\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : 20240101,\n" +
                "        \"checkout\" : \"2024-01-05\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        System.out.println("Running verifyInvalidCreationDateInteger...");

        given()
                .auth().basic("admin", "password123")
                .body(badBody)
                .when()
                .post("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(500);
    }
}
