package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File; // <-- 1. إضافة جديدة

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingIDs {
    File createBody = new File("src/test/resources/newbooking.json");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON);
    }

    @Test(priority = 1)
    public void testGetBookingIds() {
        System.out.println("Running testGetBookingIds...");
        given()
                .auth().basic("admin", "password123")
                .when()
                .get("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()))
                .body("[0].bookingid", notNullValue());
    }

    @Test(priority = 2)
    public void testFilterBookingsByFirstname() {
        int bookingIdToFind = given()
                .auth().basic("admin", "password123")
                .body(createBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        System.out.println("Created booking " + bookingIdToFind + " to filter for");

        given()
                .auth().basic("admin", "password123")
                .queryParam("firstname", "Youssef") //
                .when()
                .get("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("$", not(empty()))
                .body("bookingid", hasItem(bookingIdToFind));
    }
    @Test(priority = 3)
    public void testFilterBookingsWithNonExistentName() {
        System.out.println("Running testFilterBookingsWithNonExistentName...");

        given()
                .auth().basic("admin", "password123")
                .queryParam("firstname", "mohamed")
                .when()
                .get("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("$", empty());
    }
    @Test(priority = 1)
    public void testGetBookingIds_WithoutAuth() {
        System.out.println("Running testGetBookingIds_WithoutAuth");
        given()
                .when()
                .get("/booking")
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("$", not(empty()));
    }
}