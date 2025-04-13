import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.*;

public class Search {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void Homepage() {
        driver.get("https://www.youtube.com/");
    }

    public void performSearch(String search) {
    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
    searchBox.clear();
    searchBox.sendKeys(search);
    searchBox.sendKeys(Keys.ENTER);
    }

    @Test (description = "Searches with an empty query", priority = 1)
    public void testSearchWithEmptyInput() {
        performSearch("");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#logo")));
        assertTrue(driver.getCurrentUrl().contains("youtube.com"), "User did not remain on the homepage with an empty search");
    }

    @Test (description = "Tests that the results of the search are relevant", priority = 2)
    public void testSearchRelevance() {

        performSearch("Software Testing");

        // Wait for the outer container to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("contents")
        ));

        // Wait for titles and then get all video titles in a list
        List<WebElement> videoTitles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.id("video-title")
        ));

        // Check if list is empty
        assertFalse(videoTitles.isEmpty(), "No video titles found.");

        boolean found = false;

        // Loop through all titles in list and check for relevant results
        for (WebElement titleElement : videoTitles) {
            String text = titleElement.getText().toLowerCase();
            if (text.contains("software") || text.contains("testing")) {
                found = true;
                break;
            }
        }

        // Assert that relevant titles were found
        assertTrue(found, "No video title contained 'software' or 'testing'.");
    }


    @Test (description = "Tests that the search query is retained after a search", priority = 3)
    public void testSearchRetainsInput() throws InterruptedException {

        String searchTerm = "FGCU";

        performSearch(searchTerm);
        Thread.sleep(2000);
        WebElement searchBoxAfter = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("search_query")
        ));

        String valueAfterSearch = searchBoxAfter.getAttribute("value");

        Assert.assertEquals(valueAfterSearch, searchTerm, "Search term doesn't match.");

        searchBoxAfter.click();
        searchBoxAfter.sendKeys(" campus tour");

    }

    @Test (description = "Tests that all dropdown suggestions are relevant and hover-highlight feature is working", priority = 4)
    public void testSearchDropdownSuggestions() throws InterruptedException {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("search_query")
        ));

        searchBox.sendKeys("FGCU");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='i0' and @role='listbox']")
        ));

        List<WebElement> suggestions = driver.findElements(
                By.xpath("//div[@role='option' " +
                        "and contains(@class, 'ytSuggestionComponent')]")
        );

        Actions actions = new Actions(driver);

        for (WebElement suggestion : suggestions) {

            String label = suggestion.getAttribute("aria-label");

            assertTrue(label.toLowerCase().contains("fgcu"), "Suggestion does not contain 'fgcu': " + label);

            System.out.println("Suggestion: " + label);

            actions.moveToElement(suggestion).perform();

            Thread.sleep(100);
        }

    }


    // Previous searches made with Enter button
    @Test (description = "Tests clickable search and clear buttons", priority = 5)
    public void testSearchButtons() throws InterruptedException {

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("search_query")
        ));

        searchBox.sendKeys("Computer Science");

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Search']")
        ));

        searchButton.click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("search_query")
        ));

        WebElement clearButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@aria-label='Clear search query']")
        ));

        clearButton.click();
    }

    /**
     * Cleans up the test environment after all tests have been executed.
     *
     * This method closes and quits the WebDriver instance that was initialized
     * for running the tests, ensuring that browser resources are properly released.
     * It is executed after all test methods in the test class have completed.
     */
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
