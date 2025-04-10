import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

// comments coming soon
// -Evan Barbur
public class Login {
    WebDriver driver;
    WebDriverWait wait;

    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";
    String incorrectPassword = "WrongP@ssword123";

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com");
    }

    @Test (description = "Tests logging in with valid credentials", priority = 1)
    public void testValidLogin() {
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.id("identifierId")).sendKeys(correctEmail);
        driver.findElement(By.id("identifierNext")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys(correctPassword);
        driver.findElement(By.id("passwordNext")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='avatar-btn']")));
        Assert.assertTrue(driver.findElement(By.id("avatar-btn")).isDisplayed(), "Login failed with valid credentials.");
    }

    @Test (description = "Tests logging out the user", priority = 2)
    public void testLogout() throws InterruptedException {
        driver.findElement(By.id("avatar-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Sign out"))).click();
        Assert.assertTrue(driver.getPageSource().contains("Sign in"), "User may not be logged out.");
        Thread.sleep(3000);
    }

    @Test (description = "Tests if user is redirected to homepage after logout", priority = 3)
    public void testLogoutRedirect() throws InterruptedException {
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assert.assertTrue(currentUrl.contains("youtube.com"), "User was not redirected to the homepage.");
    }

    @Test (description = "Tests logging in with invalid credentials", priority = 4)
    public void testInvalidLogin() throws InterruptedException {
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.xpath("//div[contains(text(), 'Use another account')]")).click();
        driver.findElement(By.id("identifierId")).sendKeys(correctEmail);
        driver.findElement(By.id("identifierNext")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys(incorrectPassword);
        driver.findElement(By.id("passwordNext")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), 'Wrong password')]")
        ));
        Assert.assertTrue(error.isDisplayed(), "Error message for wrong password not shown.");
    }

    @Test (description = "Tests logging in with no credentials inputted", priority = 5)
    public void testEmptyLoginFields() {
        driver.get("https://www.youtube.com");
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.xpath("//div[contains(text(), 'Use another account')]")).click();
        driver.findElement(By.id("identifierNext")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Enter an email')]")
        ));
        Assert.assertTrue(error.isDisplayed(), "Error message for empty field not shown.");
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
