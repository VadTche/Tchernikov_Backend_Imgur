package imgur.tests;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class ImageTests extends BaseTest {
    String imageDeleteHash;
    String imageHash;

    @Test
    void uploadCandyman() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Candyman (600x950).jpeg"))
                .multiPart("title", "Candyman")
                .expect()
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("data.width", CoreMatchers.equalTo(600))
                .body("data.height", CoreMatchers.equalTo(950))
                .body("data.title", CoreMatchers.equalTo("Candyman"))
                .body("data.id", CoreMatchers.anything("data.link"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }
    @Test
    void uploadFantasyWorlds() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Fantasy Worlds (1600x900).jpeg"))
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
    void uploadAutumn() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Autumn.gif"))
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
    void uploadSample() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .multiPart("image", new File("src/test/resources/Sample.bmp"))
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

    @Test // Так и не смог разобраться почему этот тест падает(
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