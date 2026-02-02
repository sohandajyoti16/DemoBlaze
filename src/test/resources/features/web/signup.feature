@regression @signup
Feature: Sign Up Functionality
  As a new user
  I want to sign up on DemoBlaze
  So that I can create an account

  Background:
    Given user navigates to DemoBlaze homepage

  Scenario: Successful signup with valid details
    When user clicks on Sign up link
    And user enters signup username "newuser456" and signup password "NewPass@123"
    Then user should see signup success message

  Scenario Outline: Signup with various invalid inputs
    When user clicks on Sign up link
    And user enters signup username "<username>" and signup password "<password>"
    Then user should see signup error message
    And user accepts the alert

    Examples:
      | username | password |
      |          | Pass123  |
      | user123  |          |