package com.demoblaze.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class LoginApiSteps {

    private String baseUri;
    private io.restassured.specification.RequestSpecification request;
    private Response response;

    @Given("the API base URI is {string}")
    public void the_api_base_uri_is(String uri) {
        this.baseUri = uri;
    }

    @Given("the request header {string} is {string}")
    public void the_request_header_is(String name, String value) {
        if (request == null) {
            request = given().baseUri(baseUri);
        }
        request = request.header(name, value);
    }

    @When("I send a POST request to {string} with JSON body:")
    public void i_send_a_post_request_with_json_body(String path, String body) {
        if (request == null) {
            request = given().baseUri(baseUri);
        }
        response = request
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatus) {
        assertNotNull("Response is null - POST might have failed", response);
        assertEquals((int) expectedStatus, response.getStatusCode());
    }

    @Then("the response body should have key {string}")
    public void the_response_body_should_have_key(String key) {
        assertNotNull("Response is null - POST might have failed", response);
        String body = response.asString();
        assertTrue("Expected key '" + key + "' in response but got: " + body, body.contains(key));
    }

    @Then("the response body should contain key {string} with value {string}")
    public void the_response_body_should_contain_key_with_value(String key, String expectedValue) {
        assertNotNull("Response is null - POST might have failed", response);
        String actualValue = response.jsonPath().getString(key);
        assertNotNull("Key '" + key + "' not found in response", actualValue);
        assertEquals("Value mismatch for key '" + key + "'", expectedValue, actualValue);
    }
}
