package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class updateBooking {

    File createBody = new File("src/test/resources/newbooking.json");
    File updateBody = new File("src/test/resources/fullUpdate.json");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test
    public void testUpdateBooking() {
        System.out.println("Running testUpdateBooking...");

        int bookingIdToUpdate = given()
                .auth().basic("admin", "password123")
                .body(createBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        System.out.println("Booking ID Created, now updating: " + bookingIdToUpdate);


        given()
                .auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON)
                .body(updateBody)
        .when()
                .put("/booking/"+bookingIdToUpdate)
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)

                .body("firstname", equalTo("Full Update"))
                .body("lastname", equalTo("Done"))
                .body("totalprice", equalTo(999))
                .body("depositpaid", equalTo(true))
                .body("additionalneeds", equalTo("Dinner"))
                .body("bookingdates.checkin", equalTo("2024-01-01"))
                .body("bookingdates.checkout", equalTo("2024-01-05"));
    }
}