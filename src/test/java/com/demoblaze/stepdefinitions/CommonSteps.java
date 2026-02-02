package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.TestContext;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class CommonSteps {
    private TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Then("user accepts the alert")
    public void userAcceptsTheAlert() {
        try {
            Thread.sleep(500);
            ExtentReportManager.logInfo("Accepting the alert");
            try {
                context.getLoginPage().acceptAlert();
                System.out.println("Alert accepted successfully via LoginPage");
                ExtentReportManager.logPass("Alert accepted successfully");
            } catch (Exception e) {
                context.getSignUpPage().acceptAlert();
                System.out.println("Alert accepted successfully via SignUpPage");
                ExtentReportManager.logPass("Alert accepted successfully");
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to accept alert: " + e.getMessage());
            Assert.fail("Failed to accept alert: " + e.getMessage());
        }
    }
}
