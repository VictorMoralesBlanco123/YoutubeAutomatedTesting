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


/**
 * This class contains automated test cases for logging in and out of a YouTube account.
 * It uses Selenium WebDriver and TestNG to perform the tests and validate the login functionality.
 * The various scenarios tested include successful login, login failures with incorrect or empty fields,
 * logging out, and verifying redirects after logout.
 *
 * Methods in this class are annotated with TestNG annotations for setup, testing, and teardown operations.
 */
public class Login {
    WebDriver driver;
    WebDriverWait wait;

    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";
    String incorrectPassword = "WrongP@ssword123";

    /**
     * Initializes the Chrome WebDriver and WebDriverWait for use in the test class.
     * This method is executed before any test is run and performs the following setup actions:
     * - Creates a new ChromeDriver instance.
     * - Configures a WebDriverWait with a timeout of 10 seconds.
     * - Maximizes the browser window.
     * - Navigates to the YouTube homepage.
     */
    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com");
    }

    /**
     * Tests the login functionality with valid credentials.
     *
     * This test performs the following actions:
     * 1. Navigates to the login page by clicking the sign-in link.
     * 2. Enters a valid email address into the email field and proceeds to the next step.
     * 3. Waits for the password field to become visible and enters a valid password.
     * 4. Completes the login process by submitting the credentials.
     *
     * The test validates successful login by ensuring the presence of the avatar button,
     * which signifies that the user has been authenticated and redirected to the appropriate page.
     *
     * Assertions:
     * - Validates that the avatar button is displayed after logging in with valid credentials.
     *
     * Priority:
     * - This test is assigned a priority of 1, indicating it should run early in the test sequence.
     */
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

    /**
     * Tests the logout functionality of the application.
     *
     * This method performs the following actions:
     * 1. Triggers the logout dropdown menu by clicking the avatar button.
     * 2. Waits for the "Sign out" option to become visible and selects it.
     * 3. Verifies successful logout by checking for the presence of "Sign in" on the page.
     *
     * Assertions:
     * - Validates that the "Sign in" text is present on the page, which indicates
     *   that the logout process was successful.
     *
     * Priority:
     * - This test is assigned a priority of 2, indicating it should run after the login test.
     *
     * @throws InterruptedException if the thread is interrupted during the wait.
     */
    @Test (description = "Tests logging out the user", priority = 2)
    public void testLogout() throws InterruptedException {
        driver.findElement(By.id("avatar-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Sign out"))).click();
        Assert.assertTrue(driver.getPageSource().contains("Sign in"), "User may not be logged out.");
        Thread.sleep(3000);
    }

    /**
     * Tests if the user is redirected to the homepage after logging out.
     *
     * This test performs the following actions:
     * 1. Waits for a brief duration to allow redirection to complete.
     * 2. Retrieves the current URL of the browser.
     * 3. Validates that the user has been redirected to the homepage by asserting
     *    that the URL contains "youtube.com".
     *
     * Assertions:
     * - Validates that the current URL after logout contains "youtube.com",
     *   ensuring that the redirection to the homepage was successful.
     *
     * Priority:
     * - This test is assigned a priority of 3, indicating it should run
     *   after the logout test.
     *
     * @throws InterruptedException if the thread is interrupted during the wait.
     */
    @Test (description = "Tests if user is redirected to homepage after logout", priority = 3)
    public void testLogoutRedirect() throws InterruptedException {
        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assert.assertTrue(currentUrl.contains("youtube.com"), "User was not redirected to the homepage.");
    }

    /**
     * Tests the login functionality with invalid credentials.
     *
     * This method performs the following actions:
     * 1. Navigates to the login page by clicking the sign-in link.
     * 2. Selects the option to use another account.
     * 3. Enters a valid email address into the email field and proceeds to the next step.
     * 4. Waits for the password field to become visible and enters an invalid password.
     * 5. Submits the invalid credentials and waits for the error message to appear.
     *
     * The test validates that the error message for a wrong password is displayed.
     *
     * Assertions:
     * - Verifies that the error message indicating a wrong password is visible.
     *
     * Priority:
     * - This test is assigned a priority of 4, indicating it should run later in the test sequence.
     *
     * @throws InterruptedException if the thread is interrupted during the wait.
     */
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

    /**
     * Tests logging in with no credentials inputted.
     *
     * This test performs the following actions:
     * 1. Navigates to the login page by clicking the sign-in link.
     * 2. Attempts to proceed without entering any credentials.
     * 3. Waits for the error message prompting the user to enter an email to become visible.
     * 4. Validates that the error message is displayed when required fields are left empty.
     *
     * Assertions:
     * - Verifies that the error message for empty fields is visible.
     *
     * Priority:
     * - This test is assigned a priority of 5, indicating it should run later in the test sequence.
     */
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


    /**
     * Cleans up the test environment after all tests have been executed.
     *
     * This method closes and quits the WebDriver instance that was initialized
     * for running the tests, ensuring that browser resources are properly released.
     * It is executed after all test methods in the test class have completed.
     */
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
