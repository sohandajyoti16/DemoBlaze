package com.demoblaze.listeners;

import com.demoblaze.utils.ExtentReportManager;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

/**
 * JUnit RunListener to manage Extent Report lifecycle
 * This ensures the report is properly initialized and flushed
 */
public class ExtentReportListener extends RunListener {

    @Override
    public void testRunStarted(Description description) throws Exception {
        System.out.println("========================================");
        System.out.println("Test Execution Started");
        System.out.println("========================================");
        // Initialize Extent Reports at the start of test run
        ExtentReportManager.initReports();
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        System.out.println("========================================");
        System.out.println("Test Execution Completed");
        System.out.println("Total Tests: " + result.getRunCount());
        System.out.println("Passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Failed: " + result.getFailureCount());
        System.out.println("Ignored: " + result.getIgnoreCount());
        System.out.println("========================================");
        
        // Flush Extent Reports at the end of test run
        ExtentReportManager.flushReports();
        
        System.out.println("Extent Report Location: " + ExtentReportManager.getReportPath());
    }
}