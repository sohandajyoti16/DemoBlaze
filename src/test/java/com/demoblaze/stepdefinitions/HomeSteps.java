package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ConfigReader;
import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class HomeSteps {
    private TestContext context;

    public HomeSteps(TestContext context) {
        this.context = context;
    }

    @Given("user navigates to DemoBlaze homepage")
    public void userNavigatesToDemoBlazeHomepage() {
        String url = ConfigReader.getBaseUrl();
        ExtentReportManager.logInfo("Navigating to DemoBlaze homepage: <b>" + url + "</b>");
        context.getHomePage().navigateToHomePage(url);
        ExtentReportManager.logPass("Successfully navigated to DemoBlaze homepage");
    }

    @Then("user should see the homepage")
    public void userShouldSeeTheHomepage() {
        boolean isHomePageDisplayed = context.getHomePage().isHomePageDisplayed();
        if (isHomePageDisplayed) {
            ExtentReportManager.logPass("Homepage is displayed successfully");
        } else {
            ExtentReportManager.logFail("Homepage is not displayed");
        }
        Assert.assertTrue("Homepage is not displayed", isHomePageDisplayed);
    }

    @When("user clicks on {string} category")
    public void userClicksOnCategory(String category) {
        ExtentReportManager.logInfo("Clicking on category: <b>" + category + "</b>");
        context.getHomePage().clickCategory(category);
        ExtentReportManager.logPass("Successfully clicked on category: " + category);
    }

    @When("user clicks on Cart")
    public void userClicksOnCart() {
        ExtentReportManager.logInfo("Clicking on Cart");
        context.getHomePage().clickCart();
        ExtentReportManager.logPass("Successfully clicked on Cart");
    }
}