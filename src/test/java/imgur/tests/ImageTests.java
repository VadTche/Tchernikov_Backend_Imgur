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
/*
    @Test
    void uploadOneHundredSymbols() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", IMAGE_JPG_SMALL.getPath())
                .multiPart("title", "Fantasy Worlds")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(1600))
                .body("data.height", CoreMatchers.equalTo(900))
                .body("data.title", CoreMatchers.equalTo("Fantasy Worlds"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadBullets() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Bullets_(3840x2160).jpeg"))
                .multiPart("title", "Bullets")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(3840))
                .body("data.height", CoreMatchers.equalTo(2160))
                .body("data.title", CoreMatchers.equalTo("Bullets"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadNonLetters() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/@*&%$#.gif"))
                .multiPart("title", "Autumn")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(360))
                .body("data.height", CoreMatchers.equalTo(640))
                .body("data.title", CoreMatchers.equalTo("Autumn"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadOneLetterName() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/S.bmp"))
                .multiPart("title", "Sample")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(1280))
                .body("data.height", CoreMatchers.equalTo(853))
                .body("data.title", CoreMatchers.equalTo("Sample"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadSpiderman() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Человек_Паук.png"))
                .multiPart("title", "Spiderman")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(866))
                .body("data.height", CoreMatchers.equalTo(738))
                .body("data.title", CoreMatchers.equalTo("Spiderman"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test // Так и не смог разобраться почему этот тест в Postman проходит с ошибкой 400, а здесь падает с ошибками 500 и 255(
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
*/
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