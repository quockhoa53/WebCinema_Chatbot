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

public class ChangePasswordTest {
	
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
    
    //Test case 1: đổi mật khẩu thành công
    @Test
    public void testChangePasswordSuccess() throws InterruptedException {
    	
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
        
        WebElement buttonChangePass = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#dashboard-tab")
            ));
            assertTrue("Page change password should be displayed",  buttonChangePass.isDisplayed());

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  buttonChangePass);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonChangePass);
            
        WebElement currentPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        	    By.cssSelector("#input-current-password")));
        	currentPasswordField.sendKeys("05032003");
         
         WebElement newPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-new-password")));
         newPasswordField.sendKeys("05032003");
         
         WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-confirm-new-password")));
         confirmPasswordField.sendKeys("05032003");
         
         WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                 By.cssSelector("#dashboard > div > div.ten > div > div > form > div.form-group.col-12 > button")
             ));
             assertTrue("Password should be changed success",  button.isDisplayed());

             ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  button);
             Thread.sleep(300);
             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
    
    //Test case 2: nhập mật khẩu cũ không đúng
    @Test
    public void testChangePasswordFailOldPassword() throws InterruptedException {
    	
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
        
        WebElement buttonChangePass = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#dashboard-tab")
            ));
            assertTrue("Page change password should be displayed",  buttonChangePass.isDisplayed());

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  buttonChangePass);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonChangePass);
            
        WebElement currentPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        	    By.cssSelector("#input-current-password")));
        	currentPasswordField.sendKeys("05032002");//Sai mật khẩu cũ
         
         WebElement newPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-new-password")));
         newPasswordField.sendKeys("05032003");
         
         WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-confirm-new-password")));
         confirmPasswordField.sendKeys("05032003");
         
         WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                 By.cssSelector("#dashboard > div > div.ten > div > div > form > div.form-group.col-12 > button")
             ));
             assertTrue("Password should be changed success",  button.isDisplayed());

             ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  button);
             Thread.sleep(300);
             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
    
    //Test case 3: xác nhận mật khẩu mới không đúng
    @Test
    public void testChangePasswordFailConfirmPassword() throws InterruptedException {
    	
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
        
        WebElement buttonChangePass = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#dashboard-tab")
            ));
            assertTrue("Page change password should be displayed",  buttonChangePass.isDisplayed());

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  buttonChangePass);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonChangePass);
            
        WebElement currentPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        	    By.cssSelector("#input-current-password")));
        	currentPasswordField.sendKeys("05032003");
         
         WebElement newPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-new-password")));
         newPasswordField.sendKeys("05032002");
         
         WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(
        		 By.cssSelector("#input-confirm-new-password")));
         confirmPasswordField.sendKeys("05032000");//Mật khẩu xác nhân không khớp với mật khẩu mới
         
         WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                 By.cssSelector("#dashboard > div > div.ten > div > div > form > div.form-group.col-12 > button")
             ));
             assertTrue("Password should be changed success",  button.isDisplayed());

             ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  button);
             Thread.sleep(300);
             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
	
    
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        System.out.println("After all tests");
    }
}
