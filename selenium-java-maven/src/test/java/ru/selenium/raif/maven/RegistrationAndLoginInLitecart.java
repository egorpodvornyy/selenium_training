package ru.selenium.raif.maven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationAndLoginInLitecart {

    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> elements;
    private String email = "john-smith-@gmail.com";
    private String password = "qwerty1";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/en/");
    }

    @Test
    public void testCreateAccountAndLogin() {
        driver.findElement(By.xpath("//*[contains(text(), 'New customers click here')]")).click();

        driver.findElement(By.xpath("(//input[@type='text'])[1]")).click();

        elements = driver.findElements(By.xpath("//input"));

        for (int i = 1; i < elements.size(); i++) {

            if (elements.get(i).findElements(By.xpath("//../span")).size() == 0) {

                if (elements.get(i).getAttribute("name").equals("newsletter")) {
                    if (elements.get(i).isSelected()) {
                        elements.get(i).click();
                    }
                }

                continue;
            }

            if (elements.get(i).getAttribute("name").equals("firstname")) {
                elements.get(i).sendKeys("John");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("lastname")) {
                elements.get(i).sendKeys("Smith");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("address1")) {
                elements.get(i).sendKeys("775 Westminster Avenue APT D5");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("postcode")) {
                elements.get(i).sendKeys("11230");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("city")) {
                elements.get(i).sendKeys("New York");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("email")) {
                email = new StringBuilder(email).insert(11, new Random().nextInt(5000)).toString();
                elements.get(i).sendKeys(email);
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("phone")) {
                elements.get(i).sendKeys("+79998882345");
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("password")) {
                elements.get(i).sendKeys(password);
                continue;
            }

            if (elements.get(i).getAttribute("name").equals("confirmed_password")) {
                elements.get(i).sendKeys(password);
            }
        }

        driver.findElement(By.cssSelector("select[name='country_code'] option[value='US'")).click();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
                By.cssSelector("select[name='zone_code']"))));

        driver.findElement(By.cssSelector("button[name='create_account'")).click();

        logOut();

        // LogIn

        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email + Keys.TAB);

        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password + Keys.ENTER);

        logOut();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    public void logOut() {
        driver.findElement(By.linkText("Logout")).click();
    }
}
