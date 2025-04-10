import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Settings {
    WebDriver driver;
    WebDriverWait wait;

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
