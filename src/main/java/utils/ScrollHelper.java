package utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ScrollHelper {

    static WebDriverWait wait;
    public static void scrollToElement(WebDriver driver, Object locator) {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null.");
        }

        WebElement element;

        try {
            // Determine the element based on the locator type
            if (locator instanceof By) {
                element = driver.findElement((By) locator);
            } else if (locator instanceof WebElement) {
                element = (WebElement) locator;
            } else {
                throw new IllegalArgumentException("Unsupported locator type: " + locator.getClass().getSimpleName());
            }

            // Scroll the element into view with smooth scrolling
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'start'});", element);

            // Wait for the element to become clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));

        } catch (NoSuchElementException e) {
            Logger.info("Element not found: [%s]", e.getMessage());
            throw e;
        } catch (TimeoutException e) {
            Logger.info("Element not clickable within timeout: [%s]", e.getMessage());
            throw e;
        }
    }
    public static void scrollToElement(WebDriver driver, By elementLocator, int scrollAmount, int maxScrollCount) {
        // Initialize the scroll count
        int scrollCount = 0;

        // Wait for the target element to be present
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));

        // Use JavascriptExecutor for scrolling
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // If the element is not initially displayed, keep scrolling until it's in view
        while (!targetElement.isDisplayed() && scrollCount < maxScrollCount) {
            // Scroll by the specified amount
            js.executeScript(String.format("window.scrollBy(0, %d)",scrollAmount));
            scrollCount++;

            // Optionally, wait for a short period before next scroll to ensure the page has time to adjust
            try {
                Thread.sleep(500); // Add a brief wait to prevent rapid scrolling issues
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Re-fetch the element in case the DOM updates after scrolling
            targetElement = driver.findElement(elementLocator);
        }

        if (scrollCount >= maxScrollCount) {
            Logger.info("Reached maximum scroll attempts without finding the element");
        }
    }

    public static void scrollToElementInContainer(WebDriver driver, By elementLocator, int scrollAmount, int maxScrollCount) {
        int scrollCount=0;
        // Find the target element
        WebElement targetElement = driver.findElement(elementLocator);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Use querySelector to scroll the container to the target element
        String script = String.format("document.querySelector('.hmenu.hmenu-visible').scrollBy(0, %d)", scrollAmount) ;
        while (!targetElement.isDisplayed() && scrollCount<maxScrollCount) {
            js.executeScript(script);
            scrollCount++;
            // Optionally, wait for a short period before next scroll to ensure the page has time to adjust
            try {
                Thread.sleep(500); // Add a brief wait to prevent rapid scrolling issues
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Re-fetch the element in case the DOM updates after scrolling
            targetElement = driver.findElement(elementLocator);
        }
        if (scrollCount >= maxScrollCount) {
            Logger.info("Reached maximum scroll attempts without finding the element");
        }
    }

    public static void scrollByPixel(WebDriver driver, int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    public void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    public void scrollUntilVisible(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (true) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0, 250);");
            }
        }
    }
}
