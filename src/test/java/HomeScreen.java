import org.jspecify.annotations.Nullable;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v132.page.model.Screenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "https://www.youtube.com/";

    @BeforeTest
    public void Setup() {
        driver = new ChromeDriver();
        driver.get("https://www.youtube.com/");
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query"))); // Wait for search bar
    }

    @Test(description = "Opens the website and checks all the links are working")
    public void OpenWebsite() throws IOException {
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, baseUrl);
        String linkText;

        // Create a list of WebElements that gets all links
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total links on the page: " + links.size());

        // Iterate through all links, take screenshots, and navigate back
        for (int i = 0; i < links.size(); i++) {

            if (links.get(i).getAttribute("href") != null) {
                linkText = links.get(i).getText();
                links.get(i).click();
                Screenshot("C:\\Users\\Shadow\\Downloads\\" + linkText + ".png");
                driver.navigate().back();
                links = driver.findElements(By.tagName("a"));
                wait.until(ExpectedConditions.visibilityOf(links.get(++i)));
            }
        }
    }

    public void Screenshot(String path) throws IOException {
        // Take a screenshot and save it to a file

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(path);
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Screenshot taken at: " + path);
    }

    @AfterTest
    public void TearDown() {
        driver.quit();
    }
}
