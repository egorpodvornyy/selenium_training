package ru.selenium.raif.maven;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AutorizationInLitecart {

    public static void authorizeAdmin(WebDriver driver) {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
}
