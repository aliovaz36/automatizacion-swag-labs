package cr.ac.cenfotec.bisoft32.driver;

import cr.ac.cenfotec.bisoft32.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Locale;

public final class DriverFactory {
    private DriverFactory() { }

    public static WebDriver create() {
        String browser = Config.get("browser").toLowerCase(Locale.ROOT);
        return switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions());
            case "firefox" -> new FirefoxDriver(firefoxOptions());
            case "edge" -> new EdgeDriver(edgeOptions());
            case "safari" -> new SafariDriver();
            default -> throw new IllegalArgumentException("Navegador no soportado: " + browser);
        };
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(Config.get("headless"))) options.addArguments("--headless=new");
        options.addArguments("--window-size=1440,1000");
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (Boolean.parseBoolean(Config.get("headless"))) options.addArguments("-headless");
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        if (Boolean.parseBoolean(Config.get("headless"))) options.addArguments("--headless=new");
        return options;
    }
}
