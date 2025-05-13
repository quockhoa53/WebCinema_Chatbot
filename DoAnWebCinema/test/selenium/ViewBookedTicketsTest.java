package selenium;

import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewBookedTicketsTest {
    
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://localhost:9999/DoAnWebCinema/");
        System.out.println("Before each test - Browser opened and navigated to home page");
    }
    
    @Test
    public void testViewBookedTickets() throws InterruptedException{
        
        WebElement buttonProfile = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#top > div > div > div.col-md-4 > div > p > a")
        ));
        assertTrue("Page profile should be displayed", buttonProfile.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonProfile);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonProfile);

        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.name("submit"));

        emailField.sendKeys("nguyenquockhoa5549@gmail.com");
        passwordField.sendKeys("05032003");
        submitButton.click();
        
        WebElement buttonTransaction = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#orders-tab")
        ));
        assertTrue("List booked tickets should be displayed", buttonTransaction.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonTransaction);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonTransaction);

        WebElement buttonDetail = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#orders > div > div.card-body.shadow > div:nth-child(2) > div > div > div > div.col-2.d-flex.justify-content-center.gap-md-3.order-action > a > i")
        ));
        assertTrue("Detail tickets button should be displayed", buttonDetail.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonDetail);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonDetail);

        WebElement modalContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#order-details5568 > div > div > div > div > div")
        ));
        assertTrue("Ticket detail modal should be visible", modalContent.isDisplayed());
    }
    
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        System.out.println("After all tests");
    }
}
