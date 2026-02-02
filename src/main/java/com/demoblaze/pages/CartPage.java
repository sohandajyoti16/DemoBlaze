package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By cartTable = By.id("tbodyid");
    private By cartItems = By.cssSelector("#tbodyid tr");
    private By productNameInCart = By.cssSelector("td:nth-child(2)");
    private By deleteButton = By.linkText("Delete");
    private By placeOrderButton = By.cssSelector("button[data-target='#orderModal']");
    private By totalPrice = By.id("totalp");
    
    // Place Order Modal Locators
    private By nameField = By.id("name");
    private By countryField = By.id("country");
    private By cityField = By.id("city");
    private By creditCardField = By.id("card");
    private By monthField = By.id("month");
    private By yearField = By.id("year");
    private By purchaseButton = By.cssSelector("button[onclick='purchaseOrder()'");
    
    // Success Message Locators
    private By successMessage = By.cssSelector(".sweet-alert h2");
    private By purchaseDetails = By.cssSelector(".lead.text-muted");
    private By okButton = By.cssSelector(".confirm.btn.btn-lg.btn-primary");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isProductInCart(String productName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(cartTable));
            List<WebElement> items = driver.findElements(cartItems);
            for (WebElement item : items) {
                String name = item.findElement(productNameInCart).getText();
                if (name.equals(productName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(cartTable));
            List<WebElement> items = driver.findElements(cartItems);
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getTotalPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalPrice)).getText();
    }

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
    }

    public void fillOrderDetails(String name, String country, String city, String card, String month, String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(countryField).sendKeys(country);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(creditCardField).sendKeys(card);
        driver.findElement(monthField).sendKeys(month);
        driver.findElement(yearField).sendKeys(year);
    }

    public void clickPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }

    public String getPurchaseDetails() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(purchaseDetails)).getText();
    }

    public void clickOkOnSuccessMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
    }

    public void deleteProductFromCart(String productName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(cartTable));
            List<WebElement> items = driver.findElements(cartItems);
            for (WebElement item : items) {
                String name = item.findElement(productNameInCart).getText();
                if (name.equals(productName)) {
                    item.findElement(deleteButton).click();
                    Thread.sleep(1000); // Wait for deletion
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}