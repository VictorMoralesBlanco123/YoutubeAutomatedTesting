import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * The Subscriptions class is a test suite to manage YouTube subscription-related actions.
 * It uses Selenium WebDriver to automate browser interactions.
 *
 * The test suite includes methods to:
 * - Set up the test environment and log in to YouTube.
 * - Subscribe to a specific channel.
 * - Confirm that the subscription has been added successfully.
 * - Verify that the channel is listed in the subscriptions tab.
 * - Unsubscribe from a previously subscribed channel.
 * - Confirm that the unsubscription was successful.
 *
 * The class is structured with test methods annotated using TestNG. Dependencies and priorities
 * are specified between tests to ensure a logical order.
 *
 * It uses explicit waits to handle UI element visibility and interactivity.
 */
public class Subscriptions {
    WebDriver driver;
    WebDriverWait wait;

    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";
    String channelName;

    /**
     * Sets up the WebDriver, configures browser settings, and performs login to YouTube.
     *
     * This method initializes the Chrome web driver, sets a default wait time, and maximizes
     * the browser window. It navigates to the YouTube homepage, automates the sign-in process
     * by entering credentials, and waits until the user is successfully logged in.
     *
     * Preconditions:
     * - The correct email and password must be provided for the login fields.
     * - The corresponding WebDriver executable must be properly set in the system path.
     *
     * Postconditions:
     * - The WebDriver instance is initialized.
     * - The user is successfully logged in to the YouTube account.
     * - The method will wait until the profile avatar button is visible before proceeding.
     *
     * Note:
     * - Replace "correctEmail" and "correctPassword" with the actual login credentials.
     * - This method is annotated with @BeforeClass, and therefore, it is executed
     *   once before any tests in the class.
     * - The "Wait" method call at the end appears to be a custom implementation and
     *   should ensure an appropriate delay before subsequent actions.
     */
    @BeforeClass
    public void setupAndLogin() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.youtube.com");

