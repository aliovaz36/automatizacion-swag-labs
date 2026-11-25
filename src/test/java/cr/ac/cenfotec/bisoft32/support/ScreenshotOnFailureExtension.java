package cr.ac.cenfotec.bisoft32.support;

import cr.ac.cenfotec.bisoft32.driver.DriverContext;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) return;
        WebDriver driver = DriverContext.get();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) return;

        String safeName = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        Path screenshot = Path.of("target", "screenshots", safeName + ".png");
        try {
            Files.createDirectories(screenshot.getParent());
            Files.write(screenshot, screenshotDriver.getScreenshotAs(OutputType.BYTES));
            System.err.println("Captura del fallo: " + screenshot.toAbsolutePath());
        } catch (IOException exception) {
            System.err.println("No se pudo guardar la captura: " + exception.getMessage());
        }
    }
}
