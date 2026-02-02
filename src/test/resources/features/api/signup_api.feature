@api @regressionAPI @signupAPI
Feature: Signup API tests using Rest-Assured for Demoblaze
  As a QA engineer
  I want to validate the Signup API of Demoblaze application
  So that new users can register and create an account

  Background:
    Given the API base URI is "https://api.demoblaze.com"
    And the request header "Content-Type" is "application/json"

  Scenario Outline: Successful signup with valid credentials
    When I send a POST request to "/signup" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 200
    And the response body should contain key "errorMessage" with value "Sign up successful."

    Examples:
      | username          | password     |
      | testuser_456      | SecurePass1! |

  Scenario Outline: Signup with existing username returns error
    When I send a POST request to "/signup" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 200
    And the response body should contain key "errorMessage" with value "This user already exist."

    Examples:
      | username     | password     |
      | jyotiuser123 | Password@123 |

  Scenario Outline: Signup with empty username returns error
    When I send a POST request to "/signup" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 500

    Examples:
      | username | password     |
      |          | Password@123 |

  Scenario Outline: Signup with empty password returns error
    When I send a POST request to "/signup" with JSON body:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    Then the response status code should be 200

    Examples:
      | username    | password |
      | newuser789  |          |