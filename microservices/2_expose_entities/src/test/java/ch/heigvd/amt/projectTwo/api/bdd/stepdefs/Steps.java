package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Steps extends AbstractSteps implements En {
  /*  private String authorization = "";
    private StadiumDetails stadiumAdded;
    private TeamDetails teamAdded;
    private MatchDetails matchAdded;

    @Autowired
    private JwtTokenProvider provider;

    private String stadiumsUrl = "/api/app/stadiums";
    private String matchesUrl = "/api/app/matches";
    private String cancellationsUrl = "/api/app/cancellations";
    private String teamsUrl = "/api/app/teams";

    public Steps() {
        Given("user is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, false, id);
        });
        Given("admin is authenticated with mail \"([^\"]*)\" and id \"(\\d+)\"", (String mail, Integer id) -> {
            // initial request, reset context
            testContext().reset();
            this.authorization = provider.createTestToken(mail, true, id);
        });

        When("^user get matches$", () -> {
            executeGet(matchesUrl, this.authorization);
        });

        When("^user update match (\\d+) with the following attributes$", (Integer matchId, DataTable matchDt) -> {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(matchId));
            executePut(matchesUrl + "/" + matchId, this.authorization);
        });
        Then("^we can cancel this match$", () -> {
            super.testContext().setPayload(matchAdded);
            executePost(cancellationsUrl, this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add match with following attributes$", (DataTable matchDt) -> {
            List<Match> matchList = matchDt.asList(Match.class);
            super.testContext().setPayload(matchList.get(0));
            executePost(matchesUrl, this.authorization);

            Response response = testContext().getResponse();
            // If we were able to add ,then we store it
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300)
                matchAdded = response.getBody().as(MatchDetails.class);
        });

        When("^user get match (\\d+)$", (Integer matchId) -> {
            executeGet(matchesUrl + "/" + matchId, this.authorization);
        });

        Then("^we can get this match$", () -> {
            executeGet(matchesUrl + "/" + matchAdded.getId(), this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
        When("^user get stadiums$", () -> {
            // Due to the large number of stadiums, we can't display them all, so we don't log the body of the response
            executeGet(stadiumsUrl, this.authorization);
        });

        When("^user update stadium (\\d+) with the following attributes$", (Integer stadiumId, DataTable stadiumDt) ->
        {
            List<Stadium> stadiumList = stadiumDt.asList(Stadium.class);
            super.testContext().setPayload(stadiumList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(stadiumId));
            executePut(stadiumsUrl + "/" + stadiumId, this.authorization);
        });
        Then("^we can delete this stadium$", () -> {
            executeDelete(stadiumsUrl + "/" + stadiumAdded.getId(), this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add stadium with following attributes$", (DataTable stadiumDt) -> {
            List<Stadium> stadiumList = stadiumDt.asList(Stadium.class);
            super.testContext().setPayload(stadiumList.get(0));
            executePost(stadiumsUrl, this.authorization);

            Response response = testContext().getResponse();
            // If we were able to add ,then we store it
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300)
                stadiumAdded = response.getBody().as(StadiumDetails.class);
        });

        When("^user get stadium (\\d+)$", (Integer stadiumId) -> {
            executeGet(stadiumsUrl + "/" + stadiumId, this.authorization);
        });
        Then("^we can get this stadium$", () -> {
            executeGet(stadiumsUrl + "/" + stadiumAdded.getId(), this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
        When("^user get teams$", () -> {
            // Due to the large number of teams, we can't display them all, so we don't log the body of the response
            executeGet(teamsUrl, this.authorization);
        });

        When("^user update team (\\d+) with the following attributes$", (Integer teamId, DataTable teamDt) ->
        {
            List<Team> teamList = teamDt.asList(Team.class);
            super.testContext().setPayload(teamList.get(0));
            Logger logger = LoggerFactory.getLogger(Steps.class);

            logger.debug(Integer.toString(teamId));
            executePut(teamsUrl + "/" + teamId, this.authorization);
        });
        Then("^we can delete this team$", () -> {
            executeDelete(teamsUrl + "/" + teamAdded.getId(), this.authorization);
            Response deleteResponse = testContext().getResponse();
            assertThat(deleteResponse.getStatusCode() == 200);
        });
        When("^user add team with following attributes$", (DataTable teamDt) -> {
            List<Team> teamList = teamDt.asList(Team.class);
            super.testContext().setPayload(teamList.get(0));
            executePost(teamsUrl, this.authorization);

            Response response = testContext().getResponse();
            // If we were able to add ,then we store it
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300)
                teamAdded = response.getBody().as(TeamDetails.class);
        });

        When("^user get team (\\d+)$", (Integer teamId) -> {
            executeGet(teamsUrl + "/" + teamId, this.authorization);
        });
        Then("^we can get this team$", () -> {
            executeGet(teamsUrl + "/" + teamAdded.getId(), this.authorization);
            Response getResponse = testContext().getResponse();
            assertThat(getResponse.getStatusCode() == 200);
        });
        Then("^we can get matches$", () -> {
            executeGet(matchesUrl, this.authorization);
        });
    }*/
}