        // Login
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        driver.findElement(By.id("identifierId")).sendKeys(correctEmail);
        driver.findElement(By.id("identifierNext")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys(correctPassword);
        driver.findElement(By.id("passwordNext")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("avatar-btn")));
        Wait(3);
    }

    /**
     * Automates the process of subscribing to Rick Astley's YouTube channel.
     *
     * This method navigates to Rick Astley's "Never Gonna Give You Up" video URL on YouTube,
     * detects and logs the channel name, and then clicks the subscribe button
     * if it is visible and clickable.
     *
     * Preconditions:
     * - A valid WebDriver instance must be properly configured and initialized.
     * - The WebDriver instance should be capable of handling explicit waits.
     *
     * Details:
     * - The WebDriver navigates to the specified video URL.
     * - Waits for the channel name element to be visible, retrieves the channel name,
     *   and logs it to the console.
     * - Waits for the subscribe button to become clickable and clicks it.
     *
     * Note:
     * - This method includes static wait statements for demonstration purposes.
     *   Consider removing or replacing these with dynamic waits for optimal performance.
     * - Ensure that popups, if any, such as consent forms or authentication prompts,
     *   do not block the automation flow.
     *
     * Potential Exceptions:
     * - NoSuchElementException: Thrown if the expected elements are not found on the page.
     * - TimeoutException: Thrown if web elements do not meet wait conditions in a timely manner.
     */
    @Test(priority = 1)
    public void subscribeToRickAstley() {
        driver.get("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        Wait(3);

        WebElement channel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ytd-channel-name//a")));
        channelName = channel.getText();
        System.out.println("Detected channel name: " + channelName);

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"subscribe-button-shape\"]/button")));
        button.click();
        Wait(2);
    }

    /**
     * Validates that a subscription has been successfully added by checking the visibility
     * of a notification element on the webpage.
     *
     * This method depends on the execution of the `subscribeToRickAstley` method.
     * It waits for a specific notification element to become visible with the message
     * indicating that the subscription has been added. Once visible, it asserts that
     * the notification is displayed to confirm the action was successful.
     *
     * Preconditions:
     * - The `subscribeToRickAstley` method must execute successfully before this method.
     * - The WebDriver must be initialized and capable of interacting with the web page.
     *
     * Assertions:
     * - The method asserts that the 'Subscription added' notification is displayed.
     *
     * Test priority is set to 1, indicating the order of execution in the test suite.
     */
    @Test(priority = 1, dependsOnMethods = "subscribeToRickAstley")
    public void confirmSubscriptionAdded() {
        WebElement addedNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='text' and contains(text(), 'Subscription added')]")
        ));
        Assert.assertTrue(addedNotification.isDisplayed(), "'Subscription added' notification not visible.");
    }

    /**
     * Verifies that a subscribed YouTube channel is displayed in the "Manage" tab under subscriptions.
     *
     * This test navigates to the YouTube subscriptions page, simulates a subscription action,
     * and confirms that the subscribed channel appears in the subscription list.
     * It uses explicit waits to ensure elements are interactable or visible before performing actions.
     *
     * Preconditions:
     * - The user must be logged into their YouTube account.
     * - The desired channel to verify subscription must be specified using the variable `channelName`.
     *
     * Steps:
     * 1. Navigate to the YouTube subscriptions page.
     * 2. Locate and click on the "Subscribe" button using its UI element.
     * 3. Wait for the subscribed channel to appear in the subscriptions list.
     * 4. Assert that the channel is successfully displayed in the subscriptions list.
     *
     * Expected Outcome:
     * - The subscribed channel is found and shown in the "Manage" tab under subscriptions.
     *
     * Test Priority:
     * - Priority 2
     *
     * Assertions:
     * - Validates whether the `channelName` is visible in the subscriptions list.
     *
     * Exceptions:
     * - Throws a TimeoutException if the specified elements are not interactable or visible within the wait period.
     * - Test fails and throws an AssertionError if the `channelName` is not found in the subscriptions list.
     */
    @Test(priority = 2)
    public void verifySubscriptionInManageTab() {
        driver.get("https://www.youtube.com/feed/subscriptions");
        Wait(3);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"subscribe-button\"]/ytd-button-renderer[1]/yt-button-shape[1]/a[1]")
        )).click();
        Wait(2);

        WebElement channelEntry = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//yt-formatted-string[contains(text(),'" + channelName + "')]")
        ));
        Assert.assertTrue(channelEntry.isDisplayed(), channelName + " was not found in subscriptions.");
    }

    /**
     * Automates the process of unsubscribing from Rick Astley's YouTube channel.
     *
     * This method simulates the following sequence of actions on the respective webpage:
     * 1. Waits for the "Subscribed" button to become clickable and clicks it.
     * 2. Waits for the "Unsubscribe" option from the dropdown menu to become clickable and clicks it.
     * 3. Waits for the confirmation button to become clickable and confirms the choice to unsubscribe.
     *
     * The method uses explicit waits to handle the asynchronous nature of the webpage and simulates
     * short delays where necessary using a Wait function.
     *
     * This test method is executed with a priority value of 3 within a test suite.
     *
     * Precondition:
     * - The user must already be subscribed to the channel to perform the unsubscribe process.
     *
     * Postcondition:
     * - The user will no longer be subscribed to the channel after the successful execution of this method.
     *
     * Throws:
     * - org.openqa.selenium.TimeoutException if any of the elements fail to load or become clickable within the defined timeout.
     */
    @Test(priority = 3)
    public void unsubscribeFromRickAstley() {
        WebElement subscribedButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"notification-preference-button\"]/ytd-subscription-notification-toggle-button-renderer-next/yt-button-shape/button")
        ));
        subscribedButton.click();
        WebElement unsubscribeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"items\"]/ytd-menu-service-item-renderer[4]/tp-yt-paper-item")
        ));
        unsubscribeButton.click();
        Wait(2);

        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"confirm-button\"]/yt-button-shape/button")
        ));
        confirmBtn.click();
        Wait(2);
    }

    /**
     * Validates that a subscription has been successfully removed by verifying the visibility
     * of a notification element on the webpage.
     *
     * This method depends on the execution of the `unsubscribeFromRickAstley` method. It waits
     * for a specific notification element to appear with the message indicating that the subscription
     * has been removed. Once the notification is detected, it asserts that the notification is displayed,
     * confirming the subscription removal.
     *
     * Preconditions:
     * - The `unsubscribeFromRickAstley` method must execute successfully before this method.
     * - The WebDriver should be initialized and functioning properly.
     *
     * Assertions:
     * - Confirms the visibility of a notification stating "Subscription removed."
     *
     * Exceptions:
     * - The test fails and throws an AssertionError if the notification is not displayed within the wait period.
     *
     * Test Priority:
     * - Priority 4, which defines its sequence relative to other test methods in the suite.
     */
    @Test(priority = 4, dependsOnMethods = "unsubscribeFromRickAstley")
    public void confirmSubscriptionRemoved() {
        WebElement removedNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='text' and contains(text(), 'Subscription removed')]")
        ));
        Assert.assertTrue(removedNotification.isDisplayed(), "'Unsubscribed' notification not visible.");
    }

    /**
     * Pauses the execution for the specified number of seconds.
     *
     * This method internally uses the {@code Thread.sleep()} function to introduce
     * a delay in the execution of the program. It handles the {@code InterruptedException}
     * by printing the stack trace.
     *
     * @param seconds the number of seconds to wait. The value is converted into milliseconds
     *                for the {@code Thread.sleep()} method.
     */
    public void Wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cleans up resources and properly closes the WebDriver instance after all tests
     * in the test class have been executed.
     *
     * This method is executed only once after all test methods have completed, ensuring
     * that the WebDriver session is terminated to free up system resources. It is
     * annotated with {@code @AfterClass}, indicating its post-test execution role.
     *
     * Postconditions:
     * - The WebDriver instance is closed and the browser is terminated.
     *
     * Note:
     * - This action ensures that no WebDriver or browser processes are left running
     *   after the test suite completes.
     * - Failure to properly execute this method might result in resource leaks.
     */
    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
