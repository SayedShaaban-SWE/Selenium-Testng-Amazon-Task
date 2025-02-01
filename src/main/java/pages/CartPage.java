package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import utils.Logger;
import utils.ScrollHelper;

import java.time.Duration;
import java.util.List;

public class CartPage {
    // Locators
    private static final By MY_CART = By.id("nav-cart-count-container");
    private static final By SUBTOTAL_AMOUNT = By.id("sc-subtotal-amount-buybox");
    private static final By PROCEED_TO_BUY_BUTTON = By.cssSelector("input[value='Proceed to checkout']");
    private static final By DELETE_BUTTON = By.xpath("//input[@value='Delete']");

    WebDriver driver;
    WebDriverWait wait;
    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void openCart() throws InterruptedException {
        Thread.sleep(3000);
        ScrollHelper.scrollToTop(driver);
        wait.until(ExpectedConditions.elementToBeClickable(MY_CART));
        driver.findElement(MY_CART).click();
    }

    public double getCartTotal() {
        WebElement totalElement = driver.findElement(SUBTOTAL_AMOUNT);
        return Double.parseDouble(totalElement.getText().replace("EGP", "").replace(",", ""));
    }
    public void clickOnProceedToBuyBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(PROCEED_TO_BUY_BUTTON));
        driver.findElement(PROCEED_TO_BUY_BUTTON).click();
    }
    public void clearCart(){
        try {
            //open the cart
            openCart();

            int productNumber =1;
            while (true) {
                // Find all delete buttons
                List<WebElement> deleteButtons = driver.findElements(DELETE_BUTTON);

                if (deleteButtons.isEmpty()) {
                    Logger.info("No items in the cart to delete.");
                    break;
                }

                // Click on the first delete button
                WebElement deleteButton = deleteButtons.get(0);

                try {
                    deleteButton.click();
                    Logger.info("The product number [%s] is deleted successfully.", String.valueOf(productNumber));
                    productNumber++;

                    // Wait for the cart to update (wait for the old button to become stale)
                    Thread.sleep(3000);
                    wait.until(ExpectedConditions.stalenessOf(deleteButton));

                } catch (Exception clickException) {
                    Logger.info("Failed to click delete button: [%s]", clickException.getMessage());
                    break; // Break the loop if we encounter repeated issues
                }
            }

            Logger.info("Cart cleared successfully.");
        } catch (Exception e) {
            Logger.info("Error clearing the cart: [%s]", e.getMessage());
        }

    }
}
