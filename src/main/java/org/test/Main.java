package org.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        URL driverPath = Main.class.getClassLoader().getResource("chromedriver.exe");
        if (driverPath == null) {
            throw new RuntimeException("chromedriver.exe not found in resources!");
        }

        try {
            String correctPath = Paths.get(driverPath.toURI()).toString();
            System.setProperty("webdriver.chrome.driver", correctPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error processing path to chromedriver.exe", e);
        }

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}