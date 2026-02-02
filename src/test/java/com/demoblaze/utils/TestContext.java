package com.demoblaze.utils;

import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.LoginPage;
import com.demoblaze.pages.ProductPage;
import com.demoblaze.pages.SignUpPage;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public TestContext() {
        this.driver = DriverManager.getDriver();
        initializePages();
    }

    private void initializePages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signUpPage = new SignUpPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public SignUpPage getSignUpPage() {
        return signUpPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public WebDriver getDriver() {
        return driver;
    }
}