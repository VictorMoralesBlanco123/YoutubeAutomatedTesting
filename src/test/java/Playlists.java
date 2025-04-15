import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.*;

/**
 * The Search class automates UI testing for the search functionality on YouTube.
 * It includes methods for setup, performing searches, validating search functionalities,
 * and cleaning up after tests. The test cases ensure that the search functionality
 * behaves as intended under various conditions, such as empty queries, query relevance,
 * search term retention, suggestion display, and interaction with buttons.
 */
public class Playlists {

    WebDriver driver;
    WebDriverWait wait;

    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";

    /**
     * Sets up the test environment before the test suite is executed.
     * <p>
     * This method initializes the Chrome WebDriver instance for browser automation
     * and applies a WebDriver wait with a specified timeout to handle asynchronous events.
     * Additionally, it maximizes the browser window to ensure tests are executed in a consistent
     * and visible user interface state.
     * <p>
     * Annotations:
     * - `@BeforeTest` ensures this method runs once before all test methods in this test suite.
     */
    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    /**
     * Navigates to the YouTube homepage.
     * <p>
     * This method directs the WebDriver instance to open the homepage of YouTube,
     * ensuring the test starts from a consistent initial state before executing
     * test scenarios. It is executed before each test method in the test class.
     * <p>
     * Annotations:
     * - `@BeforeMethod` ensures this method is invoked before each test method.
     */
    @BeforeMethod
    public void Homepage() {
        driver.get("https://www.youtube.com/");
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

    public void login() {
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.id("identifierId")).sendKeys(correctEmail);
        driver.findElement(By.id("identifierNext")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys(correctPassword);
        driver.findElement(By.id("passwordNext")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='avatar-btn']")));
        Assert.assertTrue(driver.findElement(By.id("avatar-btn")).isDisplayed(), "Login failed with valid credentials.");
    }

    @Test (description = "Creates a youtube playlist", priority = 1)
    public void testCreatePlaylist() {
        login();

        driver.get("https://www.youtube.com/watch?v=YaXJeUkBe4Y");


        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Save to playlist' or .//div[text()='Save']]")
        ));
        saveButton.click();
        Wait(1);
        WebElement newPlaylist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='New playlist']")
        ));
        newPlaylist.click();
        Wait(1);
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[@placeholder='Choose a title']")
        ));

        title.sendKeys("My Test Playlist");

        WebElement create = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//div[text()='Create']]")
        ));

        Wait(1);
        create.click();

        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Saved to')]")
        ));
        Wait(1);
        Assert.assertTrue(alert.isDisplayed());
    }

    @Test(description = "Adds a video to your created playlist", priority = 2)
    public void addVideoToPlaylist() throws InterruptedException {

        String[] videoUrls = {
                "https://www.youtube.com/watch?v=oLc9gVM8FBM",
                "https://www.youtube.com/watch?v=vwqV-2nXWPg"
        };

        for (String videoUrl : videoUrls) {
            driver.get(videoUrl);

            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@aria-label='Save to playlist' or .//div[text()='Save']]")
            ));
            saveButton.click();

            Thread.sleep(1000); // Simple wait

            WebElement checkBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//yt-formatted-string[text() = 'My Test Playlist']/ancestor::tp-yt-paper-checkbox")
            ));
            checkBox.click();

            Thread.sleep(1000); // Simple wait

            boolean isChecked = checkBox.getAttribute("aria-checked").equals("true");
            Assert.assertTrue(isChecked, "The playlist checkbox is not checked for video: " + videoUrl);
        }
    }

    @Test (description = "Edits the playlist using drag and drop", priority = 3)
    public void testEditPlaylistOrder() throws InterruptedException {

        driver.get("https://www.youtube.com/feed/playlists");

        WebElement viewPlaylist = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("View full playlist")
        ));
        Wait(1);
        viewPlaylist.click();
        wait.until(ExpectedConditions.urlContains("/playlist?list="));
        Assert.assertTrue(driver.getCurrentUrl().contains("/playlist?list="), "Did not navigate to playlist.");

        WebElement dragItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//yt-formatted-string[text() = '3']/ancestor::div[@id='index-container']")
        ));
        WebElement dropItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//yt-formatted-string[text() = '2']/ancestor::div[@id='index-container']")
        ));
        WebElement dropItem2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//yt-formatted-string[text() = '1']/ancestor::div[@id='index-container']")
        ));

        Actions actions = new Actions(driver);
        actions.clickAndHold(dragItem)
                .moveToElement(dropItem2)
                .pause(Duration.ofMillis(2000))
                .moveToElement(dropItem)
                .pause(Duration.ofMillis(1000))
                .release()
                .perform();

        Wait(2);
    }

    @Test (description = "Edits a created playlist's name", priority = 4)
    public void testEditPlaylistName() throws InterruptedException {

        driver.get("https://www.youtube.com/feed/playlists");
        WebElement moreActions = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='More actions' or @title='More actions']")
        ));
        moreActions.click();
        WebElement edit = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//yt-list-item-view-model//span[text()='Edit']")
        ));
        edit.click();

        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.style-scope.tp-yt-paper-input")
        ));

        description.clear();
        description.sendKeys("Software Testing Videos");

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='actions']//button[.//div[text()='Save']]"))
        );
        Wait(1);
        saveButton.click();

        Wait(2);




    }

    @Test (description = "Deletes your created playlist", priority = 5)
    public void testDeletePlaylist() throws InterruptedException {

        driver.get("https://www.youtube.com/feed/playlists");

        WebElement moreActions = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='More actions' or @title='More actions']")
        ));
        moreActions.click();

        WebElement delete = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//yt-list-item-view-model//span[text()='Delete']")
        ));
        delete.click();
        Wait(2);
        assertTrue(driver.findElements(
                By.linkText("My Test Playlist")).isEmpty(), "Playlist was not deleted.");

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

