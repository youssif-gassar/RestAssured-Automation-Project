package RequestsAssertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.containsString;

public class GetToken {
    File body = new File("src/test/resources/userandpassword.json");
    public static String token;
    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void verifyValidAuthentication() {

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("token", not(emptyOrNullString()))
                .time(lessThan(3000L))
                .header("Content-Type", containsString("application/json"))
                .extract().path("token");
    }
    @Test
    public void testGetToken_WithWrongUsername() {
        String wrongUserBody = "{\n" +
                "    \"username\": \"WrongAdminUser\",\n" +
                "    \"password\": \"password123\"\n" +
                "}";

        System.out.println("Running testGetToken_WithWrongUsername...");

        given()
                .contentType(ContentType.JSON)
                .body(wrongUserBody)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    public void testGetToken_WithWrongPassword() {

        String wrongPasswordBody = "{\n" +
                "    \"username\": \"admin\",\n" +
                "    \"password\": \"WrongPassword123\"\n" +
                "}";
        System.out.println("Running testGetToken_WithWrongPassword...");

        given()
                .contentType(ContentType.JSON)
                .body(wrongPasswordBody)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}