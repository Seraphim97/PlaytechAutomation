package org.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class BonusTask {
    private static WebDriver driver;

    @BeforeAll
    static void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(3000); // Allow browser to start properly
    }

    @Test
    void playtechAutomationTest() throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open Playtech website
        driver.get("https://www.playtechpeople.com");
        Thread.sleep(3000); // Wait for full load

        // Try to close the cookie consent popup if it appears by clicking on the "Deny" button using coordinates (280, 58)
        clickAtCoordinates(280, 58);

        // Click on the "Locations" button using coordinates (73.3, 18)
        clickAtCoordinates(73, 18);
        Thread.sleep(2000); // Allow page to load

        // List of locations, find all the location links
        List<WebElement> locationLinks = driver.findElements(By.className("item-title"));

        // Print and write to file all locations found
        try (FileWriter writer = new FileWriter("test_results.txt")) {
            System.out.println("Found locations:");
            for (WebElement locationLink : locationLinks) {
                String locationText = locationLink.getText();
                System.out.println(locationText);
                writer.write(locationText + "\n");
            }
        }

        // Click on "All Jobs" button using coordinates (126.5, 47.39)
        clickAtCoordinates(126, 47);

        // Wait for the "All Jobs" page to load and grab all job elements
        Thread.sleep(3000);
        List<WebElement> jobs = driver.findElements(By.xpath("//a[contains(@class, 'job-item')]"));

        System.out.println("Available jobs in Estonia:");
        boolean foundJobs = false;
        for (WebElement job : jobs) {
            // Extract job title and link
            String title = job.findElement(By.tagName("h6")).getText();
            String link = job.getAttribute("href");

            // Print job details
            System.out.println(title + " - " + link);
            foundJobs = true;
        }

        // If no jobs were found
        if (!foundJobs) {
            System.out.println("No available jobs found in Estonia.");
        }
    }

    // Utility method to click at specific coordinates
    private void clickAtCoordinates(int x, int y) {
        // Create a Point object with the desired coordinates
        Point point = new Point(x, y);
        // Use JavascriptExecutor to click at the given screen coordinates
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + x + ", " + y + ");");
        try {
            Thread.sleep(500); // Wait to make sure the element is in focus
            new Actions(driver).moveByOffset(x, y).click().perform();
        } catch (Exception e) {
            System.out.println("Error clicking at coordinates: " + x + ", " + y);
        }
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }
}
