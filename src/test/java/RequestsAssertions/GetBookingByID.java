package RequestsAssertions;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingByID {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test(dependsOnGroups = "Create")
    public void testGetCreatedBooking() {

        int idToTest = CreateBooking.createdBookingId;

        System.out.println("Running testGetCreatedBooking for ID: " + idToTest);

        given()
                .when()
                .get("/booking/" + idToTest)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo("Youssef"))
                .body("lastname", equalTo("Bassem"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2024-01-01"));
    }
    @Test
    public void testGetBookingWithNonExistentID() {
        System.out.println("Running testGetBookingWithNonExistentID...");

        int fakeId = 99999999;

        given()
                .when()
                .get("/booking/" +fakeId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(404);
    }
}