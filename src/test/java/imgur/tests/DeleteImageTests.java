package imgur.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest{
    String imageDeleteHash;
    @BeforeEach
    void setUp() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/Spiderman.png"))
                .expect()
                .statusCode(200)
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void deleteExistentImageTest() {
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
