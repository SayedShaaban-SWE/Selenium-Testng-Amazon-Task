package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ScrollHelper;

import java.time.Duration;

public class HomePage {
    // Locators using By
    private static final By LANGUAGES_LIST = By.xpath("//*[@id=\"icp-nav-flyout\"]/span");
    private static final By EN_LANG = By.xpath("//*[@id=\"icp-language-settings\"]/div[3]/div/label/i");
    private static final By SAVE_CHANGES_BTN = By.xpath("//*[@id=\"icp-save-button\"]/span/input");
    private static final By LOGIN_LINK = By.id("nav-link-accountList");
    private static final By ALL_MENU_ICON = By.id("nav-hamburger-menu");
    private static final By SEE_ALL = By.xpath("//div[contains(text(),'See all')]");
    private static final By VIDEO_GAMES = By.xpath("(//div[@id='hmenu-container']//a[div[text()='Video Games']])[1]");
    private static final By ALL_VIDEO_GAMES = By.xpath("(//*[text()='All Video Games'])[2]");

    WebDriver driver;
    WebDriverWait wait;
    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    public void selectEnLanguage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(LANGUAGES_LIST));
        driver.findElement(LANGUAGES_LIST).click();
        driver.findElement(EN_LANG).click();
        driver.findElement(SAVE_CHANGES_BTN).click();
    }
    public void makeHoverOnSignInLink() throws InterruptedException {
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_LINK));
        Thread.sleep(3000);
        actions.moveToElement(driver.findElement(LOGIN_LINK)).perform();
    }
    public void clickOnSignIn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_LINK));
        driver.findElement(LOGIN_LINK).click();
    }
    public void navigateToAllVideoGames(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ALL_MENU_ICON));
        driver.findElement(ALL_MENU_ICON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEE_ALL));
        driver.findElement(SEE_ALL).click();
        ScrollHelper.scrollToElementInContainer(driver,VIDEO_GAMES,500,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(VIDEO_GAMES));
        driver.findElement(VIDEO_GAMES).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(ALL_VIDEO_GAMES));
        driver.findElement(ALL_VIDEO_GAMES).click();
    }
}
