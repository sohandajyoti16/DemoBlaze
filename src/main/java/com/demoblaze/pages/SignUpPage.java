package com.demoblaze.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By signUpModal = By.id("signInModal");
    private By usernameField = By.id("sign-username");
    private By passwordField = By.id("sign-password");
    private By signUpButton = By.xpath("//button[text()='Sign up']");
    private By closeButton = By.xpath("//div[@id='signInModal']//button[text()='Close']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void waitForSignUpModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpModal));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignUpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).click();
    }

    public String getAlertText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();

        return alertText;
    }

    public void acceptAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void signUp(String username, String password) {
        waitForSignUpModal();
        enterUsername(username);
        enterPassword(password);
        clickSignUpButton();
    }

    public void closeSignUpModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}