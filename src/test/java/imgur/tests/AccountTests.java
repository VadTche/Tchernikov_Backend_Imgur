package imgur.tests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import imgur.src.main.AccountResponse;

import static imgur.src.main.EndPoints.GET_ACCOUNT;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class AccountTests extends BaseTest {
    ResponseSpecification accountResponseSpec;

    @Test
    void getAccountPositiveTest() {
        accountResponseSpec = positiveResponseSpecification
                .expect()
                .body("data.url", equalTo(username));

        AccountResponse response = given(requestSpecification, accountResponseSpec)
                .get(GET_ACCOUNT, username)
                .prettyPeek()
                .then()
                .extract()
                .as(AccountResponse.class);
        assertThat(response.getData().getId(), equalTo(userId));
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
