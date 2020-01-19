package ch.heigvd.amt.projectTwo.api.bdd.stepdefs;

import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Steps extends AbstractSteps implements En {
    private String authorization = "";

    @Autowired
    private JwtTokenUtil tokenUtil;


    public Steps() {
    }
}
