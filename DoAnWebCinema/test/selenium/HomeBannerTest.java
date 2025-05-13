package selenium;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class HomeBannerTest {

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
    public void testBannerHome() throws InterruptedException {
        WebElement buttonSlides = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("#carouselExampleCaptions > div.carousel-indicators > button:nth-child(2)")
        ));
        assertTrue("Next slide should be shown by button", buttonSlides.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonSlides);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSlides);

        WebElement buttonDetail = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("#carouselExampleCaptions > div.carousel-inner > div:nth-child(1) > div > h6.mt-3 > a")
        ));
        assertTrue("Button detail film should be clicked", buttonDetail.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonDetail);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonDetail);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.name("submit"));

        emailField.sendKeys("nguyenquockhoa5549@gmail.com");
        passwordField.sendKeys("05032003");
        submitButton.click();

        Thread.sleep(300);
        System.out.println("Đã đăng nhập.");
        assertTrue("Should be on detail film page", driver.getCurrentUrl().contains("/film/detail"));

        WebElement rating = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("body > div.detail-movie-container > div > div.rating-container > span:nth-child(5)")
        ));
        assertTrue("Rated 5 stars", rating.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", rating);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", rating);

        WebElement buttonSend = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#btn-send")));
        assertTrue("Film review submitted", buttonSend.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonSend);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSend);
        
        WebElement buttonClose = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#closeModal")));
        assertTrue("Close Modal", buttonClose.isDisplayed());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonClose);
        Thread.sleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonClose);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#rating-value")));
        WebElement textRating = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#rating-value")));
        String ratingText = textRating.getText().trim();
        assertEquals("Your Rating: 5", "Your Rating: 5", ratingText);
        
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        System.out.println("After all tests");
    }

}
