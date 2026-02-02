@api @regressionAPI @loginAPI
Feature: Login API tests using Rest-Assured for Demoblaze
  As a QA engineer
  I want to validate the Login API of Demoblaze application
  So that users can authenticate and receive an auth token

  Background:
    Given the API base URI is "https://api.demoblaze.com"
    And the request header "Content-Type" is "application/json"

  Scenario Outline: Successful login returns auth token
    When I send a POST request to "/check" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 200
    And the response body should have key "token"

    Examples:
      | username     | password     |
      | jyotiuser123 | Password@123 |

  Scenario Outline: Login with invalid credentials returns appropriate error message
    When I send a POST request to "/login" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 200
    Then the response body should contain key "errorMessage" with value "Wrong password."

    Examples:
      | username     | password |
      | invalid_user | bad_pass |