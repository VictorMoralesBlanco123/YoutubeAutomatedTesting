import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Comments {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.get("https://www.youtube.com/watch?v=Q3boOPiTFHY");
    }

    //Needs to login
    @Test(description = "Checks comments work properly")
    public void addComment() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-comments/ytd-item-section-renderer/div[1]/ytd-comments-header-renderer/div[5]/ytd-comment-simplebox-renderer/div[3]/ytd-comment-dialog-renderer/ytd-commentbox/div[2]/div/div[2]/tp-yt-paper-input-container/div[2]/div/div[1]/ytd-emoji-input/yt-user-mention-autosuggest-input/yt-formatted-string/div")));
        WebElement commentBox = driver.findElement(By.xpath("/html/body/ytd-app/div[1]/ytd-page-manager/ytd-watch-flexy/div[5]/div[1]/div/div[2]/ytd-comments/ytd-item-section-renderer/div[1]/ytd-comments-header-renderer/div[5]/ytd-comment-simplebox-renderer/div[3]/ytd-comment-dialog-renderer/ytd-commentbox/div[2]/div/div[2]/tp-yt-paper-input-container/div[2]/div/div[1]/ytd-emoji-input/yt-user-mention-autosuggest-input/yt-formatted-string/div"));
        commentBox.sendKeys("Automation Working");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
