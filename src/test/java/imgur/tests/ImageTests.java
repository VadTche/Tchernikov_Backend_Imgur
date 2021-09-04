package imgur.tests;

import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class ImageTests extends BaseTest {
    String imageDeleteHash;

    @Test
    void uploadCandyman() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .contentType("multipart/form-data")
                .multiPart("image", new File("src/test/resources/Candyman (600x950).jpeg"),"multipart/form-data")
                .multiPart("title", "Candyman","multipart/form-data")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(600))
                .body("data.height", CoreMatchers.equalTo(950))
                .body("data.title", CoreMatchers.equalTo("Candyman"))
                .body("data.link", CoreMatchers.anything("data.id"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadNonImage() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/patroni.pdf"))
                .expect()
                .statusCode(400)
                .body("success", CoreMatchers.is(false))
                .body("data.error", CoreMatchers.equalTo("Could not process upload!"))
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