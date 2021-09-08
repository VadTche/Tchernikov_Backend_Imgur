package imgur.tests;

import imgur.src.main.Images;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.File;

import static imgur.src.main.EndPoints.UPLOAD_IMAGE;
import static imgur.src.main.Images.IMAGE_URL;
import static imgur.src.main.Images.NON_IMAGE_PDF;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class ImageTests extends BaseTest {
    String imageDeleteHash;
    RequestSpecification multiPartReqSpec;
    String base64Image;
    RequestSpecification imageRequestSpecification;

    //@BeforeEach
    //void setUp() throws IOException {
        //byte[] imageBytesArray = FileUtils.readFileToByteArray(new File(image.getPath()));
        //base64Image = Base64.getEncoder().encodeToString(imageBytesArray);

    @ParameterizedTest
    @EnumSource(value = Images.class, names = {"IMAGE_JPG_ORDINARY", "IMAGE_JPG_SMALL", "IMAGE_JPG_HD",
                                                "IMAGE_GIF", "IMAGE_BMP", "IMAGE_PNG", "IMAGE_PNG_1x1",
                                                "IMAGE_PNG_LESS_ONE_KB", "IMAGE_TIFF"})
    void uploadImageWithAllowedFormat(@org.jetbrains.annotations.NotNull Images image) {
        imageDeleteHash=  given()
                .headers("Authorization", token)
                .multiPart("image", new File(image.getPath()))
                .expect()
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


    @Test // Так и не смог разобраться почему этот тест в Postman проходит с ошибкой 400, а здесь падает с ошибками 500 и 255(
    void uploadNonImageTest() {
        imageDeleteHash = given()
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