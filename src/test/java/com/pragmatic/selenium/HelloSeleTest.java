package com.pragmatic.selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class HelloSeleTest {

    @Test
    public void testHelloSele()
    {
        // Create browser instance
        WebDriver driver = new FirefoxDriver();
        driver.navigate().to("https://www.saucedemo.com/");

// Initialize elements and assertions
        WebElement userNameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.xpath("//input[@type='submit']"));

        SoftAssert softAssert = new SoftAssert();

// Test 1: Missing username
        passwordField.sendKeys("secret_sauc");
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username is required");
        userNameField.clear();

// Test 2: Invalid username
        userNameField.sendKeys("standar");
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username and password do not match any user in this service");
        passwordField.clear();
        userNameField.clear();

// Test 3: Missing password
        userNameField.sendKeys("standard_user");
        passwordField.clear();
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Password is required");

// Test 4: Valid login
        userNameField.clear();
        passwordField.clear();
        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginBtn.click();

        softAssert.assertAll();
        driver.quit();
    }

    /*@Test
    public void testHelloSeletwo()
    {

    }*/
}
