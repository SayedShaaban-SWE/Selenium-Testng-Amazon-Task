package tests;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Hooks;
import utils.Logger;

import java.io.IOException;


public class AmazonTests extends Hooks {

    @BeforeMethod
    public void generalSteps() throws InterruptedException, IOException {
        //get username and password
        String userName =getValueFromConfig("username");
        String password =getValueFromConfig("password");
        // Navigate to amazon egypt
        getDriver().navigate().to(getValueFromConfig("url"));
        //Select EN language
        homePage.selectEnLanguage();
        // Login to the amazon website
        homePage.makeHoverOnSignInLink();
        homePage.clickOnSignIn();
        loginPage.login(userName, password);
        // Clear the shopping cart
        cartPage.clearCart();
    }

    @Test()
    public void addProductsToTheCartAndCheckout() throws InterruptedException {
        // Navigate to all video games and apply filters
        homePage.navigateToAllVideoGames();
        gamesFilterPage.applyFilters();
        gamesFilterPage.sortByPriceHighToLow();
        Logger.info("The filters applied successfully...!");

        // Add products to cart and calculate the total
        double calculatedTotal = gamesFilterPage.addProductsToCartBelowPrice(15000);

        // Open the cart and get the total
        cartPage.openCart();
        double cartTotal = cartPage.getCartTotal();

        // Verify the cart total is correct
        Assert.assertEquals(cartTotal, calculatedTotal, "Discrepancy in products total amount!");

        // Proceed to check out and complete the order
        cartPage.clickOnProceedToBuyBtn();
        checkoutPage.addAddress(fullName, phoneNumber, streetName, buildingNo, cityName, districtName);
        checkoutPage.selectCashPayment();
        Logger.info("The payment way selected successfully...!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Check if the test failed to capture screenshot
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());
        }
    }
}
