package ru.selenium.raif.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        driver.get("http://www.youtube.com");
        driver.findElement(By.name("search_query")).click();
        driver.findElement(By.name("search_query")).sendKeys("Алексей Баранцев" + Keys.ENTER);
        driver.findElement(By.id("channel-title")).click();
        wait.until(titleIs("Alexei Barantsev - YouTube"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
