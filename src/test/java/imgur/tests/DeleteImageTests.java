package imgur.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest{
    String imageDeleteHash;
    @BeforeEach
    void setSpiderman() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/Spiderman (866x738).png"))
                .expect()
                .statusCode(200)
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void deleteSpiderman() {
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }
}
