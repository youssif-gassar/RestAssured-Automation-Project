package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class patchBooking {
    File createBody = new File("src/test/resources/newbooking.json");
    File patchBody = new File("src/test/resources/patchbooking.json");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test
    public void testPartialUpdateBooking() {
        System.out.println("Running testPartialUpdateBooking...");

        int bookingIdToPatch = given()
                .auth().basic("admin", "password123")
                .body(createBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        System.out.println("Booking ID Created, now patching: " + bookingIdToPatch);

        given()

                .auth().preemptive().basic("admin", "password123")
                .body(patchBody)
                .when()

                .patch("/booking/" + bookingIdToPatch)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)

                .body("firstname", equalTo("Partial update"))
                .body("lastname", equalTo("Done"))
                .body("totalprice", equalTo(777))
                .body("depositpaid", equalTo(true));
    }
}