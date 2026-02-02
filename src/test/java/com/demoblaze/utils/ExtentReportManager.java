package com.demoblaze.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static String reportPath;

    /**
     * Initialize Extent Reports
     */
    public static void initReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportPath = "test-output/ExtentReports/ExtentReport_" + timestamp + ".html";
            
            // Create directory if it doesn't exist
            File reportDir = new File("test-output/ExtentReports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("DemoBlaze Test Automation Report");
            sparkReporter.config().setReportName("Selenium Cucumber BDD Test Report");
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            sparkReporter.config().setEncoding("UTF-8");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "DemoBlaze");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("Environment", "QA");
        }
    }

    /**
     * Create a new test in the report
     */
    public static ExtentTest createTest(String testName, String description) {
        if (extent == null) {
            initReports();
        }
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    /**
     * Create a scenario test
     */
    public static ExtentTest createScenario(String scenarioName, String description) {
        if (extent == null) {
            initReports();
        }
        ExtentTest scenario = extent.createTest(scenarioName, description);
        scenarioTest.set(scenario);
        return scenario;
    }

    /**
     * Get current test
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Get current scenario test
     */
    public static ExtentTest getScenarioTest() {
        return scenarioTest.get();
    }

    /**
     * Log info message
     */
    public static void logInfo(String message) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().info(message);
        }
    }

    /**
     * Log pass message
     */
    public static void logPass(String message) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
        }
    }

    /**
     * Log fail message
     */
    public static void logFail(String message) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
        }
    }

    /**
     * Log warning message
     */
    public static void logWarning(String message) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().warning(MarkupHelper.createLabel(message, ExtentColor.YELLOW));
        }
    }

    /**
     * Log skip message
     */
    public static void logSkip(String message) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().skip(MarkupHelper.createLabel(message, ExtentColor.ORANGE));
        }
    }

    /**
     * Attach screenshot as Base64
     */
    public static void attachScreenshot(String screenshotBase64, String title) {
        if (scenarioTest.get() != null && screenshotBase64 != null && !screenshotBase64.isEmpty()) {
            try {
                // ExtentReports expects pure base64 string without data URI prefix
                scenarioTest.get().info(title, 
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
            } catch (Exception e) {
                System.err.println("Failed to attach screenshot: " + e.getMessage());
            }
        }
    }

    /**
     * Attach screenshot from file path
     */
    public static void attachScreenshotFromPath(String screenshotPath, String title) {
        if (scenarioTest.get() != null && screenshotPath != null && !screenshotPath.isEmpty()) {
            try {
                scenarioTest.get().info(title, 
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                System.err.println("Failed to attach screenshot from path: " + e.getMessage());
            }
        }
    }

    /**
     * Capture and attach screenshot
     */
    public static void captureAndAttachScreenshot(WebDriver driver, String title) {
        if (driver != null) {
            String screenshotBase64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
            attachScreenshot(screenshotBase64, title);
        }
    }

    /**
     * Log step with screenshot
     */
    public static void logStepWithScreenshot(WebDriver driver, String stepDescription, Status status) {
        if (scenarioTest.get() != null && driver != null) {
            String screenshotBase64 = ScreenshotUtil.captureScreenshotAsBase64(driver);
            try {
                if (screenshotBase64 != null && !screenshotBase64.isEmpty()) {
                    // ExtentReports expects pure base64 string without data URI prefix
                    scenarioTest.get().log(status, stepDescription, 
                        MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
                } else {
                    scenarioTest.get().log(status, stepDescription);
                }
            } catch (Exception e) {
                scenarioTest.get().log(status, stepDescription);
                System.err.println("Failed to attach screenshot for step: " + e.getMessage());
            }
        }
    }

    /**
     * Assign category to test
     */
    public static void assignCategory(String... categories) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().assignCategory(categories);
        }
    }

    /**
     * Assign author to test
     */
    public static void assignAuthor(String... authors) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().assignAuthor(authors);
        }
    }

    /**
     * Assign device to test
     */
    public static void assignDevice(String... devices) {
        if (scenarioTest.get() != null) {
            scenarioTest.get().assignDevice(devices);
        }
    }

    /**
     * Flush the report
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("Extent Report generated at: " + reportPath);
        }
    }

    /**
     * Get report path
     */
    public static String getReportPath() {
        return reportPath;
    }

    /**
     * Remove test from thread local
     */
    public static void removeTest() {
        extentTest.remove();
        scenarioTest.remove();
    }
}