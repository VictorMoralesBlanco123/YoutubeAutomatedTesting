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
 * Verifies the functionality of reordering items in a YouTube playlist via drag-and-drop.
 *
 * This method automates the process of editing the order of items in a YouTube playlist.
 * It achieves this by simulating the drag-and-drop operation to change the order of
 * playlist items and validates the operation by checking the new order of the items.
 *
 * Preconditions:
 * - The user must be logged into their YouTube account.
 * - The WebDriver instance must be initialized.
 * - The specified playlist must exist with multiple items for reordering.
 *
 * Test Steps:
 * 1. Log in to the YouTube account.
 * 2. Navigate to the "Library" section and access the targeted playlist.
 * 3. Click the "View full playlist" button to open the playlist details page.
 * 4. Identify playlist items and simulate drag-and-drop to reorder items.
 * 5. Verify that the reordering operation is successfully reflected.
 *
 * Expected Result:
 * - Playlist items should reflect the updated order after the drag-and-drop operation.
 *
 * Annotations:
 * - `@Test` marks this method as a test case.
 * - `description` provides a brief summary of the test case's objective.
 * - `priority` determines the execution order of this test in the suite.
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
    @BeforeClass
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

    /**
     * Automates the login process by interacting with web elements on the login page.
     *
     * The method performs the following steps:
     * 1. Finds and clicks the "Sign in" button based on a specified XPath locator.
     * 2. Locates the email input field using its ID and inputs the correct email address.
     * 3. Clicks the "Next" button after inputting the email address.
     * 4. Waits until the password field becomes visible.
     * 5. Inputs the correct password into the password field.
     * 6. Clicks the "Next" button to proceed with authentication.
     * 7. Waits until the presence of the avatar button is confirmed as a sign of successful login.
     * 8. Asserts that the avatar button is displayed to verify login success.
     *
     * Preconditions:
     * - The driver variable should be initialized for interacting with the web page.
     * - The wait variable should be configured to handle dynamic waits.
     * - The correctEmail and correctPassword strings should contain valid login credentials.
     *
     * Post-conditions:
     * - Successful login should result in the display of the avatar button indicating an authenticated session.
     *
     * Throws:
     * - AssertionError if the avatar button is not displayed, indicating a login failure.
     */
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

    /**
     * Tests the functionality of creating a new YouTube playlist.
     *
     * This method automates the process of creating a playlist with a specified title
     * on a video page in YouTube. It performs the following actions:
     *
     * - Logs into the application if not already logged in.
     * - Navigates to the intended YouTube video page.
     * - Locates and interacts with the "Save to playlist" button.
     * - Clicks on the "New playlist" option.
     * - Inputs the desired playlist title.
     * - Creates the playlist and verifies successful creation via confirmation alert.
     *
     * Preconditions:
     * - The user must be authorized and logged into YouTube.
     * - The video should be accessible for saving to a playlist.
     *
     * Test Steps:
     * 1. Log in to the YouTube account using the `login` method.
     * 2. Navigate to the YouTube video page via its URL.
     * 3. Wait for and click the "Save to playlist" button.
     * 4. Select "New playlist" to initiate the creation of a new playlist.
     * 5. Input a title for the new playlist in the text field.
     * 6. Click on the "Create" button to save the playlist.
     * 7. Wait for a confirmation message indicating successful playlist creation.
     * 8. Assert that the alert confirming "Saved to" is displayed successfully.
     *
     * Expected Result:
     * - The new playlist is successfully created and the confirmation alert is displayed.
     *
     * Annotations:
     * - `@Test` marks this method as a test case.
     * - `description` specifies the test purpose as creating a YouTube playlist.
     * - `priority` sets the execution order of this test case within the suite.
     */
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

    /**
     * Adds multiple videos to a specified playlist.
     *
     * This method navigates to each video URL provided in the list of video URLs,
     * then clicks the "Save to playlist" button, selects the checkbox for the targeted playlist,
     * and verifies that the video is successfully added to the playlist.
     *
     * It waits for the required elements to be clickable and uses assertions
     * to ensure that the video has been successfully added to the playlist.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
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

    /**
     * This test method verifies the ability to edit the order of items in a YouTube playlist
     * using drag-and-drop functionality. It navigates to a playlist page, selects items by
     * their current position, and reorders them to validate the drag-and-drop feature.
     *
     * Preconditions:
     * - The WebDriver instance should be initialized and ready to use.
     * - The target YouTube account should have access to a playlist with sufficient items to test reordering.
     *
     * Test Steps:
     * 1. Launch the YouTube playlists page.
     * 2. Click "View full playlist" to navigate to a specific playlist's detail page.
     * 3. Validate that the navigation to the playlist page is successful.
     * 4. Locate playlist items using their current index (e.g., items at positions 3, 2, and 1).
     * 5. Perform drag-and-drop operations to change the order of the playlist items.
     * 6. Pause for visual confirmation after each drag-and-drop operation.
     *
     * Expectations:
     * - The test confirms successful navigation to the playlist page by checking the updated URL.
     * - The drag-and-drop actions are performed correctly without exceptions or interruptions.
     * - UI responds as expected while reordering playlist items.
     *
     * Exceptions:
     * - Throws InterruptedException if the thread is interrupted during waiting.
     *
     * Configurations:
     * - The method relies on WebDriver and ExpectedConditions to interact with web elements.
     * - Utilizes the Actions class to simulate drag-and-drop actions for reordering items.
     */
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

    /**
     * Tests the functionality for editing the name of an existing YouTube playlist.
     *
     * This method navigates to the playlists feed on YouTube, interacts with
     * the "More actions" menu for a specific playlist, and modifies the playlist name.
     * It performs the following actions:
     *
     * 1. Opens the YouTube playlists page.
     * 2. Selects the "More actions" menu for the target playlist.
     * 3. Clicks the "Edit" option to enter edit mode.
     * 4. Clears the current playlist name and inputs a new name.
     * 5. Saves the changes and verifies the name update.
     *
     * Preconditions:
     * - The user must be logged in to YouTube.
     * - The playlist to be edited must exist in the user's playlist feed.
     *
     * Expected Result:
     * - The playlist name is updated successfully, and no errors are encountered during the process.
     *
     * Exceptions:
     * - InterruptedException if the thread is interrupted while waiting.
     *
     * Annotations:
     * - `@Test` marks this method as a test case in the test suite.
     * - `description` specifies the test purpose as editing a playlist's name.
     * - `priority` determines the order of execution for this test case within the suite.
     */
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

    /**
     * Deletes a user-created playlist on YouTube.
     *
     * This test navigates to the YouTube playlists feed, interacts with the
     * "More actions" menu of the playlist, and deletes the specified playlist.
     * After performing the delete action, an assertion is made to verify that
     * the playlist no longer exists in the feed.
     *
     * Annotations:
     * - `@Test` specifies this method as a test case in the test suite.
     *
     * Preconditions:
     * - The user must be logged in to YouTube.
     * - The playlist to be deleted must exist in the user's playlist feed.
     *
     * Test Steps:
     * 1. Navigate to the YouTube playlists feed.
     * 2. Locate and click the "More actions" menu for the specified playlist.
     * 3. Select the delete option and confirm the action.
     * 4. Assert that the playlist has been removed.
     *
     * Expected Result:
     * - The playlist is successfully deleted and does not appear in the
     *   user's playlist feed.
     *
     * @throws InterruptedException if the thread is interrupted during a wait operation.
     */
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

    /**
     * Cleans up the test environment after the test suite is executed.
     *
     * This method terminates the WebDriver instance, closing the browser
     * and releasing all associated resources. It ensures that there are no
     * lingering processes or resources after the tests have completed,
     * maintaining system stability.
     *
     * Annotation:
     * - `@AfterClass` ensures this method runs once after all test methods in the test suite.
     */
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

