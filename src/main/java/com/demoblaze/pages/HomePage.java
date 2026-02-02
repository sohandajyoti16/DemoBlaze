package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By loginLink = By.id("login2");
    private By signUpLink = By.id("signin2");
    private By logoutLink = By.id("logout2");
    private By welcomeUser = By.id("nameofuser");
    private By categoriesSection = By.id("cat");
    private By cartLink = By.id("cartur");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void clickSignUp() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).getText();
    }

    public void clickCategory(String category) {
        By categoryLocator = By.linkText(category);
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public boolean isHomePageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesSection)).isDisplayed();
    }
}