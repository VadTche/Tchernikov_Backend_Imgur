package imgur.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static imgur.src.main.EndPoints.UPLOAD_IMAGE;
import static imgur.src.main.Images.IMAGE_JPG_ORDINARY;
import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest{

    String imageDeleteHash;

    @BeforeEach
    void setUp() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(IMAGE_JPG_ORDINARY.getPath()))
                .when()
                .post(UPLOAD_IMAGE)
                .jsonPath()
                .getString("data.deletehash");
    }
    //авторизация по токену
    @Test
    void deleteExistentAuthTest() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }

    //авторизация по id, совсем запутался тут)
 /*   @Test
    void deleteExistentNonAuthTest() {
        given()
                .header("Authorization", userId)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }*/
}
