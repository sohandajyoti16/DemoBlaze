@regression @login
Feature: Login Functionality
  As a user
  I want to login to DemoBlaze
  So that I can access my account

  Background:
    Given user navigates to DemoBlaze homepage

  @smoke
  Scenario: Successful login with valid credentials
    When user clicks on Login link
    And user enters username "jyotiuser123" and password "Password@123"
    Then user should be logged in successfully
    And user should see welcome message

  Scenario: Login with invalid credentials
    When user clicks on Login link
    And user enters username "invaliduser" and password "wrongpass"
    Then user should see an error message for invalid credentials
    And user accepts the alert

  @smoke
  Scenario: Logout functionality
    When user clicks on Login link
    And user enters username "jyotiuser123" and password "Password@123"
    Then user should be logged in successfully
    When user clicks on Logout
    Then user should see the homepage