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

public class PlaytechAutomation {
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

        // Try to close the cookie consent popup if it appears
        try {
            WebElement denyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Deny')]")));
            denyButton.click();
        } catch (Exception ignored) {
            // No cookie popup? No problem.
        }

        // Navigate to the "Locations" page to explore available locations
        driver.findElement(By.linkText("Locations")).click();
        Thread.sleep(2000); // Allow page to load

        // Find all links with the class "item-title" that represent country locations
        List<WebElement> locationLinks = driver.findElements(By.className("item-title"));

        System.out.println("Found locations:");
        try (FileWriter writer = new FileWriter("locations_list.txt")) {
            for (WebElement locationLink : locationLinks) {
                // Get the text of each location link
                String locationText = locationLink.getText();
                System.out.println(locationText);
                writer.write(locationText + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Hover over the menu item (for some other part of the site navigation)
        WebElement menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"menu-item-49\"]/a")
        ));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();
        Thread.sleep(1000); // Allow submenu to appear

        // Click on a submenu item
        WebElement submenuElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"menu-item-47\"]/a")
        ));
        submenuElement.click();

        // Scroll to the bottom of the page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(2000); // Allow page content to load

        // Get text from a specific page element
        WebElement element = driver.findElement(By.xpath("//*[@id=\"component-4\"]/div[1]/div/div/div[2]/div[1]/div/p"));
        System.out.println(element.getText());

        // Open the jobs page for Estonia
        driver.get("https://www.playtechpeople.com/jobs-our/?activeLocation=Estonia");
        Thread.sleep(3000); // Let the page load

        // Find all job elements
        List<WebElement> jobs = driver.findElements(By.xpath("//a[contains(@class, 'job-item')]"));

        System.out.println("Available jobs in Estonia:");

        boolean foundJobs = false;
        for (WebElement job : jobs) {
            // Extract job title
            String title = job.findElement(By.tagName("h6")).getText();

            // Extract location (should contain "Estonia")
            String location = job.findElement(By.className("location-link")).getText();

            // Extract job link
            String link = job.getAttribute("href");

            // Check if it's in Estonia
            if (location.toLowerCase().contains("estonia")) {
                System.out.println(title + " - " + link);
                foundJobs = true;
            }
        }

        // If no jobs were found
        if (!foundJobs) {
            System.out.println("No available jobs found in Estonia.");
        }
    }

    @AfterAll
    static void teardown() {
        driver.quit(); // Close everything when done
    }
}
