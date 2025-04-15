import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

//https://www.vidsplay.com/flying-over-the-ocean/ for the video

/**
 * Represents the configuration and testing operations for automating YouTube functionality using Selenium WebDriver.
 *
 * The `Settings` class encapsulates methods to set up a testing environment, interact with various YouTube Studio settings,
 * and perform tests such as navigating and validating side links, toggling notification settings, uploading a video, and editing a video's title.
 * This class uses Selenium WebDriver to automate browser actions and provides a framework for TestNG-based tests.
 */
public class Settings {
    WebDriver driver;
    WebDriverWait wait;
    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";
    JavascriptExecutor js;

    /**
     * Sets up the testing environment for YouTube functionality validation.
     *
     * This method initializes the WebDriver instance with a ChromeDriver, maximizes the browser window,
     * and navigates to the YouTube homepage. It automates the login process using valid credentials
     * provided in the class fields and verifies the successful login by checking the presence of the
     * avatar button. After login, it navigates to the YouTube account page.
     *
     * Preconditions:
     * - Valid `correctEmail` and `correctPassword` values are provided in the class fields.
     * - ChromeDriver is correctly set up and accessible.
     *
     * Annotations:
     * - Annotated with TestNG's @BeforeTest annotation to ensure this method is executed before any test methods in the suite.
     */
    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
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
        driver.get("https://www.youtube.com/account");
    }

    /**
     * Tests the functionality of side links in the settings sidebar of YouTube Studio.
     *
     * This method performs the following steps:
     * - Locates the settings sidebar menu using its XPath.
     * - Finds all links (anchor tags) within the sidebar.
     * - Iterates through each link, prints its URL to the console, and clicks on it.
     * - Waits for a specified duration after each click to allow the page or section to load.
     * - Returns to the initial state by clicking the first link after the iteration.
     *
     * Preconditions:
     * - A valid WebDriver instance is initialized.
     * - User is logged into YouTube Studio.
     *
     * Annotations:
     * - Annotated with TestNG's @Test annotation, including a description and priority.
     */
    @Test(description = "Tests side links are working properly", priority = 1)
    public void testSideLinks() {
        WebElement sideMenu = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-browse/ytd-settings-sidebar-renderer"));
        List<WebElement> sideLinks = sideMenu.findElements(By.tagName("a"));
        for (WebElement link : sideLinks) {
            System.out.println("Link: " + link.getAttribute("href"));
            link.click();
            Wait(1);
        }
        sideLinks.get(0).click();
    }

    /**
     * Tests the functionality of toggling all notification checkboxes on the YouTube notifications settings page.
     *
     * This method performs the following actions:
     * - Navigates to the YouTube notifications settings page.
     * - Waits for the page to load.
     * - Identifies and iterates through the notification toggle elements.
     * - For each toggle:
     *   - Scrolls the element into view.
     *   - Toggles the checkbox off.
     *   - Toggles the checkbox back on.
     *
     * Test Priority:
     * - Priority level is set to 2.
     *
     * Annotations:
     * - Annotated with TestNG's @Test annotation, including a description and priority.
     */
    @Test(description = "Tests all checkboxes in notifications", priority = 2)
    public void testNotifications() {
        driver.get("https://www.youtube.com/account_notifications");
        Wait(3);
        List<WebElement> toggles = driver.findElements(By.id("toggle"));
        for (int i = 0; i < 8; i++) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", toggles.get(i));
            Wait(1);
            toggles.get(i).click();
            Wait(1);
            toggles.get(i).click();
        }
    }

    /**
     * Tests the functionality of uploading a video to YouTube Studio.
     *
     * This method performs the following steps:
     * - Navigates to the YouTube Studio channel page.
     * - Clicks the "Create" button to open the upload options menu.
     * - Selects the "Upload videos" option from the menu.
     * - Chooses a video file to upload by sending the file path to the file picker input element.
     *
     * Preconditions:
     * - A valid WebDriver instance is initialized.
     * - The user is logged into YouTube Studio.
     * - The specified video file is available on the local file system at the provided path.
     *
     * Test Priority:
     * - Priority level is set to 3.
     *
     * Annotations:
     * - Annotated with TestNG's @Test annotation, including a description and priority.
     */
    @Test(description = "Upload video", priority = 3)
    public void testUploadVideo() {
        driver.get("https://studio.youtube.com/channel/UCa7t0uOFey9_7AyT5buAHlg");

        WebElement create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/ytcp-header/header/div/div/ytcp-button/ytcp-button-shape/button")));
        create.click();
        Wait(1);

        WebElement uploadBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytcp-text-menu/tp-yt-paper-dialog/tp-yt-paper-listbox/tp-yt-paper-item[1]")));
        uploadBtn.click();
        Wait(1);

        WebElement selectBtn = driver.findElement(By.xpath("/html/body/ytcp-uploads-dialog/tp-yt-paper-dialog/div/ytcp-uploads-file-picker/div/input"));
        selectBtn.sendKeys("C:\\Users\\Shadow\\Downloads\\big-island-shoreline-out-over-ocean.mp4");
        Wait(3);
    }

    /**
     * Tests the functionality of editing the title of a video in the "Content" tab within YouTube Studio.
     *
     * The method performs the following steps:
     * - Navigates to the YouTube Studio "Content" page.
     * - Selects the first video listed using its associated checkbox.
     * - Opens the bulk edit menu and selects the "Edit title" option.
     * - Inputs the new title into the provided text box.
     * - Confirms the change by interacting with the confirmation UI element.
     * - Updates the video title by completing the action in the confirmation dialog.
     *
     * Preconditions:
     * - A valid WebDriver instance is initialized.
     * - User is logged into YouTube Studio.
     * - At least one video is available in the "Content" section.
     *
     * Test Priority:
     * - Priority level is set to 4.
     *
     * Annotations:
     * - Annotated with TestNG's @Test annotation, including a description and priority.
     */
    @Test(description = "Edit video title", priority = 4)
    public void testEditVideoTitle() {
        driver.get("https://studio.youtube.com/channel/UCa7t0uOFey9_7AyT5buAHlg");

        WebElement contentBtn = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/ytcp-navigation-drawer/nav/ytcp-animatable[2]/ul/li[2]/ytcp-ve/a/tp-yt-paper-icon-item"));
        contentBtn.click();
        Wait(1);

        WebElement checkBox = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/ytcp-video-row[1]/div/div[1]/ytcp-checkbox-lit/div/div/div/div"));
        checkBox.click();
        Wait(1);

        WebElement editBtn = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/div[1]/div/ytcp-video-bulk-actions/ytcp-bulk-actions/div[1]/span/ytcp-select/ytcp-text-dropdown-trigger/ytcp-dropdown-trigger/div/div[3]/tp-yt-iron-icon"));
        editBtn.click();
        Wait(1);

        WebElement title = driver.findElement(By.xpath("/html/body/ytcp-text-menu/tp-yt-paper-dialog/tp-yt-paper-listbox/tp-yt-paper-item[1]"));
        title.click();
        Wait(1);


        WebElement textBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/div[1]/div/ytcp-video-bulk-actions/ytcp-bulk-actions/div[2]/ytcp-bulk-actions-collapsible-editor/ytcp-bulk-actions-editor-text/ytcp-form-textarea/div/textarea")));
        textBox.sendKeys("testing");

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/div[1]/div/ytcp-video-bulk-actions/ytcp-bulk-actions/div[1]/div[3]/ytcp-button[2]/ytcp-button-shape/button")));
        confirm.click();
        Wait(1);

        WebElement checkBox2 = driver.findElement(By.id("confirm-checkbox"));
        checkBox2.click();
        Wait(1);

        WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/ytcp-confirmation-dialog/ytcp-dialog/tp-yt-paper-dialog/div[3]/div[2]/ytcp-button[2]/ytcp-button-shape/button")));
        updateBtn.click();
        Wait(10);
    }

    /**
     * Tests the functionality of downloading a video from the "Content" tab in the YouTube Studio interface.
     *
     * The method navigates to the "Content" page, selects a video using a checkbox, opens the "More actions" menu,
     * and initiates the download process for the selected video. Various waits are included to handle potential delays
     * in the user interface updates.
     *
     * Preconditions:
     * - A valid WebDriver instance.
     * - The user is logged into their YouTube Studio account.
     * - The channel contains at least one video available in the "Content" tab.
     *
     * Test Priority:
     * - Priority level is set to 5.
     *
     * Annotations:
     * - Annotated with TestNG's @Test annotation and includes a description.
     */
    @Test(description = "Download a video from content tab", priority = 5)
    public void testDownloadVideo() {
        driver.get("https://studio.youtube.com/channel/UCa7t0uOFey9_7AyT5buAHlg");

        WebElement contentBtn = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/ytcp-navigation-drawer/nav/ytcp-animatable[2]/ul/li[2]/ytcp-ve/a/tp-yt-paper-icon-item"));
        contentBtn.click();
        Wait(1);

        WebElement checkBox = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/ytcp-video-row[1]/div/div[1]/ytcp-checkbox-lit/div/div/div/div"));
        checkBox.click();
        Wait(1);

        WebElement moreActions = driver.findElement(By.xpath("/html/body/ytcp-app/ytcp-entity-page/div/div/main/div/ytcp-animatable[4]/ytcp-content-section/ytcp-video-section/ytcp-video-section-content/div/div[1]/div/ytcp-video-bulk-actions/ytcp-bulk-actions/div[1]/span/div/ytcp-select/ytcp-text-dropdown-trigger/ytcp-dropdown-trigger"));
        moreActions.click();
        Wait(1);

        WebElement download = driver.findElement(By.xpath("/html/body/ytcp-text-menu/tp-yt-paper-dialog/tp-yt-paper-listbox/tp-yt-paper-item[1]"));
        download.click();
        Wait(3);
    }

    /**
     * Cleans up resources and closes the browser session after the execution of all tests in the test suite.
     *
     * This method is annotated with TestNG's @AfterTest annotation, ensuring it is executed after
     * all the test methods defined in the suite have been run. It terminates the WebDriver instance
     * to release resources and close the browser window.
     */
    @AfterClass
    public void teardown() {
        driver.quit();
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
}
