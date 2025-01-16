package com.pragmatic.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CrossBrowserTest  {


    private WebDriver driver;  // class level declaration in this browser mode all webdriver inits and url navigation will be done in tests itself

    @BeforeMethod //runs before each method
    public void init() {
       /* driver = new ChromeDriver();  // removed WebDriver declaration since it's already declared at class level
        driver.manage().window().maximize();*/
    }

    @AfterMethod
    public void clean() {
       // added null check for safety
            driver.quit();

    }

   /* @Test
    public void TestLogins() {*/

//without user name and pwd
 /*       driver.findElement(By.xpath("//input[@type='submit']")).click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username is required");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();*/

// Test 1: Missing username
      /*  driver.findElement(By.id("password")).sendKeys("secret_sauc");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        clearTextbox(driver, driver.findElement(By.id("password")));
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                "Epic sadface: Username is required");
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
        clearTextbox(driver, driver.findElement(By.id("password")));*/

// Test 2: Invalid username

    //one can note that all the lines are mainly common so we ccan use data  provders to do all tests from one
    //debug mode , use evauate expression and enter  webdriver.findelem ..... so on  and once element is called we can call methods
      /*  @Test
        public void testInvalidUsername()
        {
            SoftAssert softAssert = new SoftAssert();
            driver.navigate().to("https://www.saucedemo.com/");
            clearTextbox(driver, driver.findElement(By.id("user-name")));
            driver.findElement(By.id("user-name")).sendKeys("standard");
            clearTextbox(driver, driver.findElement(By.id("password")));
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@type='submit']")).click();
            softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                    "Epic sadface: Username and password do not match any user in this service");
            driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
        }*/

    //Locked user
    @Test(dataProvider = "login_credentials"  )
    public void testLockedUser(String userName,String pwd,String msg)
    {
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        SoftAssert softAssert = new SoftAssert();
        clearTextbox(driver, driver.findElement(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys(userName);
        clearTextbox(driver, driver.findElement(By.id("password")));
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                msg);
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
    }
    @Test(dataProvider = "login_credentials"  ,dataProviderClass = DataProviderClass.class)
    public void testLockedUserclass(String userName,String pwd,String msg)
    {
        driver=new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        SoftAssert softAssert = new SoftAssert();;
        clearTextbox(driver, driver.findElement(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys(userName);
        clearTextbox(driver, driver.findElement(By.id("password")));
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),
                msg);
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
    }
    // Test 4: Valid login
      /*  @Test()
        public void testLogins()
        {SoftAssert softAssert = new SoftAssert();
            driver.navigate().to("https://www.saucedemo.com/");
            clearTextbox(driver,driver.findElement(By.id("user-name")));
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            clearTextbox(driver, driver.findElement(By.id("password")));
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@type='submit']")).click();
            softAssert.assertTrue(driver.findElement(By.xpath("//div//div[@class=\"app_logo\"]")).getText().equals("Swag Labs"));
            softAssert.assertAll();

        }*/
    @DataProvider(name = "login_credentials")
    public Object[][] credentials() {
        return new Object[][] {
                { "", "", "Epic sadface: Username is required" },
                { "", "secret_sauce", "Epic sadface: Username is required" },
                { "locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out." }
        };
        //runs as a loop sending order is critical
    }


    public void clearTextbox(WebDriver driver, WebElement textbox) {
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
