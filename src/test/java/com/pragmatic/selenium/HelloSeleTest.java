package com.pragmatic.selenium;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class HelloSeleTest {


    @Test
    public void TestLogins()
    {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
        WebElement userNameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.xpath("//input[@type='submit']"));
        SoftAssert softAssert = new SoftAssert();

//without user name and pwd
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username is required");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();

// Test 1: Missing username
        passwordField.sendKeys("secret_sauc");
        loginBtn.click();
        clearTextbox(driver,passwordField);
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username is required");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
        clearTextbox(driver,passwordField);

// Test 2: Invalid username
        clearTextbox(driver,userNameField);
        userNameField.sendKeys("standar");
        clearTextbox(driver,passwordField);
        passwordField.sendKeys("secret_sauce");
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username and password do not match any user in this service");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();

//Locked user
        clearTextbox(driver,userNameField);
        userNameField.sendKeys("locked_out_user");
        clearTextbox(driver,passwordField);
        passwordField.sendKeys("secret_sauce");
        loginBtn.click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Sorry, this user has been locked out.");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();

// Test 4: Valid login
       clearTextbox(driver,userNameField);
        userNameField.sendKeys("standard_user");
        clearTextbox(driver,passwordField);
        passwordField.sendKeys("secret_sauce");
        loginBtn.click();
        softAssert.assertTrue(driver.findElement(By.xpath("//div//div[@class=\"app_logo\"]")).getText().equals("Swag Labs"));
        softAssert.assertAll();
        driver.quit();
    }

    public void clearTextbox(WebDriver driver,WebElement textbox) {
        try {
            Actions actions = new Actions(driver);
            actions.doubleClick(textbox).sendKeys("").build().perform();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", textbox);
            js.executeScript("arguments[0].textContent = ''; arguments[0].innerText = '';", textbox);
        } catch (Exception e) {
            System.out.println("Error clearing textbox: " + e.getMessage());
        }
    }

}
