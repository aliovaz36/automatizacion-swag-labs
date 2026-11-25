package cr.ac.cenfotec.bisoft32.driver;

import org.openqa.selenium.WebDriver;

public final class DriverContext {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverContext() { }

    public static void set(WebDriver driver) { DRIVER.set(driver); }
    public static WebDriver get() { return DRIVER.get(); }
    public static void clear() { DRIVER.remove(); }
}
