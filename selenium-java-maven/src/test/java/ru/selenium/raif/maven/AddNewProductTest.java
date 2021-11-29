package ru.selenium.raif.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AddNewProductTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    @Test
    public void testAddProduct() {
        driver.get("http://localhost/litecart/admin/");

        AutorizationInLitecart.authorizeAdmin(driver);

        driver.findElement(By.xpath("//span[contains(text(), 'Catalog')] /..")).click();

        driver.findElement(By.xpath("//a[contains(text(), 'Add New Product')]")).click();

        driver.findElement(By.xpath("//label[contains(text(), 'Enabled')] //input")).click();

        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("Quartz Watch");

        driver.findElement(By.cssSelector("input[name='code']")).sendKeys("qw001");

        driver.findElement(By.cssSelector("input[value='1-1'")).click();

        driver.findElement(By.cssSelector("input[name='quantity']")).clear();

        driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("20");

        ((JavascriptExecutor) driver).executeScript("arguments[0].selectedIndex = 2",
                driver.findElement(By.cssSelector("select[name='sold_out_status_id']")));

        driver.findElement(By.cssSelector("input[name='new_images[]'")).sendKeys(
                new File("src\\main\\resources\\quartz watch black.jpg").getAbsolutePath());

        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("29112021");

        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("29112025");

        driver.findElement(By.xpath("//a[contains(text(), 'Information')]")).click();

        driver.findElement(By.xpath("//select[@name='manufacturer_id'] //option[contains(text(), 'ACME')]")).click();

        driver.findElement((By.cssSelector("input[name='keywords']"))).sendKeys("watch, clock, quartz");

        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Quartz Watch Black");

        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys(
                "Movement type: quartz\n" +
                        "Dial: pointer\n" +
                        "Glass: mineral\n" +
                        "Marking type: Arabic numerals\n" +
                        "Size, cm: 4.4x4.4x1.2, strap length 19.5\n" +
                        "Features: backlight\n" +
                        "Additional features: calendar\n" +
                        "Waterproof: Yes\n" +
                        "Waterproof class: WR100");

        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("Quartz Watch");

        driver.findElement(By.cssSelector("input[name='meta_description[en]'")).sendKeys(
                "Quartz watches at a low price!");

        driver.findElement(By.xpath("//a[contains(text(), 'Prices')]")).click();

        driver.findElement(By.cssSelector("input[name='purchase_price'")).clear();

        driver.findElement(By.cssSelector("input[name='purchase_price'")).sendKeys("499.00");

        driver.findElement(By.cssSelector("select[name='purchase_price_currency_code'] option[value='USD']")).click();

        driver.findElement(By.cssSelector("input[name='gross_prices[USD]'")).clear();

        driver.findElement(By.cssSelector("input[name='gross_prices[USD]'")).sendKeys("499.00");

        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]']")).clear();

        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]']")).sendKeys("439.00");

        driver.findElement(By.cssSelector("button[name='save")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
