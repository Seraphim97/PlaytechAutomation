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
        Thread.sleep(3000);
    }

    @Test
    void playtechAutomationTest() throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.playtechpeople.com");
        Thread.sleep(3000);

        try {
            WebElement denyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Deny')]")));
            denyButton.click();
        } catch (Exception ignored) {
        }

        driver.findElement(By.xpath("//a[contains(text(),'Locations')]")).click();
        Thread.sleep(2000);

        String[] locationXpaths = {
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[1]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[4]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[7]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[10]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[13]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[16]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[2]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[5]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[8]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[11]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[14]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[3]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[6]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[9]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[12]/div/a",
                "/html/body/div[2]/header/div[2]/div/div/div/div/div[15]/div/a"
        };

        System.out.println("Found locations:");
        try (FileWriter writer = new FileWriter("test_results.txt")) {
            for (String xpath : locationXpaths) {
                try {
                    WebElement locationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                    String locationText = locationElement.getText();
                    System.out.println(locationText);
                    writer.write(locationText + "\n");
                } catch (Exception e) {
                    System.out.println("Could not find element: " + xpath);
                }
            }
        }

        WebElement menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"menu-item-49\"]/a")
        ));

        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();
        Thread.sleep(1000);

        WebElement submenuElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"menu-item-47\"]/a")
        ));
        submenuElement.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(2000);

        WebElement element = driver.findElement(By.xpath("//*[@id=\"component-4\"]/div[1]/div/div/div[2]/div[1]/div/p"));
        System.out.println(element.getText());

        WebElement firstElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/header/div[1]/div/div/div[3]/a")
        ));
        firstElement.click();
        Thread.sleep(2000);

        WebElement secondElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/section[1]/div[2]/div/div/div/div/div/form/div[2]/div[1]")
        ));
        secondElement.click();
        Thread.sleep(2000);

        WebElement thirdElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/section[1]/div[2]/div/div/div/div/div/form/div[2]/div[2]/div[6]")
        ));
        thirdElement.click();
        Thread.sleep(2000);

        WebElement fourthElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/section[1]/div[2]/div/div/div/div/div/form/input")
        ));
        fourthElement.click();
        Thread.sleep(2000);

        System.out.println("https://www.playtechpeople.com/jobs-our/?activeLocation=Estonia");
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }
}