package imgur.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ImageTests extends BaseTest {
    String imageDeleteHash;

    @Test
    void uploadImageFileTest() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/img.png"))
                .expect()
                .statusCode(200)
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .then()
                .statusCode(200);
    }
}