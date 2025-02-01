package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    // Locators using By
    private static final By EMAIL_FIELD = By.xpath("//*[@id='ap_email' or @id='ap_email_login']");
    private static final By PASSWORD_FIELD = By.id("ap_password");
    private static final By CONTINUE_BTN = By.id("continue");
    private static final By SIGN_IN_BUTTON = By.id("signInSubmit");

    WebDriver driver;
    WebDriverWait wait;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        driver.findElement(EMAIL_FIELD).sendKeys(username);
        driver.findElement(CONTINUE_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(SIGN_IN_BUTTON).click();
    }
}
