package selenium;

import static org.junit.Assert.assertEquals;
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

public class UpdateProfileTest {


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
    public void testUpdateProfile() throws InterruptedException {
    	
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
            
       WebElement addressField = driver.findElement(By.cssSelector("#account-detail > div > form > div > div > div > div.col-8 > div > div:nth-child(4) > input"));
       addressField.clear(); 
       addressField.sendKeys("Go Cong Tay, Tien Giang");
       
       WebElement buttonConfirm = wait.until(ExpectedConditions.elementToBeClickable(
               By.cssSelector("#account-detail > div > form > div > div > div > div.col-8 > div > div:nth-child(6) > button")
           ));
       assertTrue("Personal Information should be saved", buttonConfirm.isDisplayed());

       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonConfirm);
       Thread.sleep(300);
       ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonConfirm);
    }
    
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        System.out.println("After all tests");
    }

}
