package pages;

import io.cucumber.java.an.E;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Logger;
import utils.ScrollHelper;

import java.time.Duration;

public class CheckoutPage {
    // Locators
    private static final By ADD_NEW_ADDRESS_LINK = By.id("add-new-address-desktop-sasp-tango-link");
    private static final By FULL_NAME_FIELD = By.id("address-ui-widgets-enterAddressFullName");
    private static final By PHONE_NUMBER_FIELD = By.id("address-ui-widgets-enterAddressPhoneNumber");
    private static final By STREET_NAME_FIELD = By.id("address-ui-widgets-enterAddressLine1");
    private static final By BUILDING_NAME_FIELD = By.id("address-ui-widgets-enter-building-name-or-number");
    private static final By CITY_NAME_FIELD = By.id("address-ui-widgets-enterAddressCity");
    private static final By FIRST_OPTION = By.id("address-ui-widgets-autoCompleteResult-0");
    private static final By DISTRICT_FIELD = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    private static final By SUBMIT_ADDRESS_BUTTON = By.id("checkout-primary-continue-button-id");
    private static final By VALUE_PAYMENT_OPTION = By.xpath("(//input[@name='ppw-instrumentRowSelection'])[1]");
    private static final By CASH_PAYMENT_OPTION = By.xpath("(//input[@name='ppw-instrumentRowSelection'])[2]");
    private static final By USE_THIS_PAYMENT_METHOD_BUTTON = By.id("checkout-primary-continue-button-id");

    WebDriver driver;

    WebDriverWait wait;
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void addAddress(String fullName, String phoneNumber, String streetName, String buildingNo, String cityName, String districtName) {
        try {
            // Step 1: Click 'Add New Address' link
            wait.until(ExpectedConditions.elementToBeClickable(ADD_NEW_ADDRESS_LINK)).click();

            // Step 2: Fill in the Full Name
            WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME_FIELD));
            fullNameField.sendKeys(fullName);

            // Step 3: Enter Phone Number
            WebElement phoneNumberField = driver.findElement(PHONE_NUMBER_FIELD);
            phoneNumberField.clear();
            phoneNumberField.sendKeys(phoneNumber);

            // Step 4: Enter Street Name
            WebElement streetNameField = driver.findElement(STREET_NAME_FIELD);
            streetNameField.clear();
            streetNameField.sendKeys(streetName);

            // Step 5: Enter Building Number
            WebElement buildingNoField = driver.findElement(BUILDING_NAME_FIELD);
            buildingNoField.clear();
            buildingNoField.sendKeys(buildingNo);

            // Step 6: Handle City Name (Open List)
            WebElement cityNameField = driver.findElement(CITY_NAME_FIELD);
            cityNameField.clear();
            cityNameField.click();
            cityNameField.sendKeys(cityName);

            // Wait for the dropdown or suggestions to appear and select the correct city
            try {
                WebElement cityOption1 = wait.until(ExpectedConditions.elementToBeClickable(FIRST_OPTION));
                cityOption1.click();
            }catch (Exception e) {
                Logger.info("The first option is selected: [%s]", e.getMessage());
            }


            // Step 7: Wait for District Field to be enabled
            wait.until(ExpectedConditions.elementToBeClickable(DISTRICT_FIELD));
            WebElement districtNameField = driver.findElement(DISTRICT_FIELD);

            // Step 8: Enter District Name
            districtNameField.clear();
            districtNameField.click();
            districtNameField.sendKeys(districtName);

            try {
                WebElement cityOption2 = wait.until(ExpectedConditions.elementToBeClickable(FIRST_OPTION));
                cityOption2.click();
            }catch (Exception e) {
                Logger.info("The first option is selected: [%s]", e.getMessage());
            }

            // Step 9: Scroll and Submit
            wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT_ADDRESS_BUTTON));
            ScrollHelper.scrollToElement(driver, SUBMIT_ADDRESS_BUTTON);
            driver.findElement(SUBMIT_ADDRESS_BUTTON).click();

        } catch (Exception e) {
            Logger.info("Failed to add address:[%s]", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void selectCashPayment() {
        try {
            WebElement cashOption = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(CASH_PAYMENT_OPTION)));
            if (cashOption.isEnabled()) {
                cashOption.click();
                Logger.info("The cash payment option is valid...!");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            Logger.info("The cash payment option is not valid: [%s]", e.getMessage());
        }
        try {
            WebElement valueOption = wait.until(ExpectedConditions.visibilityOfElementLocated(VALUE_PAYMENT_OPTION));
            if (valueOption.isEnabled()) {
                valueOption.click();
                Logger.info("The value payment option is valid...!");
            }
        }catch (NoSuchElementException | TimeoutException e) {
            Logger.info("The value payment option is not valid: [%s]", e.getMessage());
        }


        WebElement useThisPayment = wait.until(ExpectedConditions.elementToBeClickable(USE_THIS_PAYMENT_METHOD_BUTTON));
        ScrollHelper.scrollToElement(driver, USE_THIS_PAYMENT_METHOD_BUTTON);
        useThisPayment.click();
    }
}
