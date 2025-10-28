package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class deleteBooking {
    File createBody = new File("src/test/resources/newbooking.json");
    public static int bookingIdToDelete;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test(groups = "Delete")
    public void testDeleteBooking() {
        System.out.println("Running testDeleteBooking...");
        bookingIdToDelete = given()
                .auth().basic("admin", "password123")
                .body(createBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        System.out.println("Booking ID Created, now deleting: " + bookingIdToDelete);

        given()
                .auth().preemptive().basic("admin", "password123")
                .when()
                .delete("/booking/" + bookingIdToDelete)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201); //
    }

    @Test(dependsOnGroups = "Delete")
    public void testVerifyBookingIsDeleted() {
        System.out.println("Verifying booking is gone - ID: " + bookingIdToDelete);

        given()
                .when()
                .get("/booking/" + bookingIdToDelete)
                .then()
                .log().all()
                .assertThat()
                .statusCode(404);
    }
}