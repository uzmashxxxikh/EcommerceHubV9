package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    // ================= LOGGER =================
    protected static final Logger log = LogManager.getLogger(BaseClass.class);

    // ================= DRIVER =================
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties p;

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    public void waitForVisible(By locator) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClickable(By locator) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForText(By locator, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public void ensureScreenshotDirectory() {
        File folder = new File(System.getProperty("user.dir") + File.separator + "screenshots");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // ================= SETUP =================
    @BeforeClass(groups = { "sanity", "regression", "datadriven", "negative", "boundary" })
    @Parameters({ "os", "browser" })
    public void openApp(@Optional("windows") String os, @Optional("chrome") String br) {

        log.info("========== Test Execution Started ==========");
        log.debug("Operating System: {}", os);
        log.debug("Browser Parameter Received: {}", br);

        try {
            // Load config.properties
            log.debug("Loading config.properties file");
            FileReader file = new FileReader(".//src//test//resources//config.properties");
            p = new Properties();
            p.load(file);
            file.close();

            WebDriver localDriver = null;

            if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                // os
                if (os.equalsIgnoreCase("windows")) {
                    capabilities.setPlatform(Platform.WIN11);
                } else if (os.equalsIgnoreCase("mac")) {
                    capabilities.setPlatform(Platform.MAC);
                } else {
                    throw new RuntimeException("No matching OS found: " + os);
                }

                String gridURL = "http://localhost:4444/wd/hub";

                switch (br.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), chromeOptions.merge(capabilities));
                        break;

                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), firefoxOptions.merge(capabilities));
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), edgeOptions.merge(capabilities));
                        break;

                    default:
                        throw new RuntimeException("Invalid browser: " + br);
                }
            }

            if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
                // Browser selection
                switch (br.toLowerCase()) {
                    case "chrome":
                        log.info("Launching Chrome browser");
                        localDriver = new ChromeDriver();
                        break;

                    case "edge":
                        log.info("Launching Edge browser");
                        localDriver = new EdgeDriver();
                        break;

                    case "firefox":
                        log.info("Launching Firefox browser");
                        localDriver = new FirefoxDriver();
                        break;

                    default:
                        throw new RuntimeException("Invalid browser: " + br);
                }
            }

            driver.set(localDriver);
            log.debug("WebDriver instance set to ThreadLocal");

            // Driver configurations
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            log.debug("Implicit wait set to 10 seconds");

            String appUrl = p.getProperty("appURL");
            log.info("Navigating to application URL: {}", appUrl);
            getDriver().get(appUrl);

            getDriver().manage().window().maximize();
            log.debug("Browser window maximized");

        } catch (IOException e) {
            log.error("Failed to load configuration file", e);
            throw new RuntimeException(e);

        } catch (Exception e) {
            log.error("Exception occurred during browser setup", e);
            throw e;
        }
    }

    // ================= TEARDOWN =================
    @AfterClass(groups = { "sanity", "regression", "datadriven", "negative", "boundary" })
    public void closeApp() {

        log.info("========== Test Execution Completed ==========");

        try {
            if (getDriver() != null) {
                log.info("Closing browser and quitting WebDriver");
                getDriver().quit();
                driver.remove();
            }
        } catch (Exception e) {
            log.error("Error occurred while closing the browser", e);
        }
    }

    // ================= SCREENSHOT =================
    public String captureScreen(String tname) {

        log.debug("Capturing screenshot for test: {}", tname);
        ensureScreenshotDirectory();

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try {
            File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

            String targetFilePath = System.getProperty("user.dir")
                    + File.separator + "screenshots" + File.separator + tname + "_" + timeStamp + ".png";

            File targetFile = new File(targetFilePath);
            if (sourceFile.exists()) {
                sourceFile.renameTo(targetFile);
            }

            log.info("Screenshot saved at: {}", targetFilePath);
            return targetFilePath;

        } catch (Exception e) {
            log.error("Failed to capture screenshot for test: {}", tname, e);
            return null;
        }
    }
}