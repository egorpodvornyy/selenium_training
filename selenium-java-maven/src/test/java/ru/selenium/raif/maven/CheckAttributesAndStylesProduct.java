package ru.selenium.raif.maven;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class CheckAttributesAndStylesProduct {

    private WebDriver driver;
    private WebDriverWait wait;
    private String param1;
    private String param2;
    private String regularPriceStartPage = "#box-campaigns a.link[title='Yellow Duck'] s.regular-price";
    private String campaignPriceStartPage = "#box-campaigns a.link[title='Yellow Duck'] strong.campaign-price";
    private String regularPriceProductPage = "#box-product s.regular-price";
    private String campaignPriceProductPage = "#box-product strong.campaign-price";

    @Before
    public void start() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
        driver.get("http://localhost/litecart/en/");
    }

    @Test
    public void checkNameProduct() throws Exception {
         param1 = driver.findElement(By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).getAttribute("textContent");

         clickOnProduct();

         if(param1.compareTo(driver.findElement(By.cssSelector("#box-product h1")).getAttribute("textContent")) != 0) {
             throw new Exception("Название товара не совпадает");
         }
    }

    @Test
    public void checkPriceProduct() throws Exception {
        param1 = driver.findElement(By.cssSelector(regularPriceStartPage)).getAttribute("textContent");

        param2 = driver.findElement(By.cssSelector(campaignPriceStartPage)).getAttribute("textContent");

        clickOnProduct();

        if (param1.compareTo(driver.findElement(By.cssSelector(regularPriceProductPage)).getAttribute("textContent")) != 0) {
            throw new Exception("Обычная цена товара не совпадает");
        }

        if (param2.compareTo(driver.findElement(By.cssSelector(campaignPriceProductPage)).getAttribute("textContent")) != 0) {
            throw new Exception("Акционная цена товара не совпадает");
        }
    }

    @Test
    public void checkFormatAndColorFontRegularPrice() throws Exception {
        if (!driver.findElement(By.cssSelector(regularPriceStartPage)).
                getCssValue("text-decoration").contains("line-through")) {

            throw new Exception("На главной странице обычная цена товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " не зачеркнута");
        }

        if(!colorIsGray(
                driver.findElement(By.cssSelector(regularPriceStartPage)).
                getCssValue("color").replaceAll("[^0-9 ]", "").split(" "))) {

            throw new Exception("Цвет обычной цены товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " на главной странице не серый");
        }

        clickOnProduct();

        if(!driver.findElement(By.cssSelector(regularPriceProductPage)).
                getCssValue("text-decoration").contains("line-through")) {

            throw new Exception("На странице товара " + driver.findElement(By.cssSelector("#box-product h1")).
                    getAttribute("textContent") + " обычная цена не зачеркнута");
        }

        if(!colorIsGray(
                driver.findElement(By.cssSelector(regularPriceProductPage)).
                getCssValue("color").replaceAll("[^0-9 ]", "").split(" "))) {

                throw new Exception("Цвет обычной цены товара " + driver.findElement(
                        By.cssSelector("#box-product h1")).
                        getAttribute("textContent") + " на странице товара не серый");
        }
    }

    @Test
    public void checkFormatAndColorFontCampaignPrice() throws Exception {

        if (Integer.parseInt(driver.findElement(By.cssSelector(campaignPriceStartPage)).
                getCssValue("font-weight")) < 600) {

            throw new Exception("На главной странице акционная цена товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " написана не жирным шрифтом");
        }

        if (!colorIsRed(
                driver.findElement(By.cssSelector(campaignPriceStartPage)).
                        getCssValue("color").replaceAll("[^0-9 ]", "").split(" "))) {

            throw new Exception("Цвет акционной цены товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " на главной странице не красный");
        }

        clickOnProduct();

        if (Integer.parseInt(driver.findElement(By.cssSelector(campaignPriceProductPage)).
                getCssValue("font-weight")) < 600) {

            throw new Exception("На странице товара акционная цена товара " + driver.findElement(
                    By.cssSelector("#box-product h1")).
                    getAttribute("textContent") + " написана не жирным шрифтом");
        }

        if (!colorIsRed(
                driver.findElement(By.cssSelector(campaignPriceProductPage)).
                        getCssValue("color").replaceAll("[^0-9 ]", "").split(" "))) {

            throw new Exception("Цвет акционной цены на странице товара " + driver.findElement(
                    By.cssSelector("#box-product h1")).
                    getAttribute("textContent") + " не красный");
        }

    }

    @Test
    public void checkSizeFontPrice() throws Exception {
        if (Double.parseDouble(driver.findElement(By.cssSelector(regularPriceStartPage)).getCssValue(
                "font-size").replaceAll("[^0-9.]", "")) >=
                Double.parseDouble(driver.findElement(By.cssSelector(campaignPriceStartPage)).getCssValue(
                        "font-size").replaceAll("[^0-9.]", ""))) {

            throw new Exception("На главной странице акционная цена товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " не крупнее обычной");
        }

        clickOnProduct();

        if (Double.parseDouble(driver.findElement(By.cssSelector(regularPriceProductPage)).getCssValue(
                "font-size").replaceAll("[^0-9.]", "")) >=
                Double.parseDouble(driver.findElement(By.cssSelector(campaignPriceProductPage)).getCssValue(
                        "font-size").replaceAll("[^0-9.]", ""))) {

            throw new Exception("На странице товара " + driver.findElement(
                    By.cssSelector("#box-campaigns a.link[title='Yellow Duck'] div.name")).
                    getAttribute("textContent") + " акционная цена не крупнее обычной");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    public void clickOnProduct() {
        driver.findElement(By.cssSelector("#box-campaigns a.link[title='Yellow Duck']")).click();
    }

    public boolean colorIsGray(String[] rgba) {
        for (int i = 0; i < rgba.length - 2; i++) {
            if (rgba[i].compareTo(rgba[i + 1]) != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean colorIsRed(String[] rgba) {
        for (int i = 1; i < rgba.length - 2; i++) {
            if (!rgba[i].equals("0")) {
                return false;
            }
        }
        return true;
    }
}
