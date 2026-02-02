package com.demoblaze.stepdefinitions;

import com.demoblaze.utils.ConfigReader;
import com.demoblaze.utils.DriverManager;
import com.demoblaze.utils.ExtentReportManager;
import com.demoblaze.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.ArrayList;
import java.util.List;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        
        // Skip browser initialization for API tests
        if (!scenario.getSourceTagNames().contains("@api")) {
            // Create scenario in Extent Report
            String scenarioDescription = "Feature: " + scenario.getUri().toString().substring(
                scenario.getUri().toString().lastIndexOf("/") + 1);
            ExtentReportManager.createScenario(scenario.getName(), scenarioDescription);
            
            // Assign categories based on tags
            List<String> categories = new ArrayList<>();
            for (String tag : scenario.getSourceTagNames()) {
                categories.add(tag.replace("@", ""));
            }
            if (!categories.isEmpty()) {
                ExtentReportManager.assignCategory(categories.toArray(new String[0]));
            }
            
            // Assign browser as device
            ExtentReportManager.assignDevice(ConfigReader.getBrowser());
            
            // Log scenario start
            ExtentReportManager.logInfo("<b>Scenario Started:</b> " + scenario.getName());
            ExtentReportManager.logInfo("<b>Tags:</b> " + String.join(", ", scenario.getSourceTagNames()));
            
            // Initialize browser
            DriverManager.setDriver(ConfigReader.getBrowser(), ConfigReader.isHeadless());
            ExtentReportManager.logInfo("Browser initialized: " + ConfigReader.getBrowser());
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Skip screenshot for API tests
        if (!scenario.getSourceTagNames().contains("@api")) {
            // Capture screenshot after each step for UI tests
            byte[] screenshot = ScreenshotUtil.captureScreenshotAsBytes(DriverManager.getDriver());
            if (screenshot != null && screenshot.length > 0) {
                // Attach to Cucumber report
                scenario.attach(screenshot, "image/png", "Step: " + scenario.getName());
                
                // Attach to Extent Report with step status
                String screenshotBase64 = ScreenshotUtil.captureScreenshotAsBase64(DriverManager.getDriver());
                if (screenshotBase64 != null) {
                    ExtentReportManager.attachScreenshot(screenshotBase64, "Step Screenshot");
                }
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // Skip screenshot and driver quit for API tests
        if (!scenario.getSourceTagNames().contains("@api")) {
            if (scenario.isFailed()) {
                // Log failure in Extent Report
                ExtentReportManager.logFail("<b>Scenario Failed:</b> " + scenario.getName());
                
                // Take screenshot on failure
                byte[] screenshot = ScreenshotUtil.captureScreenshotAsBytes(DriverManager.getDriver());
                if (screenshot != null && screenshot.length > 0) {
                    scenario.attach(screenshot, "image/png", "Failed: " + scenario.getName());
                    
                    // Attach failure screenshot to Extent Report
                    String screenshotBase64 = ScreenshotUtil.captureScreenshotAsBase64(DriverManager.getDriver());
                    if (screenshotBase64 != null) {
                        ExtentReportManager.attachScreenshot(screenshotBase64, "<b>Failure Screenshot</b>");
                    }
                }
                
                // Also save screenshot to file for reference
                String screenshotPath = ScreenshotUtil.captureScreenshot(DriverManager.getDriver(), 
                    "Failed_" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"));
                if (screenshotPath != null) {
                    System.out.println("Failure screenshot saved at: " + screenshotPath);
                    ExtentReportManager.logInfo("Screenshot saved at: " + screenshotPath);
                }
            } else if (scenario.getStatus().toString().equalsIgnoreCase("PASSED")) {
                // Log success in Extent Report
                ExtentReportManager.logPass("<b>Scenario Passed:</b> " + scenario.getName());
            } else if (scenario.getStatus().toString().equalsIgnoreCase("SKIPPED")) {
                // Log skip in Extent Report
                ExtentReportManager.logSkip("<b>Scenario Skipped:</b> " + scenario.getName());
            }
            
            // Log scenario completion
            ExtentReportManager.logInfo("<b>Scenario Status:</b> " + scenario.getStatus());
            
            // Quit driver
            DriverManager.quitDriver();
            
            // Remove test from thread local
            ExtentReportManager.removeTest();
        }
        
        System.out.println("Scenario " + scenario.getName() + " finished with status: " + scenario.getStatus());
    }
}