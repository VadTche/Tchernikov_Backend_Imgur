package imgur.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static imgur.src.main.EndPoints.UPLOAD_IMAGE;
import static imgur.src.main.Images.IMAGE_JPG_ORDINARY;
import static imgur.src.main.Images.IMAGE_JPG_SMALL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class DeleteImageTests extends BaseTest{

    String imageDeleteHash;

    @BeforeEach
    void setUp() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", new File(IMAGE_JPG_ORDINARY.getPath()))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.type", equalTo(IMAGE_JPG_ORDINARY.getFormat()))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

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

    @Test //совсем запутался тут с авторизацией по id)
    void deleteExistentNonAuthTest() {
        given()
                .header("Authorization", userId)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }
}
