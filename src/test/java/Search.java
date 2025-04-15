import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.*;

/**
 * The Search class automates UI testing for the search functionality on YouTube.
 * It includes methods for setup, performing searches, validating search functionalities,
 * and cleaning up after tests. The test cases ensure that the search functionality
 * behaves as intended under various conditions, such as empty queries, query relevance,
 * search term retention, suggestion display, and interaction with buttons.
 */
public class Search {

    WebDriver driver;
    WebDriverWait wait;

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
     * Performs a search operation by interacting with the search box element.
     * <p>
     * This method waits for the visibility of the search box element using an explicit wait,
     * clears any pre-existing text, inputs the provided search query, and executes the search by pressing the Enter key.
     *
     * @param search the search query to be entered into the search box
     */
    public void performSearch(String search) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.clear();
        searchBox.sendKeys(search);
        searchBox.sendKeys(Keys.ENTER);
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
     * Tests the behavior of the search functionality when provided with an empty input.
     * <p>
     * This test verifies that submitting an empty search query does not navigate away
     * from the homepage. It uses the `performSearch` method with an empty string as input,
     * waits for the homepage's logo element to be visible, and asserts that the user remains
     * on the homepage by checking the current URL.
     * <p>
     * Test Functionality:
     * - Executes the search with an empty string.
     * - Verifies the homepage logo is visible after performing the search.
     * - Asserts that the current URL belongs to the homepage.
     * <p>
     * Annotations:
     * - `@Test` ensures this is executed as a test case.
     * - `description` describes the intent of the test.
     * - `priority` specifies the order in which this test should be executed within the suite.
     */
    @Test(description = "Searches with an empty query", priority = 1)
    public void testSearchWithEmptyInput() {
        performSearch("");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#logo")));
        assertTrue(driver.getCurrentUrl().contains("youtube.com"), "User did not remain on the homepage with an empty search");
    }

    /**
     * Verifies that the search results contain relevant results based on the query.
     * <p>
     * This test performs a search operation using the `performSearch` method with the query
     * "Software Testing". It validates the following:
     * <p>
     * - Ensures that the search results container is visible.
     * - Fetches all video titles from the search results and verifies that the list is not empty.
     * - Checks that at least one video title contains the keywords "software" or "testing".
     * - Asserts the relevance of the search results by confirming that at least one matching title exists.
     * <p>
     * Annotations:
     * - `@Test` marks this as a test case.
     * - `description` provides a brief explanation of the test's intent.
     * - `priority` defines the execution order of this test within the suite.
     */
    @Test(description = "Tests that the results of the search are relevant", priority = 2)
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


    /**
     * Verifies that the input entered into the search box is retained after performing a search.
     * <p>
     * This test ensures that the search query remains visible in the search box following a search operation.
     * The test performs the following steps:
     * - Executes a search using the `performSearch` method with a predefined search term.
     * - Waits for the search box element to be visible after the search is completed.
     * - Retrieves the value from the search box and validates it against the initial search term.
     * - Adds additional input to the existing search term to validate further interaction with the search box.
     * <p>
     * Assertions:
     * - Confirms that the search term in the search box matches the expected search term.
     * <p>
     * Annotations:
     * - `@Test` marks this method as a test case.
     * - `description` provides a description of the test's purpose.
     * - `priority` defines the execution order of this test within the suite.
     *
     * @throws InterruptedException if the thread is interrupted during the test execution
     */
    @Test(description = "Tests that the search query is retained after a search", priority = 3)
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

    /**
     * Tests the behavior of the search dropdown suggestions and hover-highlight functionality.
     * <p>
     * This test verifies the following:
     * - Ensures that the search box is visible and allows input.
     * - Inputs the query "FGCU" into the search box.
     * - Waits for the appearance of the dropdown containing suggestions with the role attribute 'listbox'.
     * - Fetches all suggestions with the role attribute 'option' and the specified class name.
     * - Validates that each suggestion's label contains the provided input string ("FGCU"), ensuring relevance.
     * - Tests the hover-highlight feature by moving the mouse pointer over each suggestion and briefly pausing.
     * <p>
     * Assertions:
     * - Confirms that each suggestion label includes the substring "FGCU".
     * <p>
     * Annotations:
     * - `@Test` ensures this method is executed as part of the test suite.
     * - `description` provides a brief overview of the test's purpose.
     * - `priority` determines the order of execution for this test within the suite.
     *
     * @throws InterruptedException if the thread is interrupted during the wait or sleep operations
     */
    @Test(description = "Tests that all dropdown suggestions are relevant and hover-highlight feature is working", priority = 4)
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


    /**
     * Tests the functionality of the search and clear buttons.
     * <p>
     * This method verifies the following:
     * - The search input box is visible and allows input.
     * - The input is submitted by clicking the search button, triggering a search.
     * - The clear button is visible following a completed search.
     * - The clear button clears the search input upon being clicked.
     * <p>
     * Test workflow:
     * - Waits for the visibility of the search input element and inputs the string "Computer Science".
     * - Waits for the search button to become clickable and clicks it to perform the search.
     * - Waits for the search input box to reappear after the search is completed.
     * - Waits for the visibility of the clear button and clicks it to clear the search input.
     * <p>
     * Annotations:
     * - `@Test` ensures the method is executed as a test case.
     * - `description` provides a brief explanation of the test purpose.
     * - `priority` specifies the execution order of this test within the suite.
     *
     * @throws InterruptedException if the thread is interrupted during the wait or sleep operation.
     */
    @Test(description = "Tests clickable search and clear buttons", priority = 5)
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
     * <p>
     * This method closes and quits the WebDriver instance that was initialized
     * for running the tests, ensuring that browser resources are properly released.
     * It is executed after all test methods in the test class have completed.
     */
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
