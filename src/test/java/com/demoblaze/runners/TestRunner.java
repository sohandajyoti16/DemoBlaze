package com.demoblaze.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.demoblaze.stepdefinitions"},
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/cucumber.html",
        "json:test-output/cucumber-reports/cucumber.json",
        "junit:test-output/cucumber-reports/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,


    dryRun = false
    // tags can be passed via Maven command line using -Dcucumber.filter.tags
)
public class TestRunner {
    // This class will be empty - it's just a runner
}