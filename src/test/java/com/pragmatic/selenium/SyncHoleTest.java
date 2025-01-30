/*
package com.pragmatic.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Time;
import java.time.Duration;

public class SyncHoleTest {


    private final String url="https://eviltester.github.io/synchole/buttons.html";
    private WebDriver driver;


    @BeforeMethod
    public void init()
    {
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);

    }
    @AfterMethod
    public void afterMethod()
    {
        driver.close();
    }
   */
/* @Test
    public void testlink()
    {
        WebElement element=driver.findElement(By.xpath("//a[@id=\"aboutlink\"]"));
        driver.findElement(By.cssSelector("div#collapsable")).click();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.) //check all avail options
wait.until(ExpectedConditions.visibilityOf(element));
element.click();


    }*//*



    @Test
    public void clicls() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.id("easy00")).click();
     //Thread.sleep(2000); bad practice
        clickbutton(By.id("easy00"));
        // Thread.sleep(3000)
        driver.findElement(By.id("easy02")).click();
        driver.findElement(By.id("easy03")).click();

    }

    private void clickbutton(By by) {
        driver.findElement(by).click();
    }

    @Test
    public void clickexplecitwait() throws InterruptedException {
       WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
       wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button00")));
       clickButton(By.id("Button00"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button01")));
        clickButton(By.id("Button01"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button02")));
        clickButton(By.id("Button03"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button03")));
        clickButton(By.id("Button03"));
        //wait.pollingEvery(Duration.ofSeconds(10));
        //wait.ignoring(Exception e)
    }


    */
/*@Test
    public void waitTillvisible()
    {

        driver.findElement(By.cssSelector("div#collapsable")).click();
        WebElement element=driver.findElement(By.xpath("//a[@id=\"aboutlink\"]"));
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10),Duration.ofMillis(50));
        //wait.until(ExpectedConditions.) //check all avail options
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#collapsable")));
        element.click();
    }*//*



}
*/
