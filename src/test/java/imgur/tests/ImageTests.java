package imgur.tests;

import imgur.src.main.Images;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.File;

import static imgur.src.main.EndPoints.UPLOAD_IMAGE;
import static imgur.src.main.Images.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class ImageTests extends BaseTest {
    String imageDeleteHash;

    @ParameterizedTest
    @EnumSource(value = Images.class, names = {"IMAGE_JPG_ORDINARY", "IMAGE_JPG_SMALL", "IMAGE_JPG_HD",
            "IMAGE_GIF", "IMAGE_BMP", "IMAGE_PNG",
            "IMAGE_PNG_LESS_ONE_KB", "IMAGE_TIFF"})
    void uploadAllowedFormatTest(imgur.src.main. Images image) {
        imageDeleteHash=  given()
                .spec(requestSpecification)
                .multiPart("image", new File(image.getPath()))
                .expect()
                .spec(positiveResponseSpecification)
                .body("data.type", equalTo(image.getFormat()))
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
    void uploadUrlTest() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_URL.getPath())
                .expect()
                .spec(positiveResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.deletehash");
    }


    /*------------------------------------------------------------------------------
    Имеется ли возможность эти ассершены двух негативных тестов ниже завести в negativeResponseSpecification?

    java.lang.AssertionError: 2 expectations failed.
    JSON path success doesn't match.
    Expected: is <true>
    Actual: <false>

    JSON path status doesn't match.
    Expected: <200>
    Actual: <400>
    ------------------------------------------------------------------------------*/
    @Test
    void uploadNonImageTest() {
        given()
                .spec(requestSpecification)
                .multiPart("image", new File(NON_IMAGE_PDF.getPath()))
                .expect()
                .spec(negativeResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();
    }

    @Test
    void uploadOverSizeTest() {
        given()
                .spec(requestSpecification)
                .multiPart("image", new File(IMAGE_JPG_OVER_SIZE.getPath()))
                .expect()
                .spec(negativeResponseSpecification)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();
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