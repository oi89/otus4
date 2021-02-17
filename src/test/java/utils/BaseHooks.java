package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseHooks {
    protected static WebDriver driver;
    private static String browserName = "";
    public static WebDriverWait wait;
    private static Logger logger;

    @BeforeAll
    public static void setUp() {
        try {
            browserName = System.getProperty("browser").trim().toLowerCase();
        } catch (Exception exception) {
            logger = LogManager.getLogger(BaseHooks.class);
            logger.info("Браузер не указан. Будет запущен Chrome.");
        }

        driver = WebDriverFactory.create(browserName);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void deleteCookies() {
        driver.manage().deleteAllCookies();
    }
}
