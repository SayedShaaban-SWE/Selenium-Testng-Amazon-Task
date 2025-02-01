package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Logger;
import utils.ScrollHelper;

import java.time.Duration;
import java.util.List;

public class GamesFilterPage {
    // Locators
    private static final By FREE_SHIPPING_CHECKBOX = By.xpath("//label[@for='apb-browse-refinements-checkbox_0']//i");
    private static final By NEW_CONDITION_CHECKBOX = By.xpath("//ul[@aria-labelledby='p_n_condition-type-title']//li[1]//span[1]//a[1]//span[1]");
    private static final By SORT_DROPDOWN = By.id("s-result-sort-select");
    private static final By RESULTS_TEXT = By.xpath("//h2[text()='Results']");
    private static final By PRODUCTS = By.cssSelector(".puis-list-col-right");
    private static final By PRODUCT_PRICE = By.cssSelector("span.a-price-whole");
    private static final By ADD_TO_CART_BUTTON = By.xpath(".//button[text()='Add to cart']");
    private static final By NEXT_PAGE_BUTTON = By.cssSelector(".s-pagination-next");

    WebDriver driver;
    static WebDriverWait wait;
    public GamesFilterPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void applyFilters(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(FREE_SHIPPING_CHECKBOX));
        ScrollHelper.scrollToElement(driver,FREE_SHIPPING_CHECKBOX);
        driver.findElement(FREE_SHIPPING_CHECKBOX).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(NEW_CONDITION_CHECKBOX));
        ScrollHelper.scrollToElement(driver,NEW_CONDITION_CHECKBOX);
        driver.findElement(NEW_CONDITION_CHECKBOX).click();
    }

    public void sortByPriceHighToLow() throws InterruptedException {
        Select sort = new Select(driver.findElement(SORT_DROPDOWN));
        Actions actions = new Actions(driver);
        // Select the element
        sort.selectByVisibleText("Price: High to Low");
        // Send the Enter key to the active element
        actions.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(3000);
    }
    public double addProductsToCartBelowPrice(double maxPrice) {
        boolean hasNextPage = true;
        double totalPrice = 0.0; // To store the total price of added products
        boolean productAdded = false;

        while (hasNextPage && !productAdded) {
            // Wait for products to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(RESULTS_TEXT));

            // Get all product elements
            List<WebElement> products = driver.findElements(PRODUCTS);

            for (WebElement product : products) {
                try {
                    // Scroll to the product element
                    ScrollHelper.scrollToElement(driver, product);
                    // Check if the product has an "Add to Cart" button

                        // Find the price element
                        WebElement priceElement = product.findElement(PRODUCT_PRICE);
                        double price = Double.parseDouble(priceElement.getText().replace(",", "").trim());

                        if (price < maxPrice) {
                            List<WebElement> addToCartButtons = product.findElements(ADD_TO_CART_BUTTON);
                            // Check if the product has an "Add to Cart" button
                            if (!addToCartButtons.isEmpty()) {
                                // Add to cart
                                addToCartButtons.get(0).click();
                                totalPrice += price; // Add product price to total
                                productAdded = true;
                                Logger.info("Product added to cart:[%s]", product.getText());
                            }
                        }

                } catch (Exception e) {
                    // Handle products without price or other issues
                    Logger.info("Skipping product: [%s]", e.getMessage());
                }
            }

            if (!productAdded) {
                // Check for "Next" page button
                List<WebElement> nextPageButton = driver.findElements(NEXT_PAGE_BUTTON);
                if (!nextPageButton.isEmpty() && nextPageButton.get(0).isDisplayed()) {
                    nextPageButton.get(0).click();
                    wait.until(ExpectedConditions.presenceOfElementLocated(RESULTS_TEXT));
                } else {
                    hasNextPage = false;
                    Logger.info("No more pages to navigate.");
                }
            } else {
                Logger.info("Products added to cart on this page.");
            }
        }
        return totalPrice; // Return the total price of added products
    }
}
