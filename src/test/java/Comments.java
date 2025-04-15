import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Represents a test class for automating actions and interactions on videos
 * and comments sections of YouTube using Selenium WebDriver.
 *
 * The class contains methods to perform the following:
 * - Scroll to specific sections of a web page.
 * - Add comments to a video and verify the sorting of comments.
 * - Check like and dislike functionality on videos.
 * - Save a video to a watch list.
 *
 * All methods employ WebDriver's wait functionality to handle dynamic content
 * and ensure reliable execution.
 */
public class Comments {
    WebDriver driver;
    WebDriverWait wait;
    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";

    /**
     * Sets up the preconditions required for executing tests involving YouTube automation.
     *
     * This method performs the following actions:
     * - Initializes the web driver instance using `ChromeDriver`.
     * - Sets up an explicit wait mechanism to handle dynamic content loading.
     * - Maximizes the browser window for better visibility and interaction.
     * - Navigates to the YouTube homepage.
     * - Automates the login process by:
     *   - Clicking the 'Sign in' button to open the login interface.
     *   - Entering the email and password credentials.
     *   - Progressing through the login workflow by interacting with 'Next' buttons.
     *   - Waiting for specific elements to confirm successful authentication.
     * - Verifies that the login was successful by checking the visibility of the avatar button.
     * - Navigates to a specific YouTube video to prepare for subsequent test actions.
     *
     * The method ensures that the required environment is established and that the user
     * is successfully authenticated before proceeding with further interactions or tests.
     * Proper handling of dynamic elements and synchronization is achieved by leveraging
     * explicit waits.
     */
    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com");

        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.id("identifierId")).sendKeys(correctEmail);

        driver.findElement(By.id("identifierNext")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys(correctPassword);

        driver.findElement(By.id("passwordNext")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='avatar-btn']")));
        Assert.assertTrue(driver.findElement(By.id("avatar-btn")).isDisplayed(), "Login failed with valid credentials.");

        driver.get("https://www.youtube.com/watch?v=Q3boOPiTFHY");
    }

    /**
     * Scrolls to the comment box section of a web page by targeting a specific
     * element on the page and bringing it into view.
     *
     * This method performs the following actions:
     * - Waits for a specified duration to allow page content to load.
     * - Identifies the title element using the provided XPath expression.
     * - Waits until the title element becomes visible on the page.
     * - Uses a JavaScript Executor to scroll the page, ensuring the title element
     *   is brought into view with centered alignment.
     * - Introduces a final wait to allow the UI to settle after scrolling.
     *
     * The method ensures reliable scrolling behavior through WebDriver's
     * wait functionality and JavaScript execution, enabling interaction with
     * dynamically loaded content.
     */
    @Test(description = "Scrolls to Comment Box", priority = 1)
    public void scrollScreen() {
        Wait(1);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[1]/h1")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", title);
        Wait(1);
    }

    /**
     * Automates the action of adding a comment to a comment section on a web page.
     *
     * This method performs the following tasks:
     * - Waits until the comment text box becomes visible on the page.
     * - Clicks on the comment text box to activate it.
     * - Introduces a short wait to allow the UI to update.
     * - Waits for the text input field to become visible.
     * - Enters a predefined comment message into the text input field.
     * - Introduces another short pause for synchronization.
     * - Waits for the comment button to become visible.
     * - Clicks the comment button to submit the comment.
     * - Includes a final delay to ensure that the comment is posted successfully.
     *
     * The method employs WebDriver's `wait` functionality to ensure that all dynamic elements
     * are visible before interaction, ensuring reliable execution in dynamic web environments.
     *
     * Test priority for this method is set to 2.
     */
    @Test(description = "Adds Comment", priority = 2)
    public void addComment() {
        WebElement commentTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("placeholder-area")));
        commentTextBox.click();
        Wait(1);
        WebElement enterText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-comments/ytd-item-section-renderer/div[1]/ytd-comments-header-renderer/div[5]/ytd-comment-simplebox-renderer/div[3]/ytd-comment-dialog-renderer")));
        enterText.sendKeys("Don't mind me, I'm just testing something");
        Wait(1);
        WebElement commentButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-comments/ytd-item-section-renderer/div[1]/ytd-comments-header-renderer/div[5]/ytd-comment-simplebox-renderer/div[3]/ytd-comment-dialog-renderer/ytd-commentbox/div[2]/div/div[4]/div[5]/ytd-button-renderer[2]/yt-button-shape/button/yt-touch-feedback-shape/div")));
        commentButton.click();
        Wait(2);
    }

    /**
     * Verifies the sorting functionality in the comments section of a web page by interacting
     * with the sort dropdown menu and selecting a sort option.
     *
     * The method performs the following actions:
     * - Waits for the sort menu to become visible.
     * - Clicks on the sort menu to open the dropdown.
     * - Introduces a short pause for synchronization.
     * - Waits for the sort options dropdown to become visible.
     * - Selects a specific sort option from the dropdown.
     * - Introduces another short pause to allow for the sorting operation to complete.
     *
     * This method employs WebDriver's `wait` functionality to ensure that all elements
     * are visible before interaction, ensuring reliable execution in dynamic web environments.
     *
     * Test priority for this method is set to 3.
     */
    @Test(description = "Checks Sorting Functionality", priority = 3)
    public void checkSorting() {
        WebElement sortMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-comments/ytd-item-section-renderer/div[1]/ytd-comments-header-renderer/div[1]/div[2]/span/yt-sort-filter-sub-menu-renderer/yt-dropdown-menu/tp-yt-paper-menu-button/div/tp-yt-paper-button/yt-icon/span/div")));
        sortMenu.click();
        Wait(1);
        WebElement sortOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));
        sortOption.click();
        Wait(2);
    }

    /**
     * Simulates the action of toggling the "like" and "dislike" buttons on a video.
     *
     * The method performs the following steps:
     * - Waits for the "dislike" button to become visible on the web page.
     * - Clicks the "dislike" button and pauses the execution for a specified duration.
     * - Waits for the "like" button to become visible on the web page.
     * - Clicks the "like" button and pauses the execution for a specified duration.
     *
     * This method utilizes WebDriver's `wait` functionality to ensure that the buttons
     * are visible before interacting with them, ensuring reliable execution in dynamic
     * web contexts.
     *
     * Priority level of this test is set to 4.
     */
    @Test(description = "Checking like and dislike on the Video", priority = 4)
    public void checkLikeDislike() {
        WebElement dislikeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[2]/div[2]/div/div/ytd-menu-renderer/div[1]/segmented-like-dislike-button-view-model/yt-smartimation/div/div/dislike-button-view-model/toggle-button-view-model/button-view-model/button/yt-touch-feedback-shape/div")));
        dislikeBtn.click();
        Wait(2);
        WebElement likeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[2]/div[2]/div/div/ytd-menu-renderer/div[1]/segmented-like-dislike-button-view-model/yt-smartimation/div/div/like-button-view-model/toggle-button-view-model/button-view-model/button/yt-touch-feedback-shape/div")));
        likeBtn.click();
        Wait(2);

    }


    /**
     * Simulates the action of saving a video to a watch list by interacting with UI elements on the webpage.
     * The method waits for the 'Save' button to become visible, clicks it,
     * and then toggles the watch list checkbox twice with pauses in between.
     *
     * This method utilizes WebDriver's 'wait' functionality to ensure elements
     * are visible before interaction.
     *
     * The behavior of the method includes:
     * - Clicking the save button to open the save popup.
     * - Selecting and deselecting the watch list checkbox.
     * - Adding delays between interactions for proper synchronization.
     *
     * Priority level of this test is set to 5.
     */
    @Test(description = "Save to a watch list", priority = 5)
    public void saveToWatchList() {
        WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[2]/div[2]/div/div/ytd-menu-renderer/div[2]/yt-button-view-model[3]/button-view-model/button/yt-touch-feedback-shape/div")));
        saveBtn.click();
        Wait(1);
        WebElement watchListBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        watchListBtn.click();
        Wait(2);
        watchListBtn.click();
        Wait(2);
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

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
