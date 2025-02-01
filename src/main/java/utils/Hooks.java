package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.*;
import pages.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Hooks {
    // Use ThreadLocal to ensure thread safety for parallel execution
    public static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public LoginPage loginPage;
    public HomePage homePage;
    public GamesFilterPage gamesFilterPage;
    public CartPage cartPage;
    public CheckoutPage checkoutPage;

    // Test data
    public String fullName;
    public String phoneNumber;
    public String streetName;
    public String buildingNo;
    public String cityName;
    public String districtName;

    // Get the WebDriver instance for the current thread
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // Setup web driver (chrome, firefox, edge) based on browserName
    @Parameters("browserName")
    @BeforeClass
    public void setup(@Optional("chrome") String browserName) throws IOException {
        // Ensure screenshots directory exists or create one
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdir();
        }

        // Initialize WebDriver based on the browserName parameter
        WebDriver driver;
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // Check if running in GitHub Actions
            if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
                options.addArguments("--headless"); // Enable headless only in GitHub Actions
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }else
                options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
                options.addArguments("--headless"); // Enable headless only in GitHub Actions
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }else
                options.addArguments("--start-maximized");
            driver = new FirefoxDriver(options);
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
                options.addArguments("--headless"); // Enable headless only in GitHub Actions
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }else
                options.addArguments("--start-maximized");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        // Set the driver instance for the current thread
        driverThreadLocal.set(driver);

        // Initialize page objects
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        gamesFilterPage = new GamesFilterPage(getDriver());
        cartPage = new CartPage(getDriver());
        checkoutPage = new CheckoutPage(getDriver());

        // Initialize test data
        fullName = userData().getFullName();
        phoneNumber = userData().getPhoneNumber();
        streetName = userData().getStreetName();
        buildingNo = userData().getBuildingNo();
        cityName = userData().getCityName();
        districtName = userData().getDistrictName();
    }

    // Read user data from JSON file
    public UserData userData() {
        UserData userData = null;
        String jsonFilePath = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "utils", "UserData.json").toString();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            userData = objectMapper.readValue(new File(jsonFilePath), UserData.class);
        } catch (IOException e) {
            Logger.info("Error occurred while reading the JSON file: [%s]", e.getMessage());
        }
        return userData;
    }

    // Capture screenshot and attach it to Allure report
    public void captureScreenshot(String testName) {
        WebDriver driver = getDriver();
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Path.of("screenshots/" + testName + " " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png"), screenshot);
            Allure.addAttachment("FailedScenarioScreenshot", new ByteArrayInputStream(screenshot));
            System.out.println(MessageFormat.format("Screenshot saved at: screenshots/{0}", testName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png"));
        } catch (IOException e) {
            Logger.info("Error occurred while saving the screenshot: [%s]", e.getMessage());

        }
    }

    public static String getValueFromConfig(String propertyKey) throws IOException {
        //Get browser name from the config file
        Properties prop = new Properties();
        FileInputStream configData = new FileInputStream("src/main/resources/config.properties");;
        prop.load(configData);
        return prop.getProperty(propertyKey);
    }

    // Quit the driver after each test class
    @AfterClass
    public void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove(); // Clean up the ThreadLocal instance
        }
    }
}