package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By addToCartButton = By.linkText("Add to cart");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickProduct(String productName) {
        By productLink = By.linkText(productName);
        wait.until(ExpectedConditions.elementToBeClickable(productLink)).click();
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String getProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.name"))).getText();
    }
}