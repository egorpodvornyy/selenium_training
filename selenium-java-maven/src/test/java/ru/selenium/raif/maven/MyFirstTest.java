package ru.selenium.raif.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testSearch() {
        driver.get("https://software-testing.ru/lms/mod/lesson/view.php?id=232674&pageid=8823");
        driver.findElement(By.name("username")).sendKeys("egorpodvornyy");
        driver.findElement(By.name("password")).sendKeys("wawawawa1");
        driver.findElement(By.cssSelector("input[value=Вход]")).click();

    }

//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }
}
