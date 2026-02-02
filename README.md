# DemoBlaze Test Automation Framework

## Overview
This is a **Selenium-Cucumber BDD** test automation framework for [DemoBlaze](https://www.demoblaze.com/) e-commerce website. The framework follows **Page Object Model (POM)** design pattern and supports **tag-based execution** for flexible test runs.

---

## Technology Stack

### Core Technologies
- **Java 11** - Programming language
- **Maven 3.x** - Build and dependency management tool

### Testing Frameworks
- **Selenium WebDriver 4.15.0** - Web UI automation
- **Cucumber BDD 7.14.0** - Behavior-Driven Development framework
- **JUnit 4.13.2** - Unit testing framework
- **REST Assured 5.4.0** - API testing framework

### Supporting Libraries
- **WebDriverManager 5.6.2** - Automatic browser driver management
- **Cucumber PicoContainer 7.14.0** - Dependency injection for Cucumber
- **ExtentReports Cucumber7 Adapter 1.14.0** - Advanced HTML reporting
- **SLF4J 2.0.9** - Logging framework
- **REST Assured JSON Path 5.4.0** - JSON response parsing

### Design Patterns
- **Page Object Model (POM)** - UI test design pattern
- **BDD (Behavior-Driven Development)** - Test specification approach
- **Dependency Injection** - Using PicoContainer for context sharing

---

## Project Structure
```
DemoBlazeProject/
├── src/
│   ├── main/java/com/demoblaze/
│   │   ├── pages/              # Page Object Model classes
│   │   │   ├── CartPage.java
│   │   │   ├── HomePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductPage.java
│   │   │   └── SignUpPage.java
│   │   └── utils/              # Utility and helper classes
│   │       ├── ConfigReader.java    # Configuration file reader
│   │       └── DriverManager.java   # WebDriver lifecycle management
│   └── test/
│       ├── java/com/demoblaze/
│       │   ├── runners/        # Test execution runners
│       │   │   └── TestRunner.java
│       │   ├── stepdefinitions/# Cucumber step definitions
│       │   │   ├── CommonSteps.java
│       │   │   ├── HomeSteps.java
│       │   │   ├── Hooks.java       # Setup and teardown hooks
│       │   │   ├── LoginApiSteps.java
│       │   │   ├── LoginSteps.java
│       │   │   ├── ProductSteps.java
│       │   │   └── SignUpSteps.java
│       │   └── utils/          # Test utilities
│       │       └── TestContext.java # Shared test context
│       └── resources/
│           ├── features/       # Cucumber BDD feature files
│           │   ├── api/        # API test scenarios
│           │   │   └── login_api.feature
│           │   └── web/        # Web UI test scenarios
│           │       ├── login.feature
│           │       ├── product.feature
│           │       ├── purchase.feature
│           │       └── signup.feature
│           ├── config.properties      # Application configuration
│           ├── extent-config.xml     # ExtentReports XML configuration
│           └── extent.properties     # ExtentReports properties
├── pom.xml                     # Maven project configuration
└── README.md                   # Project documentation
```

### Directory Details

#### **src/main/java/com/demoblaze/**
- **pages/**: Contains Page Object Model classes representing web pages
  - Each page class encapsulates page elements and actions
  - Follows Single Responsibility Principle
- **utils/**: Reusable utility classes
  - `DriverManager`: Manages WebDriver instance lifecycle
  - `ConfigReader`: Reads properties from config.properties

#### **src/test/java/com/demoblaze/**
- **runners/**: JUnit test runners for Cucumber
  - `TestRunner.java`: Main entry point for test execution
- **stepdefinitions/**: Cucumber step implementation
  - `Hooks.java`: Setup/teardown with @api tag support for conditional browser initialization
  - Separate step files for each feature (Login, SignUp, Product, etc.)
  - `LoginApiSteps.java`: API testing steps using REST Assured
- **utils/**: Test-specific utilities
  - `TestContext.java`: Shares state between step definitions using dependency injection

#### **src/test/resources/**
- **features/**: Gherkin feature files organized by test type
  - `api/`: API test scenarios (browser not initialized for @api tagged scenarios)
  - `web/`: Web UI test scenarios
- **config.properties**: Environment and test configuration
- **extent-config.xml** & **extent.properties**: Reporting configuration

---

## Prerequisites
1. **Java JDK 11** or higher installed
2. **Maven 3.x** installed
3. **Chrome/Firefox/Edge** browser installed
4. **Internet connection** for downloading dependencies

---

## Setup Instructions

### 1. Clone or Download the Project
```bash
git clone <repository-url>
cd DemoBlazeProject
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Configure Test Settings
Edit `src/test/resources/config.properties` to customize:
- Browser type (chrome/firefox/edge)
- Headless mode (true/false)
- Timeouts
- Test credentials

---

## Execution Commands

### Run All Tests
```bash
mvn clean test
```

### Run Tests with Specific Tags

#### Run Smoke Tests Only
```bash
mvn clean test "-Dcucumber.filter.tags=@smoke"
```

#### Run Regression Tests
```bash
mvn clean test "-Dcucumber.filter.tags=@regression"
```

#### Run Multiple Tags (AND condition)
```bash
mvn clean test "-Dcucumber.filter.tags=@smoke and @login"
```

#### Run All API Tests
```bash
mvn clean test "-Dcucumber.filter.tags=@api"
```
**Note:** Tests tagged with `@api` skip browser initialization for faster API-only execution.

#### Run API Test With Specific Module
```bash
mvn clean test "-Dcucumber.filter.tags=@api and @loginAPI"
```

#### Exclude Specific Tags
```bash
mvn clean test "-Dcucumber.filter.tags=not @negative"
```

## Test Reports

### Cucumber HTML Report
After execution, view the report at:
```
test-output/cucumber-reports/cucumber.html
```

### ExtentReports
Enhanced HTML report available at:
```
test-output/SparkReport/Spark.html
```

**ExtentReports Configuration:**
- `extent.properties` - Main configuration for report output paths
- `extent-config.xml` - XML configuration for report theme, title, and customization

### Screenshots
Failed test screenshots are saved in:
```
test-output/screenshots/
```

---

## Configuration

### Browser Configuration
Edit `config.properties`:
```properties
browser=chrome          # Options: chrome, firefox, edge
headless=false         # Set to true for headless execution
```

### Timeout Configuration
```properties
implicit.wait=10       # Implicit wait in seconds
explicit.wait=20       # Explicit wait in seconds
page.load.timeout=30   # Page load timeout in seconds
```

---

## Writing New Tests

### 1. Create Feature File
Add new `.feature` file in `src/test/resources/features/`

### 2. Create Page Object
Add new page class in `src/main/java/com/demoblaze/pages/`

### 3. Create Step Definitions
Add step definition class in `src/test/java/com/demoblaze/stepdefinitions/`

### 4. Update TestContext
Add new page object initialization in `TestContext.java`

---

## Troubleshooting

### Issue: WebDriver not found
**Solution:** Run `mvn clean install` to download WebDriverManager dependencies

### Issue: Tests fail with timeout
**Solution:** Increase timeout values in `config.properties`

### Issue: Browser not launching
**Solution:** Ensure the browser is installed and WebDriverManager can download the driver

---

## Best Practices
1. Use **meaningful tag names** for better test organization
2. Keep **page objects independent** and reusable
3. Use **explicit waits** instead of Thread.sleep()
4. Follow **BDD naming conventions** in feature files
5. Maintain **one assertion per test** when possible
6. Use **Background** for common preconditions