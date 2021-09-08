package imgur.tests;

import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static imgur.src.main.EndPoints.GET_ACCOUNT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AccountTests extends BaseTest {
    ResponseSpecification accountResponseSpec;

    @Test
    void getAccountPositiveTest() {
        accountResponseSpec = positiveResponseSpecification
                .expect()
                .body("data.url", equalTo(username));

        given(requestSpecification, positiveResponseSpecification)
                .get(GET_ACCOUNT, username)
                .prettyPeek();
                //.then()
                //.extract()
                //.as(AccountResponse.class);
        //assertThat(response.getData().getId(), equalTo(userId));
    }

    @Test
    void getAccountSettingsTest() {
        given()
                .header("Authorization", token)
                .log()
                .all()
                .expect()
                .body("success", CoreMatchers.is(true))
                .body("data.account_url", equalTo(username))
                .when()
                .get("account/"+username+"/settings")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
