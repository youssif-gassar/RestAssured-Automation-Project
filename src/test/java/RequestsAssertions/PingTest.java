package RequestsAssertions;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PingTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void testPingEndpoint() {
        System.out.println("Checking-Health");

        given()
                .when()
                .get("/ping")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body(equalTo("Created"))
                .time(lessThan(1000L));
    }
}