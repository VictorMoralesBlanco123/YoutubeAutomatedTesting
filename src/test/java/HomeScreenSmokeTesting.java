import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


/**
 * A class for performing smoke testing on the YouTube home screen.
 * It includes setup, test cases for various UI elements, taking screenshots,
 * and cleanup after testing.
 */
public class HomeScreenSmokeTesting {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "https://www.youtube.com/";

    /**
     * Sets up the preconditions for the test execution.
     * Initializes the WebDriver instance for Chrome browser and configures
     * an explicit WebDriverWait with a timeout of 10 seconds. Navigates
     * to the YouTube homepage.
     *
     * This setup method is executed before any test methods marked with the
     * TestNG @Test annotation. It ensures the WebDriver is properly initialized
     * and points to the target URL for testing.
     */
    @BeforeTest
    public void Setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.get("https://www.youtube.com/");
    }

    /**
     * Verifies that the website opens successfully.
     *
     * This method:
     * - Waits for the visibility of the search bar element identified by its name.
     * - Retrieves the current URL of the webpage and asserts that it matches the expected base URL.
     * - Maximizes the browser window.
     * - Introduces a brief delay to allow for UI stabilization.
     * - Captures a screenshot of the current state of the website for verification purposes.
     *
     * @throws IOException If an input/output operation fails during the screenshot process.
     * @Test This is a TestNG test method that runs with the specified description and priority.
     */
    @Test(description = "Verifies the website opens", priority = 1)
    public void OpenWebsite() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, baseUrl);
        driver.manage().window().maximize();
        Wait(2);
        Screenshot("Verify website");
    }

    /**
     * Verifies the functionality of the "Shorts" link on the YouTube homepage.
     *
     * <ul>
     * - Waits for the "Shorts" link element to be visible on the page.
     * - Locates the "Shorts" link element using its XPath.
     * - Clicks on the "Shorts" link.
     * - Waits for a specified duration to allow for the page to load.
     * - Captures a screenshot of the visible state after clicking the link.
     * </ul>
     *
     * @throws IOException if there is an error while taking or saving the screenshot
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - The "Shorts" link element must be present and visible on the page.
     */
    @Test(description = "Checks Shorts link", priority = 2)
    public void ShortsLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[2]/a")));
        WebElement shortsLink;
        shortsLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[2]/a"));
        shortsLink.click();
        Wait(2);
        Screenshot("Shorts link");
    }

    /**
     * Verifies the functionality of the YouTube icon link on the homepage.
     *
     * This method:
     * - Waits for the YouTube icon link element to be visible on the page.
     * - Locates the YouTube icon link element using its XPath.
     * - Clicks on the YouTube icon link to redirect to the main YouTube page.
     * - Waits for a specified duration to allow for the page to load.
     * - Captures a screenshot of the current state after interacting with the icon link.
     *
     * @throws IOException if there is an error while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance should be initialized and directed to the YouTube homepage.
     * - Explicit wait must be configured to ensure the visibility of elements on the page.
     */
    @Test(description = "Checks Youtube icon link", priority = 3)
    public void YoutubeIconLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[1]/ytd-topbar-logo-renderer/a/div/ytd-logo/yt-icon/span/div")));
        WebElement youtubeIconLink;
        youtubeIconLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[1]/ytd-topbar-logo-renderer/a/div/ytd-logo/yt-icon/span/div"));
        youtubeIconLink.click();
        Wait(2);
        Screenshot("Youtube icon link");
    }

    /**
     * Verifies the functionality of the "History" link on the YouTube homepage.
     *
     * This method performs the following steps:
     * - Waits for the "History" link element to become visible.
     * - Locates the "History" link using its XPath.
     * - Clicks on the "History" link to navigate to the History page.
     * - Waits for a specified duration to ensure the page loads completely.
     * - Captures a screenshot of the current state after clicking the link.
     *
     * @throws IOException if there is an error while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - An explicit wait must be configured to ensure the visibility of web elements during the test execution.
     */
    @Test(description = "Checks History link", priority = 4)
    public void HistoryLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[2]/a")));
        WebElement historyLink;
        historyLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[2]/a"));
        historyLink.click();
        Wait(2);
        Screenshot("History link");
    }

    /**
     * Verifies the functionality of the "You" link on the YouTube homepage.
     *
     * This method performs the following steps:
     * - Waits for the "You" link element to be visible.
     * - Locates the "You" link element using its XPath.
     * - Clicks on the "You" link to navigate to the associated page.
     * - Pauses for a specified duration to allow the page to load completely.
     * - Captures a screenshot of the current state after clicking the link.
     *
     * @throws IOException if there is an error while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - Explicit wait must be configured to ensure the visibility of web elements during the test execution.
     */
    @Test(description = "Checks You link", priority = 5)
    public void YouLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[1]/a")));
        WebElement youLink;
        youLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[1]/a"));
        youLink.click();
        Wait(2);
        Screenshot("You link");
    }

    /**
     * Verifies the functionality of the "Subscriptions" link on the YouTube homepage.
     *
     * This method performs the following steps:
     * - Waits for the "Subscriptions" link element to become visible on the page.
     * - Locates the "Subscriptions" link element using its XPath.
     * - Clicks on the "Subscriptions" link to navigate to the associated page.
     * - Waits for a specified duration to ensure the page fully loads after navigation.
     * - Captures a screenshot of the page after clicking the "Subscriptions" link.
     *
     * @throws IOException If there is an error while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - An explicit wait must be configured to ensure the visibility of web elements during the test execution.
     */
    @Test(description = "Checks Subscriptions link", priority = 6)
    public void SubscriptionsLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[3]/a")));
        WebElement subscriptionsLink;
        subscriptionsLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[3]/a"));
        subscriptionsLink.click();
        Wait(2);
        Screenshot("Subscriptions link");
    }

    /**
     * Verifies the functionality of the "Home" link on the YouTube homepage.
     *
     * This method performs the following actions:
     * - Waits for the "Home" link element to become visible using an explicit wait.
     * - Locates the "Home" link element by its XPath.
     * - Clicks on the "Home" link to navigate to the homepage.
     * - Introduces a delay to allow the page to load completely.
     * - Captures a screenshot of the current state after interacting with the "Home" link.
     *
     * @throws IOException if an error occurs while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - An explicit wait must be configured to ensure the visibility of web elements during the test execution.
     */
    @Test(description = "Checks Home link", priority = 7)
    public void HomeLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[1]/a")));
        WebElement homeLink;
        homeLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[1]/a"));
        homeLink.click();
        Wait(2);
        Screenshot("Home link");
    }

    /**
     * Verifies the functionality of the "Login" link on the YouTube homepage.
     *
     * This method performs the following steps:
     * - Waits for the "Login" link element to become visible on the page.
     * - Locates the "Login" link element using its XPath.
     * - Clicks on the "Login" link to navigate to the login page.
     * - Waits for a specified duration to allow the page to load completely.
     * - Captures a screenshot of the current state after clicking the link.
     *
     * @throws IOException if there is an error while taking or saving the screenshot.
     *
     * Preconditions:
     * - The WebDriver instance must be properly initialized and pointing to the YouTube homepage.
     * - An explicit wait must be configured to ensure the visibility of web elements during test execution.
     */
    @Test(description = "Checks Login link", priority = 8)
    public void LoginLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[3]/div[2]/ytd-button-renderer/yt-button-shape/a")));
        WebElement loginLink;
        loginLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[3]/div[2]/ytd-button-renderer/yt-button-shape/a"));
        loginLink.click();
        Wait(2);
        Screenshot("Login Link");
    }

    /**
     * Captures a screenshot of the current state of the browser and saves it to the specified path.
     *
     * The method uses the `TakesScreenshot` interface to take a screenshot, and stores it in a location
     * under the user's home directory with the specified file name. If the directory or file already
     * exists, it overwrites the existing file.
     *
     * @param path The name of the file (without extension) to save the screenshot in the directory
     *             "ScreenshotsForYoutubeProject" under the user's home directory. The final file will
     *             have a `.png` extension.
     * @throws IOException If an I/O error occurs while copying the screenshot file to the specified path.
     */
    public void Screenshot(String path) throws IOException {

        String userHome = System.getProperty("user.home");

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(userHome + File.separator + "ScreenshotsForYoutubeProject" + File.separator + path + ".png");
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Screenshot taken at: " + userHome + File.separator + "ScreenshotsForYoutubeProject" + File.separator + path + ".png");
    }

    /**
     * Pauses the execution of the current thread for a specified duration.
     *
     * @param seconds The duration to pause, expressed in seconds.
     */
    public void Wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void TearDown() {
        driver.quit();
    }
}
