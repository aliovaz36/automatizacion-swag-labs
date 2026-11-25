package cr.ac.cenfotec.bisoft32.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    // Localizadores de la lista, sus productos y el botón para volver al catálogo.
    private final By cartList = By.className("cart_list");
    private final By cartItem = By.className("cart_item");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) { super(driver); }

    public boolean containsProduct(String productName) {
        return driver.findElements(By.cssSelector("[data-test='inventory-item-name']"))
                .stream().anyMatch(element -> productName.equals(element.getText().trim()));
    }

    public int itemCount() {
        // Espera a que cargue la lista antes de contar sus filas de producto.
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
        return driver.findElements(cartItem).size();
    }

    public InventoryPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        // Espera el regreso al inventario y devuelve el objeto que representa esa página.
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        InventoryPage inventoryPage = new InventoryPage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        return inventoryPage;
    }
}
