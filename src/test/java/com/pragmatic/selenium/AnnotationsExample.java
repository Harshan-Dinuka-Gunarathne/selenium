package com.pragmatic.selenium;

import org.testng.annotations.*;

public class AnnotationsExample {
    // Runs once before all tests in the class
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("1. @BeforeSuite - First! Setup system properties");
    }

    // Runs once before all tests that belong to this test group
    @BeforeGroups("regression")
    public void beforeGroups() {
        System.out.println("2. @BeforeGroups - Setup for regression group");
    }

    // Runs once before any test method in the class is executed
    @BeforeClass
    public void beforeClass() {
        System.out.println("3. @BeforeClass - Setup class-level resources");
    }

    // Runs before each @Test method
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("4. @BeforeMethod - Setup new browser instance");
    }

    // Test methods - can be run in parallel if configured
    @Test(groups = "regression")
    public void testOne() {
        System.out.println("5. @Test - First test method");
    }

    @Test(dependsOnMethods = "testOne")
    public void testTwo() {
        System.out.println("6. @Test - Second test method");
    }

    // Runs after each @Test method
    @AfterMethod
    public void afterMethod() {
        System.out.println("7. @AfterMethod - Close browser instance");
    }

    // Runs once after all test methods in the class
    @AfterClass
    public void afterClass() {
        System.out.println("8. @AfterClass - Cleanup class resources");
    }

    // Runs once after all tests in this group
    @AfterGroups("regression")
    public void afterGroups() {
        System.out.println("9. @AfterGroups - Cleanup regression group");
    }

    // Runs once after all tests in the suite
    @AfterSuite
    public void afterSuite() {
        System.out.println("10. @AfterSuite - Final cleanup");
    }
}



/*
@BeforeSuite

Runs once before all tests in the suite
        Use: Setting up system properties, database connections

        javaCopy@BeforeSuite
public void setupDatabase() {
        DatabaseConnection.init();
        }

@BeforeTest

Runs before any test tag in testng.xml
        Use: Setting up test environment configurations

        javaCopy@BeforeTest
public void setupTestData() {
        TestDataLoader.load();
        }

@BeforeGroups

Runs before any test method in specified group
        Use: Group-specific setup

        javaCopy@BeforeGroups("api-tests")
public void setupAPI() {
        APIClient.initialize();
        }

@BeforeClass

Runs once per class before any test in that class
Use: Class-level resource initialization

        javaCopy@BeforeClass
public void setupWebDriver() {
        driver = new ChromeDriver();
        }

@BeforeMethod

Runs before each test method
        Use: Setting up test preconditions

        javaCopy@BeforeMethod
public void setupTestPage() {
        driver.get("http://example.com");
        loginPage.login("user", "pass");
        }

@Test

The actual test methods
        Can include attributes like priority, groups, dependsOnMethods

        javaCopy@Test(priority = 1, groups = {"smoke"})
public void verifyLogin() {
        // test code
        }

@Test(dependsOnMethods = "verifyLogin")
public void verifyDashboard() {
        // test code
        }

@AfterMethod

Runs after each test method
        Use: Cleanup after each test

        javaCopy@AfterMethod
public void takeScreenshotOnFailure(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
        takeScreenshot();
        }
        }

@AfterClass

Runs once per class after all tests in the class
Use: Class-level cleanup

        javaCopy@AfterClass
public void closeBrowser() {
        if(driver != null) {
        driver.quit();
        }
        }

@AfterGroups

Runs after all test methods in specified group
        Use: Group-specific cleanup

        javaCopy@AfterGroups("api-tests")
public void cleanupAPI() {
        APIClient.shutdown();
        }

@AfterTest

Runs after all test tags in testng.xml
        Use: Test environment cleanup

        javaCopy@AfterTest
public void cleanupTestData() {
        TestDataLoader.clear();
        }

@AfterSuite

Runs once after all tests in the suite
        Use: Final cleanup

        javaCopy@AfterSuite
public void cleanupDatabase() {
        DatabaseConnection.close();
        }


        Special Annotations:
        javaCopy// Data Provider for parameterized tests
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
        return new Object[][] {
        {"user1", "pass1"},
        {"user2", "pass2"}
        };
        }

@Test(dataProvider = "loginData")
public void testLogin(String username, String password) {
        // test with provided data
        }

// Test Parameters from testng.xml
@Parameters({"browser", "environment"})
@BeforeClass
public void setup(String browser, String env) {
        // setup based on parameters
        }*/
