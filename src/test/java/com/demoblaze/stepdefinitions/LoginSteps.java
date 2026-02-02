package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginSteps {
    private TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @When("user clicks on Login link")
    public void userClicksOnLoginLink() {
        ExtentReportManager.logInfo("Clicking on Login link");
        context.getHomePage().clickLogin();
        ExtentReportManager.logPass("Successfully clicked on Login link");
    }

    @When("user enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        ExtentReportManager.logInfo("Entering username: <b>" + username + "</b> and password");
        context.getLoginPage().login(username, password);
        ExtentReportManager.logPass("Successfully entered login credentials");
    }

    @Then("user should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean isLoggedIn = context.getHomePage().isLoggedIn();
        if (isLoggedIn) {
            ExtentReportManager.logPass("User logged in successfully");
        } else {
            ExtentReportManager.logFail("User login failed - User is not logged in");
        }
        Assert.assertTrue("User is not logged in", isLoggedIn);
    }

    @Then("user should see welcome message")
    public void userShouldSeeWelcomeMessage() {
        String welcomeMsg = context.getHomePage().getWelcomeMessage();
        ExtentReportManager.logInfo("Welcome message: <b>" + welcomeMsg + "</b>");
        boolean containsWelcome = welcomeMsg.contains("Welcome");
        if (containsWelcome) {
            ExtentReportManager.logPass("Welcome message displayed successfully");
        } else {
            ExtentReportManager.logFail("Welcome message not displayed");
        }
        Assert.assertTrue("Welcome message not displayed", containsWelcome);
    }

    @When("user clicks on Logout")
    public void userClicksOnLogout() {
        ExtentReportManager.logInfo("Clicking on Logout link");
        context.getHomePage().clickLogout();
        ExtentReportManager.logPass("Successfully clicked on Logout link");
    }

    @Then("user should see an error message")
    public void userShouldSeeAnErrorMessage() {
        try {
            Thread.sleep(1000);
            String alertText = context.getLoginPage().getAlertText();
            ExtentReportManager.logInfo("Alert message received: <b>" + alertText + "</b>");
            boolean isErrorDisplayed = alertText.contains("User does not exist") || alertText.contains("Wrong password");
            if (isErrorDisplayed) {
                ExtentReportManager.logPass("Error message displayed as expected");
            } else {
                ExtentReportManager.logFail("Expected error message not displayed");
            }
            context.getLoginPage().acceptAlert();
            Assert.assertTrue("Error message not displayed", isErrorDisplayed);
        } catch (Exception e) {
            ExtentReportManager.logFail("Expected error alert not found: " + e.getMessage());
            Assert.fail("Expected error alert not found: " + e.getMessage());
        }
    }

    @Then("user should see an error message for invalid credentials")
    public void userShouldSeeAnErrorMessageForInvalidCredentials() {
        try {
            Thread.sleep(1000);
            String alertText = context.getLoginPage().getAlertText();
            System.out.println("Alert message received: " + alertText);
            ExtentReportManager.logInfo("Alert message for invalid credentials: <b>" + alertText + "</b>");
            boolean isErrorDisplayed = alertText.contains("User does not exist") || alertText.contains("Wrong password");
            if (isErrorDisplayed) {
                ExtentReportManager.logPass("Error message displayed for invalid credentials");
            } else {
                ExtentReportManager.logFail("Expected error message not displayed for invalid credentials");
            }
            Assert.assertTrue("Error message not displayed for invalid credentials", isErrorDisplayed);
        } catch (Exception e) {
            ExtentReportManager.logFail("Expected error alert not found: " + e.getMessage());
            Assert.fail("Expected error alert not found: " + e.getMessage());
        }
    }

    @Then("user should not be logged in")
    public void userShouldNotBeLoggedIn() {
        try {
            Thread.sleep(1000); // Wait to verify login state
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean isLoggedIn = context.getHomePage().isLoggedIn();
        if (!isLoggedIn) {
            ExtentReportManager.logPass("User is not logged in as expected with invalid credentials");
        } else {
            ExtentReportManager.logFail("User should not be logged in with invalid credentials");
        }
        Assert.assertFalse("User should not be logged in with invalid credentials", isLoggedIn);
    }
}