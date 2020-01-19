package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import cucumber.api.java8.En;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;



public class CommonSteps extends AbstractSteps implements En {
    public CommonSteps() {
        Then("the request {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();

            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

        Then("^response is not empty$", () -> {
            Response response = testContext().getResponse();
            assertThat(!response.getBody().asString().isEmpty());
        });
    }
}
