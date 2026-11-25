package cr.ac.cenfotec.bisoft32.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    // Localizadores de los campos, botón y mensaje de error del formulario.
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) { super(driver); }

    public void enterCredentials(String username, String password) {
        driver.findElement(usernameField).clear();
        // Convierte null en vacío para permitir los casos sin credenciales del CSV.
        driver.findElement(usernameField).sendKeys(username == null ? "" : username);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password == null ? "" : password);
    }

    public InventoryPage submitValidLogin() {
        driver.findElement(loginButton).click();
        // Confirma que el login llegó al inventario antes de devolver su Page Object.
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        InventoryPage inventoryPage = new InventoryPage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        return inventoryPage;
    }

    public void submitLogin() {
        driver.findElement(loginButton).click();
    }

    public String errorText() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return error.getText();
    }

    public boolean inventoryIsAbsent() {
        return driver.findElements(By.id("inventory_container")).isEmpty();
    }
}
