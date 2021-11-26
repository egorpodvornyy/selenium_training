package ru.selenium.raif.maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckSortedCountriesAndZones {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> countries;
    private int rowsNumb1;
    private int rowsNumb2;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
    }

    @Test
    public void checkSortedCountriesAlphabetically() throws Exception {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        AutorizationInLitecart.authorizeAdmin(driver);

        wait.until((WebDriver d) -> d.findElement(By.cssSelector("h1")));

        countries = driver.findElements(By.xpath("//*[@class='row'] //td[5]"));

        for (int i = 0; i < countries.size() - 1; i++) {
            if (countries.get(i).getAttribute("textContent").compareTo(
                    countries.get(i + 1).getAttribute("textContent")) == 1) {
                throw new Exception("Страны неотсортированы по алфавиту");
            }
        }

        for (int i = 2; i < countries.size() + 2; i++) {
            if (Integer.parseInt(driver.findElement(By.cssSelector(".row:nth-of-type(" + i + ") td:nth-of-type(6)")).
                    getAttribute("textContent")) != 0) {
                driver.findElement(By.cssSelector(".row:nth-of-type(" + i + ") td:nth-of-type(5) a")).click();

                rowsNumb1 = driver.findElements(By.cssSelector("table #table-zones tr:not(.header)")).size() - 1;

                for (int j = 2; j < rowsNumb1 + 1; j++) {

                    if (driver.findElement(By.cssSelector("#table-zones tr:not(.header):nth-of-type(" +  j + ") td:nth-of-type(3)")).
                            getAttribute("textContent").compareTo(
                            driver.findElement(By.cssSelector("#table-zones tr:not(.header):nth-of-type(" +  (j + 1) + ") td:nth-of-type(3)")).
                                    getAttribute("textContent")) == 1) {

                        driver.navigate().back();

                        throw new Exception("Геозоны страны " + driver.findElement(By.cssSelector(".row:nth-of-type(" + i + ") td:nth-of-type(5)")).
                                getAttribute("textContent") + " неотсортированы по алфавиту");
                    }
                }

                driver.navigate().back();
            }
        }
    }

    @Test
    public void checkSortedZonesAlphabetically() throws Exception {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        AutorizationInLitecart.authorizeAdmin(driver);

        rowsNumb1 = driver.findElements(By.cssSelector(".row td:nth-of-type(3) a")).size();

        for(int i = 1; i <= rowsNumb1; i++) {
            driver.findElement(By.xpath("//*[@class='row'][" + i + "] //td[3] //a")).click();

            rowsNumb2 = driver.findElements(By.cssSelector("#table-zones tr:not(.header)")).size();

            for (int j = 2; j < rowsNumb2; j++) {
                String i1 = driver.findElement(By.cssSelector("tr:nth-of-type(" + (j) + ") td:nth-of-type(3) option[selected=selected")).
                        getAttribute("textContent");
                String i2 = driver.findElement(By.cssSelector("tr:nth-of-type(" + (j+1) + ") td:nth-of-type(3) option[selected=selected")).
                        getAttribute("textContent");

                if (driver.findElement(By.cssSelector("tr:nth-of-type(" + (j) + ") td:nth-of-type(3) option[selected=selected")).
                        getAttribute("textContent").compareTo(
                            driver.findElement(By.cssSelector("tr:nth-of-type(" + (j+1) + ") td:nth-of-type(3) option[selected=selected")).
                                    getAttribute("textContent")) == 1) {

                    driver.navigate().back();

                    throw new Exception("Геозоны страны " + driver.findElement(By.xpath("//*[@class='row'][" + i + "] //td[3]")).
                                getAttribute("textContent") + " неотсортированы по алфавиту");
                    }
                }

            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


