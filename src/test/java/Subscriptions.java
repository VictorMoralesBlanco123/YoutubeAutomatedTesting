import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class Subscriptions {
    WebDriver driver;
    WebDriverWait wait;

    String correctEmail = "fgcuswtspring2025@gmail.com";
    String correctPassword = "TestP@ssword123";
    String channelName;

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

    @Test(priority = 1, dependsOnMethods = "subscribeToRickAstley")
    public void confirmSubscriptionAdded() {
        WebElement addedNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='text' and contains(text(), 'Subscription added')]")
        ));
        Assert.assertTrue(addedNotification.isDisplayed(), "'Subscription added' notification not visible.");
    }

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

    @Test(priority = 4, dependsOnMethods = "unsubscribeFromRickAstley")
    public void confirmSubscriptionRemoved() {
        WebElement removedNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='text' and contains(text(), 'Subscription removed')]")
        ));
        Assert.assertTrue(removedNotification.isDisplayed(), "'Unsubscribed' notification not visible.");
    }

    public void Wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
