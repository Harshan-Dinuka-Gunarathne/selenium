package com.pragmatic.selenium;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class checkoutFlowTest {
    Faker faker = new Faker();
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();


    }

    @BeforeMethod
    public void navigateToSite() {
        driver.get("https://www.saucedemo.com/");
login();

    }
    public void login()
    {
        clearTextbox(driver, driver.findElement(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        clearTextbox(driver, driver.findElement(By.id("password")));
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @Test(priority = 1, dataProvider = "login_credentials")
    public void testLockedUser(String userName, String pwd, String msg) {
        SoftAssert softAssert = new SoftAssert();
        driver.navigate().to("https://www.saucedemo.com/");
        clearTextbox(driver, driver.findElement(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys(userName);
        clearTextbox(driver, driver.findElement(By.id("password")));
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        softAssert.assertEquals(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(), msg);
        driver.findElement(By.xpath("//button[@data-test=\"error-button\"]")).click();
    }

    @DataProvider(name = "login_credentials")
    public Object[][] credentials() {
        return new Object[][]{
                {"", "", "Epic sadface: Username is required"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}
        };
    }

    /**
     * **************************************************
     * add to cart
     * **************************************************
     */
    @Test(priority = 2)
    public void addToCartTest() {
        // Login
//login();
        // Add items to cart
        List<WebElement> itemNames = driver.findElements(By.xpath("//div[@data-test=\"inventory-item-name\"]"));
        for (int i = 0; i < 2 && i < itemNames.size(); i++) {
            WebElement addToCartButton = itemNames.get(i).findElement(By.xpath(
                    "./ancestor::div[@data-test=\"inventory-item-description\"]//button"));
            addToCartButton.click();
        }

        // Verify cart badge
        WebElement CartNumber = driver.findElement(By.xpath("//span[@data-test=\"shopping-cart-badge\"]"));
        Assert.assertEquals(CartNumber.getText(), "2");

        // Navigate to cart and verify
        WebElement cart = driver.findElement(By.xpath("//a[@data-test=\"shopping-cart-link\"]"));
        cart.click();
        List<WebElement> cartItemList = driver.findElements(By.xpath("//div[@data-test=\"inventory-item\"]"));
        Assert.assertEquals(cartItemList.size(), 2);
    }

    @Test(priority = 3)
    public void RemoveItemFromCartTest() {
        addToCartTest();
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[text()=\"Remove\"]"));
        removeButtons.get(0).click();
        List<WebElement> cartItemList = driver.findElements(By.xpath("//div[@data-test=\"inventory-item\"]"));
        Assert.assertEquals(cartItemList.size(), 1);
    }

    /**
     * **************************************************
     * Checkout Flow Tests
     * **************************************************
     */
    @Test(dataProvider = "checkout_data", priority = 4)
    public void checkoutInputFieldTest(String usrName, String pwd, String zipCodeTxt, String validation) {
        addToCartTest();
        WebElement checkout = driver.findElement(By.xpath("//button[@id=\"checkout\"]"));
        checkout.click();

        // Fill checkout form
        WebElement fname = driver.findElement(By.xpath("//input[@id=\"first-name\"]"));
        WebElement lname = driver.findElement(By.xpath("//input[@id=\"last-name\"]"));
        WebElement zipCode = driver.findElement(By.xpath("//input[@id=\"postal-code\"]"));
        fname.sendKeys(usrName);
        lname.sendKeys(pwd);
        zipCode.sendKeys(zipCodeTxt);

        driver.findElement(By.xpath("//input[@id=\"continue\"]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//h3")).getText(), validation);
    }

    @DataProvider(name = "checkout_data")
    public Object[][] checkoutData() {
        Faker faker = new Faker();

        return new Object[][]{
                {"", "", "", "Error: First Name is required"},
                {faker.name().firstName(), "", "", "Error: Last Name is required"},
                {"", faker.name().lastName(), "", "Error: First Name is required"},
                {faker.name().firstName(), faker.name().lastName(), "", "Error: Postal Code is required"}
        };
    }

    @Test(priority = 5)
    public void checkoutSuccessTest() {
        // Setup cart with one item
    //ToDo prices check
        RemoveItemFromCartTest();

        // Start checkout process
        driver.findElement(By.xpath("//button[@id=\"checkout\"]")).click();

        // Fill checkout information
        WebElement fnameInput = driver.findElement(By.xpath("//input[@id=\"first-name\"]"));
        WebElement lnameInput = driver.findElement(By.xpath("//input[@id=\"last-name\"]"));
        WebElement zipCodeInput = driver.findElement(By.xpath("//input[@id=\"postal-code\"]"));
        fnameInput.sendKeys(faker.name().firstName());
        lnameInput.sendKeys(faker.name().firstName());
        zipCodeInput.sendKeys(faker.address().zipCode());
        driver.findElement(By.xpath("//input[@id=\"continue\"]")).click();
        Assert.assertEquals(
                driver.findElement(By.xpath("//span[@class='title']")).getText(),
                "Checkout: Overview"
        );
        WebElement subtotal = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
        WebElement tax = driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
        Assert.assertTrue(subtotal.isDisplayed());
        Assert.assertTrue(tax.isDisplayed());
        //assert only one item in card since one was removed
        Assert.assertEquals(driver.findElements(By.xpath("//div[@class=\"cart_item\"]")).size(), 1);
        driver.findElement(By.xpath("//button[@id='finish']")).click();
        Assert.assertEquals(
                driver.findElement(By.xpath("//h2[@class='complete-header']")).getText(),
                "Thank you for your order!"
        );
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

