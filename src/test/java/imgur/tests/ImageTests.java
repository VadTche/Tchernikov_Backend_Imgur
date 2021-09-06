package imgur.tests;

import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static imgur.src.main.EndPoints.UPLOAD_IMAGE;
import static imgur.src.main.Images.IMAGE_JPG_SMALL;
import static io.restassured.RestAssured.given;


public class ImageTests extends BaseTest {
    String imageDeleteHash;
    RequestSpecification multiPartReqSpec;
    String base64Image;
    RequestSpecification imageRequestSpecification;


    @Test
    void uploadCyrillic() {
        imageDeleteHash = given()
                .spec(requestSpecification)
                .multiPart("image", IMAGE_JPG_SMALL.getPath())
                //.multiPart("title", "Candyman")
                .expect()
                .spec(positiveResponseSpecification)
                //.body("data.width", CoreMatchers.equalTo(600))
                //.body("data.height", CoreMatchers.equalTo(950))
                //.body("data.title", CoreMatchers.equalTo("Candyman"))
                //.body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("data.deletehash");
    }
    @Test
    void uploadOneHundredSymbols() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Worlds (1600x900).jpeg"))
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
                .multiPart("image", new File("src/test/resources/Bullets (3840x2160).jpeg"))
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
                .multiPart("image", new File("src/test/resources/Spiderman.png"))
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