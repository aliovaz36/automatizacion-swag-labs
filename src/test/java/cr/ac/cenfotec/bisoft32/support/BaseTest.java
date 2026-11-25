package cr.ac.cenfotec.bisoft32.support;

import cr.ac.cenfotec.bisoft32.config.Config;
import cr.ac.cenfotec.bisoft32.driver.DriverContext;
import cr.ac.cenfotec.bisoft32.driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    protected void openBrowser() {
        driver = DriverFactory.create();
        DriverContext.set(driver);
        driver.manage().window().maximize();
        driver.get(Config.get("base.url"));
    }

    @AfterEach
    protected void closeBrowser() {
        if (driver != null) driver.quit();
        DriverContext.clear();
    }
}
