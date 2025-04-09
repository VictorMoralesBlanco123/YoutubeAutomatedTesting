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
 * The HomeScreenSmokeTesting class represents a test automation suite for validating
 * various functionalities and elements of the YouTube homepage. This class
 * is built using Selenium WebDriver for browser interaction and TestNG
 * annotations for structuring and executing test cases.

 * Fields:
 * - driver: Instance of WebDriver for browser operations.
 * - wait: Instance of WebDriverWait for explicit wait handling.
 * - baseUrl: Base URL of the YouTube homepage.

 * Methods:
 * - Setup(): Initializes the WebDriver, sets up WebDriverWait, and loads the YouTube homepage.
 * - OpenWebsite(): Verifies that the website opens successfully by checking the current URL.
 * - ShortsLink(): Validates the functionality and availability of the Shorts link on the homepage.
 * - YoutubeIconLink(): Checks the functionality of the main YouTube icon link in the header.
 * - HistoryLink(): Verifies the History link functionality in the navigation menu.
 * - YouLink(): Validates the availability and functionality of the You link in the navigation menu.
 * - SubscriptionsLink(): Checks the functionality of the Subscriptions link.
 * - HomeLink(): Tests the availability and functionality of the Home link in the navigation menu.
 * - LoginLink(): Verifies access to the Login link and its functionality.
 * - Screenshot(String path): Captures a screenshot and saves it to the specified path.
 * - Wait(int seconds): Pauses the test execution for the specified number of seconds.
 * - TearDown(): Closes the browser and ends the WebDriver session.

 * This test class is designed for interactive and automated validation of YouTube's
 * UI elements and behavior, ensuring that key navigation links and features work as expected.
 */
public class HomeScreenSmokeTesting {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "https://www.youtube.com/";

    @BeforeTest
    public void Setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.get("https://www.youtube.com/");
    }

    @Test(description = "Verifies the website opens", priority = 1)
    public void OpenWebsite() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, baseUrl);
        driver.manage().window().maximize();
        Wait(3);
        Screenshot("Verify website");
    }

    @Test(description = "Checks Shorts link", priority = 2)
    public void ShortsLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[2]/a")));
        WebElement shortsLink;
        shortsLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[2]/a"));
        shortsLink.click();
        Wait(3);
        Screenshot("Shorts link");
    }

    @Test(description = "Checks Youtube icon link", priority = 3)
    public void YoutubeIconLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[1]/ytd-topbar-logo-renderer/a/div/ytd-logo/yt-icon/span/div")));
        WebElement youtubeIconLink;
        youtubeIconLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[1]/ytd-topbar-logo-renderer/a/div/ytd-logo/yt-icon/span/div"));
        youtubeIconLink.click();
        Wait(3);
        Screenshot("Youtube icon link");
    }

    @Test(description = "Checks History link", priority = 4)
    public void HistoryLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[2]/a")));
        WebElement historyLink;
        historyLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[2]/a"));
        historyLink.click();
        Wait(3);
        Screenshot("History link");
    }

    @Test(description = "Checks You link", priority = 5)
    public void YouLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[1]/a")));
        WebElement youLink;
        youLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[2]/div/ytd-guide-entry-renderer[1]/a"));
        youLink.click();
        Wait(3);
        Screenshot("You link");
    }

    @Test(description = "Checks Subscriptions link", priority = 6)
    public void SubscriptionsLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[3]/a")));
        WebElement subscriptionsLink;
        subscriptionsLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[3]/a"));
        subscriptionsLink.click();
        Wait(3);
        Screenshot("Subscriptions link");
    }

    @Test(description = "Checks Home link", priority = 7)
    public void HomeLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[1]/a")));
        WebElement homeLink;
        homeLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/tp-yt-app-drawer/div[2]/div/div[2]/div[2]/ytd-guide-renderer/div[1]/ytd-guide-section-renderer[1]/div/ytd-guide-entry-renderer[1]/a"));
        homeLink.click();
        Wait(3);
        Screenshot("Home link");
    }

    @Test(description = "Checks Login link", priority = 8)
    public void LoginLink() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[3]/div[2]/ytd-button-renderer/yt-button-shape/a")));
        WebElement loginLink;
        loginLink = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/div[2]/ytd-masthead/div[4]/div[3]/div[2]/ytd-button-renderer/yt-button-shape/a"));
        loginLink.click();
        Wait(3);
        Screenshot("Login Link");
    }

    public void Screenshot(String path) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("C:\\Users\\Shadow\\Desktop\\ScreenshotsForYoutubeProject\\" + path);
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Screenshot taken at: " + path);
    }

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
