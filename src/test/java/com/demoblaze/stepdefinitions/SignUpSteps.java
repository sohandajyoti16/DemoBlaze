package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class SignUpSteps {
    private TestContext context;

    public SignUpSteps(TestContext context) {
        this.context = context;
    }

    @When("user clicks on Sign up link")
    public void userClicksOnSignUpLink() {
        ExtentReportManager.logInfo("Clicking on Sign up link");
        context.getHomePage().clickSignUp();
        ExtentReportManager.logPass("Successfully clicked on Sign up link");
    }

    @When("user enters signup username {string} and signup password {string}")
    public void userEntersSignupUsernameAndSignupPassword(String username, String password) {
        ExtentReportManager.logInfo("Entering signup username: <b>" + username + "</b> and password");
        context.getSignUpPage().signUp(username, password);
        ExtentReportManager.logPass("Successfully entered signup credentials");
    }

    @Then("user should see signup success message")
    public void userShouldSeeSignupSuccessMessage() {
        try {
            String alertMessage = context.getSignUpPage().getAlertText();
            ExtentReportManager.logInfo("Signup alert message: <b>" + alertMessage + "</b>");
            boolean isSuccessMessage = alertMessage.contains("Sign up successful") || alertMessage.contains("This user already exist");
            if (isSuccessMessage) {
                ExtentReportManager.logPass("Signup success message displayed");
            } else {
                ExtentReportManager.logFail("Expected signup success message not displayed");
            }
            Assert.assertTrue("Signup message not displayed", isSuccessMessage);
        } catch (Exception e) {
            ExtentReportManager.logFail("Alert not displayed: " + e.getMessage());
            Assert.fail("Alert not displayed: " + e.getMessage());
        }
    }

    @Then("user should see signup error message")
    public void userShouldSeeSignupErrorMessage() {
        try {
            Thread.sleep(1000);
            String alertMessage = context.getSignUpPage().getAlertText();
            ExtentReportManager.logInfo("Signup error alert message: <b>" + alertMessage + "</b>");
            boolean isErrorMessage = alertMessage.contains("Please fill out Username and Password") ||
                    alertMessage.contains("fill") ||
                    alertMessage.contains("required");
            if (isErrorMessage) {
                ExtentReportManager.logPass("Signup error message displayed as expected");
            } else {
                ExtentReportManager.logFail("Expected signup error message not displayed");
            }
            Assert.assertTrue("Error message not displayed", isErrorMessage);
        } catch (Exception e) {
            ExtentReportManager.logFail("Alert not displayed: " + e.getMessage());
            Assert.fail("Alert not displayed: " + e.getMessage());
        }
    }
}