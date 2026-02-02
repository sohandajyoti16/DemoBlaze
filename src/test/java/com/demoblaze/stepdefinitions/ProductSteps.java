package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductSteps {
    private TestContext context; 

    public ProductSteps(TestContext context) {
        this.context = context;
    }

    @When("user clicks on {string} product")
    public void userClicksOnProduct(String productName) {
        ExtentReportManager.logInfo("Clicking on product: <b>" + productName + "</b>");
        context.getProductPage().clickProduct(productName);
        ExtentReportManager.logPass("Successfully clicked on product: " + productName);
    }

    @When("user clicks on Add to cart button")
    public void userClicksOnAddToCartButton() {
        try {
            Thread.sleep(2000); // Wait for product page to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReportManager.logInfo("Clicking on Add to cart button");
        context.getProductPage().clickAddToCart();
        ExtentReportManager.logPass("Successfully clicked on Add to cart button");
    }

    @Then("user should see {string} alert message")
    public void userShouldSeeAlertMessage(String expectedMessage) {
        try {
            WebDriverWait wait = new WebDriverWait(context.getDriver(), Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String actualMessage = alert.getText();
            ExtentReportManager.logInfo("Alert message received: <b>" + actualMessage + "</b>");
            boolean isMessageCorrect = actualMessage.contains(expectedMessage);
            if (isMessageCorrect) {
                ExtentReportManager.logPass("Alert message validated: " + expectedMessage);
            } else {
                ExtentReportManager.logFail("Expected message: '" + expectedMessage + "' but got: '" + actualMessage + "'");
            }
            alert.accept();
            Assert.assertTrue("Expected message: '" + expectedMessage + "' but got: '" + actualMessage + "'", isMessageCorrect);
        } catch (Exception e) {
            ExtentReportManager.logFail("Alert not present or message validation failed: " + e.getMessage());
            Assert.fail("Alert not present or message validation failed: " + e.getMessage());
        }
    }

    @Then("user should see product {string} in cart")
    public void userShouldSeeProductInCart(String productName) {
        try {
            Thread.sleep(2000); // Wait for cart page to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean isProductInCart = context.getCartPage().isProductInCart(productName);
        if (isProductInCart) {
            ExtentReportManager.logPass("Product <b>" + productName + "</b> is present in cart");
        } else {
            ExtentReportManager.logFail("Product '" + productName + "' is not in cart");
        }
        Assert.assertTrue("Product '" + productName + "' is not in cart", isProductInCart);
    }

    @When("user clicks on Place Order button")
    public void userClicksOnPlaceOrderButton() {
        ExtentReportManager.logInfo("Clicking on Place Order button");
        context.getCartPage().clickPlaceOrder();
        ExtentReportManager.logPass("Successfully clicked on Place Order button");
    }

    @When("user fills order details with name {string}, country {string}, city {string}, card {string}, month {string}, year {string}")
    public void userFillsOrderDetails(String name, String country, String city, String card, String month, String year) {
        try {
            Thread.sleep(1000); // Wait for modal to appear
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReportManager.logInfo("Filling order details - Name: <b>" + name + "</b>, Country: <b>" + country + "</b>, City: <b>" + city + "</b>");
        context.getCartPage().fillOrderDetails(name, country, city, card, month, year);
        ExtentReportManager.logPass("Successfully filled order details");
    }

    @When("user clicks on Purchase button")
    public void userClicksOnPurchaseButton() {
        ExtentReportManager.logInfo("Clicking on Purchase button");
        context.getCartPage().clickPurchase();
        ExtentReportManager.logPass("Successfully clicked on Purchase button");
    }

    @Then("user should see {string} success message")
    public void userShouldSeeSuccessMessage(String expectedMessage) {
        try {
            Thread.sleep(2000); // Wait for success modal
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String actualMessage = context.getCartPage().getSuccessMessage();
        ExtentReportManager.logInfo("Success message received: <b>" + actualMessage + "</b>");
        boolean isMessageCorrect = actualMessage.contains(expectedMessage);
        if (isMessageCorrect) {
            ExtentReportManager.logPass("Success message validated: " + expectedMessage);
        } else {
            ExtentReportManager.logFail("Expected success message: '" + expectedMessage + "' but got: '" + actualMessage + "'");
        }
        Assert.assertTrue("Expected success message: '" + expectedMessage + "' but got: '" + actualMessage + "'", isMessageCorrect);
    }

    @Then("user clicks OK on success message")
    public void userClicksOKOnSuccessMessage() {
        ExtentReportManager.logInfo("Clicking OK on success message");
        context.getCartPage().clickOkOnSuccessMessage();
        ExtentReportManager.logPass("Successfully clicked OK on success message");
    }
}