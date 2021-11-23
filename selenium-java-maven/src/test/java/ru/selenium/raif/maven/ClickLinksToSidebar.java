package ru.selenium.raif.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ClickLinksToSidebar {
    private WebDriver driver;
    private WebDriverWait wait;
    private int amountElements;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 1);
    }

    @Test
    public void testSearch() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));

        amountElements = driver.findElements(By.cssSelector("td#sidebar li#app-")).size();

        for(int i = 0; i < amountElements; i++) {
            driver.findElement(By.xpath("(//td[@id='sidebar'] //li[@id='app-'])[" + (i+1) + "]")).click();

            if(driver.findElement(By.xpath("(//td[@id='sidebar'] //li[@id='app-'])[" + (i+1) + "]")).findElements(By.cssSelector("li")).size() > 0) {
                for(int j = 0; j < driver.findElement(By.xpath("(//td[@id='sidebar'] //li[@id='app-'])[" + (i+1) + "]")).findElements(By.cssSelector("li")).size(); j++) {
                    driver.findElement(By.xpath("(//td[@id='sidebar'] //li[@id='app-'])[" + (i+1) + "] //li[" + (j+1) + "]")).click();
                }
            }

            wait.until((WebDriver d) -> d.findElement(By.cssSelector("h1")));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
