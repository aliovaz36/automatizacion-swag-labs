package cr.ac.cenfotec.bisoft32.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {
    // Localizadores de catálogo, acciones de la mochila, carrito y datos de productos.
    private final By inventory = By.id("inventory_container");
    private final By backpackAddButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By backpackRemoveButton = By.id("remove-sauce-labs-backpack");
    private final By cartButton = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By productNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrices = By.cssSelector("[data-test='inventory-item-price']");

    public InventoryPage(WebDriver driver) { super(driver); }

    public boolean isInventoryVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(inventory)).isDisplayed();
    }

    public boolean containsProduct(String productName) {
        return driver.findElements(By.cssSelector("[data-test='inventory-item-name']"))
                .stream().anyMatch(element -> productName.equals(element.getText().trim()));
    }

    public void addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(backpackAddButton)).click();
        // Espera ambos cambios visibles que confirman que el producto se agregó.
        wait.until(ExpectedConditions.presenceOfElementLocated(backpackRemoveButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
    }

    public void removeBackpackFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(backpackRemoveButton)).click();
        // El botón vuelve a Add to cart y el indicador desaparece cuando el carrito queda vacío.
        wait.until(ExpectedConditions.presenceOfElementLocated(backpackAddButton));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public String backpackActionText() {
        // El identificador del botón cambia según la mochila esté agregada o no.
        List<WebElement> removeButtons = driver.findElements(backpackRemoveButton);
        return removeButtons.isEmpty()
                ? driver.findElement(backpackAddButton).getText()
                : removeButtons.get(0).getText();
    }

    public int cartItemCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        // Sin indicador significa cero productos; si existe, su texto contiene la cantidad.
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    public CartPage openCart() {
        driver.findElement(cartButton).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return new CartPage(driver);
    }

    public void sortByPriceAscending() {
        WebElement sortSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("product_sort_container")
        ));
        new Select(sortSelect).selectByValue("lohi");
        // Espera que el precio menor aparezca primero tras aplicar el ordenamiento.
        wait.until(ExpectedConditions.textToBe(
                By.cssSelector("[data-test='inventory-item-price']"),
                "$7.99"
        ));
    }

    public List<String> productNames() {
        return driver.findElements(productNames).stream().map(element -> element.getText().trim()).toList();
    }

    public List<String> productPriceTexts() {
        return driver.findElements(productPrices).stream().map(element -> element.getText().trim()).toList();
    }
}
