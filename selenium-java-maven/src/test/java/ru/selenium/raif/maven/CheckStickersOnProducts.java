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
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class CheckStickersOnProducts {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> products;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 1);
    }

    @Test
    public void checkStickers() throws Exception {
        driver.get("http://localhost/litecart/en/");

        products = driver.findElements(By.cssSelector("li.product"));

        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).findElements(By.cssSelector("div.sticker")).size() == 0) {
                throw new NoSuchElementException("Отсутствует стикер на элементе: " +
                        products.get(i).getText());
            };

            if(products.get(i).findElements(By.cssSelector("div.sticker")).size() > 1) {
                throw new Exception("Количество стикеров на элементе " + products.get(i).getText() + " больше одного");
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
