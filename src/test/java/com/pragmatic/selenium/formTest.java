package com.pragmatic.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

public class formTest {

WebDriver driver;
@BeforeClass
    public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); //get red of cotrolled by selenium text in top

    HashMap<Object, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("autofill.profile_enabled", false);
    prefs.put("autofill.enabled", false);
    options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void fileUplodTest()
    {
        driver.get("https://the-internet.herokuapp.com/upload");
        File resourceDir = new File("src/test/resources/uploads/images.jpeg");
        String absolutePath = resourceDir.getAbsolutePath();
        driver.findElement(By.id("file-upload")).sendKeys(absolutePath);
        //
        driver.findElement(By.id("file-submit")).click();

    }


    //select=webdrivdr find element
    //Seelect opt=new select(element)
   // opt.selectByValue("")
   // opt.ismultiple ? =return multi or single




}
