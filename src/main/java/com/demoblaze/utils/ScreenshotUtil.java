package com.demoblaze.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Captures screenshot and returns as byte array for Cucumber scenario attachment
     * @param driver WebDriver instance
     * @return byte array of screenshot
     */
    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            System.out.println("Driver is null, cannot capture screenshot");
            return new byte[0];
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Captures screenshot and saves to file
     * @param driver WebDriver instance
     * @param screenshotName Name for the screenshot file
     * @return Path to saved screenshot file
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            System.out.println("Driver is null, cannot capture screenshot");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = screenshotName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
        
        // Create screenshots directory if it doesn't exist
        String screenshotDir = "test-output/screenshots/";
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = screenshotDir + fileName;
        
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Captures screenshot with default naming (scenario name)
     * @param driver WebDriver instance
     * @param scenarioName Name of the scenario
     * @return Path to saved screenshot file
     */
    public static String captureScreenshotForScenario(WebDriver driver, String scenarioName) {
        return captureScreenshot(driver, scenarioName);
    }

    /**
     * Gets relative path for screenshot to be used in HTML reports
     * @param absolutePath Absolute path of screenshot
     * @return Relative path for HTML embedding
     */
    public static String getRelativePath(String absolutePath) {
        if (absolutePath == null) {
            return null;
        }
        return absolutePath.replace("\\", "/");
    }

    /**
     * Captures screenshot and returns as Base64 string for Extent Reports
     * @param driver WebDriver instance
     * @return Base64 encoded string of screenshot
     */
    public static String captureScreenshotAsBase64(WebDriver driver) {
        if (driver == null) {
            System.out.println("Driver is null, cannot capture screenshot");
            return null;
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.out.println("Error capturing screenshot as Base64: " + e.getMessage());
            return null;
        }
    }
}