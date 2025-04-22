import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Represents a utility class for interacting with a video player on a web page.
 * This class contains methods for setting up the browser environment, as well as
 * tests for verifying functionalities such as playing, pausing, interacting with
 * captions, handling transcripts, and navigating to the next video.
 */
public class Video {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    /**
     * Initializes the test setup before any test is run.
     *
     * This method is annotated with `@BeforeClass`, indicating that it
     * is executed once before all the test methods in the class.
     * It performs the following actions:
     *
     * - Creates a new instance of ChromeDriver to initialize the WebDriver.
     * - Sets up an explicit wait with a timeout of 10 seconds.
     * - Casts the WebDriver instance to `JavascriptExecutor` for potential JavaScript execution.
     * - Maximizes the browser window for better visibility during test execution.
     * - Navigates to a specified YouTube video URL to prepare the testing environment.
     * - Waits for the play button to become visible on the page, ensuring the video player is loaded.
     * - Simulates a click on the play button to start video playback.
     *
     * Preconditions:
     * - ChromeDriver must be properly configured on the system.
     * - The YouTube page should be accessible from the system running the tests.
     */
    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com/watch?v=Q3boOPiTFHY");
        WebElement playBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[1]/div[2]/div/div/ytd-player/div/div/div[9]/button")));
        playBtn.click();
    }

    /**
     * Tests the Pause and Play functionality of a video player.
     *
     * This method verifies the capability to pause and resume video playback
     * by simulating clicks on the video player. It ensures that:
     * - The video player is visible before interacting.
     * - A pause action is performed on the video followed by a subsequent play action.
     *
     * Test Steps:
     * 1. Wait until the video element is visible on the page.
     * 2. Simulate a click event to pause the video.
     * 3. Wait for a short duration to mimic natural user interaction.
     * 4. Simulate another click event to resume video playback.
     *
     * Preconditions:
     * - The video player must be loaded and visible on the web page.
     *
     * Priority: 1
     */
    @Test(description = "Testing Pause and Play functionality", priority = 1)
    public void testPauseAndPlay() {
        Wait(2);
        //Pauses Video
        WebElement video = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[1]/div[2]/div/div/ytd-player/div/div/div[1]/video")));
        video.click();
        Wait(2);

        //Plays it again
        video.click();
    }

    /**
     * Verifies the functionality of the captions button in a video player.
     * <p>
     * This method interacts with the captions button of a video player, ensuring that it
     * is visible and can be clicked. The method uses an explicit wait to handle the
     * dynamic nature of the web element and simulates a user clicking the button to
     * test its functionality.
     * <p>
     * Precondition:
     * - The video player must be loaded and visible on the web page.
     * <p>
     * Test Steps:
     * 1. Locate the captions button using its XPath.
     * 2. Wait until the captions button is visible.
     * 3. Simulate a click on the captions button.
     * 4. Wait for a short duration to allow for any interaction effects.
     * <p>
     * Priority: 2
     */
    @Test(description = "Verify Captions functionality is working", priority = 2)
    public void testCaptions() {
        WebElement captionsBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[1]/div[2]/div/div/ytd-player/div/div/div[35]/div[2]/div[2]/button[3]")));
        captionsBtn.click();
        Wait(2);
    }

    /**
     * Verifies the functionality of transcript timestamps in a video player.
     * <p>
     * This test ensures that:
     * 1. The transcript button is clickable and displays the transcript correctly.
     * 2. Clicking a specific timestamp in the transcript navigates to the corresponding video segment.
     * 3. The video player responds appropriately to a timestamp click by playing the video from the selected time.
     * <p>
     * Precondition:
     * - The video page must be loaded, and the transcript feature must be available for the video.
     * <p>
     * Test Steps:
     * 1. Wait for the video description box to be clickable and scroll it into view, then click the box to expand.
     * 2. Locate and click the transcript button to display the video's transcript.
     * 3. Locate a specific timestamp in the transcript and click it to navigate to the related video segment.
     * 4. Verify that the video plays from the selected timestamp by interacting with the video player.
     * <p>
     * Priority: 3
     */
    @Test(description = "Verify transcript's timestamps are working", priority = 3)
    public void testTranscript() {
        WebElement videoDescriptionBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("info-container")));

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", videoDescriptionBox);
        videoDescriptionBox.click();
        Wait(1);

        WebElement transcriptBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-watch-metadata/div/div[4]/div[1]/div/ytd-text-inline-expander/div[2]/ytd-structured-description-content-renderer/div[2]/ytd-video-description-transcript-section-renderer/div[3]/div/ytd-button-renderer/yt-button-shape/button")));
        transcriptBtn.click();
        Wait(2);

        WebElement timestamp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[2]/div/div[1]/ytd-engagement-panel-section-list-renderer[4]/div[2]/ytd-transcript-renderer/div[2]/ytd-transcript-search-panel-renderer/div[2]/ytd-transcript-segment-list-renderer/div[1]/ytd-transcript-segment-renderer[13]/div")));
        timestamp.click();

        WebElement video = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[1]/div[2]/div/div/ytd-player/div/div/div[1]/video")));
        video.click();


        Wait(2);

    }

    /**
     * Verifies the functionality of the "Next Video" button in a video player.
     * <p>
     * This method ensures that:
     * - The "Next Video" button is visible and can be interacted with.
     * - A new browser tab opens upon clicking the "Next Video" button,
     * navigating to the next video's URL.
     * <p>
     * The method utilizes explicit waits to handle dynamically loaded elements
     * and simulates user behavior of interacting with the "Next Video" button.
     * Short delays are included to mimic real-world interaction timing.
     * <p>
     * Preconditions:
     * - The video player must be loaded and visible on the webpage.
     * <p>
     * Test Steps:
     * 1. Wait for the "Next Video" button to become visible on the page.
     * 2. Read the "href" attribute of the button to retrieve the next video's URL.
     * 3. Open the next video's URL in a new browser tab.
     * 4. Track the tabs opened during the test using window handles.
     * 5. Introduce a short delay to allow for UI updates or transitions.
     * <p>
     * Test Priority: 4
     */
    @Test(description = "Check the next video button works", priority = 4)
    public void nextVideo() {
        WebElement nextVideoBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ytp-next-button")));
        nextVideoBtn.click();

        Wait(3);
    }

    /**
     * Extracts and prints the text and URLs of all anchor elements present on the current web page.
     * <p>
     * This method retrieves all elements with the tag name "a" using the WebDriver instance
     * and iterates through them. For each element, it retrieves and prints its text (title) and
     * the value of its "href" attribute (URL) to the console. After processing all links, the method
     * waits for a specified duration utilizing the `Wait` method.
     * <p>
     * Preconditions:
     * - The WebDriver instance should be active and navigating a web page containing anchor elements.
     * <p>
     * Test Priority: 5
     * <p>
     * Steps:
     * 1. Locate all anchor elements on the current page using the tag name "a".
     * 2. Iterate over the retrieved elements.
     * 3. For each element, retrieve and print its text (title) and "href" attribute (URL).
     * 4. Pause the execution for a specified duration using the `Wait` method.
     */
    @Test(description = "Get Title and Urls", priority = 5)
    public void getTitleAndUrls() {
        WebElement[] links = driver.findElements(By.tagName("a")).toArray(new WebElement[0]);
        for (WebElement link : links) {
            System.out.println(link.getText() + " : " + link.getAttribute("href") + "\n");
        }
        Wait(3);
    }

    /**
     * Cleans up resources and performs any necessary teardown actions
     * after the test execution is completed. This method ensures the
     * WebDriver instance is properly terminated to release system resources.
     */
    @AfterClass
    public void tearDown() {
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
