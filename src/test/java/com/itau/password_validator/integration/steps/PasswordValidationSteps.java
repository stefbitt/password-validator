package com.itau.password_validator.integration.steps;

import com.itau.password_validator.model.response.PasswordValidationResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordValidationSteps {

    private String password;
    private ResponseEntity<PasswordValidationResponse> response;
    private PasswordValidationResponse responseBody;

    @LocalServerPort
    private int port;

    private final String END_POINT = "/api/v1/password/validate";

    @Given("the password {string}")
    public void the_password(String password) {
        this.password = password.isBlank() ? null : password;
    }

    @When("the password is sent to the API")
    public void the_password_is_sent_to_the_api() {

        String url = "http://localhost:" + port + END_POINT;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"password\": " + (password == null ? null : "\"" + password + "\"") + "}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        try {
            response = restTemplate.postForEntity(url, entity, PasswordValidationResponse.class);
        } catch (HttpClientErrorException ex) {
            response = ResponseEntity.status(ex.getStatusCode()).body(PasswordValidationResponse.builder()
                    .isValid(false)
                    .errors(Collections.singletonList(ex.getMessage()))
                    .build());
        }

        try {
            responseBody = response.getBody();
        } catch (Exception e) {
            responseBody = null;
        }
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode().value());
    }

    @Then("the field isValid should be {word}")
    public void the_field_should_be(String expectedValue) {
        boolean actual = responseBody.isValid();
        boolean expected = Boolean.parseBoolean(expectedValue);
        assertEquals(expected, actual);
    }

    @Then("the errors list size should be {int}")
    public void the_errors_list_size_should_be(int expectedSize) {
        List<String> errors = responseBody.getErrors();
        assertEquals(expectedSize, errors == null ? 0 : errors.size());
    }
}
