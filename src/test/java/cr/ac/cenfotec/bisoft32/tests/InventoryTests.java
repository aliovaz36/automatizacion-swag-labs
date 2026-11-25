package cr.ac.cenfotec.bisoft32.tests;

import cr.ac.cenfotec.bisoft32.pages.CartPage;
import cr.ac.cenfotec.bisoft32.pages.InventoryPage;
import cr.ac.cenfotec.bisoft32.pages.LoginPage;
import cr.ac.cenfotec.bisoft32.support.BaseTest;
import cr.ac.cenfotec.bisoft32.support.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ScreenshotOnFailureExtension.class)
class InventoryTests extends BaseTest {
    @Test
    @DisplayName("TC04 - Agregar y quitar un producto del carrito")
    void addAndRemoveProductFromCart() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.addBackpackToCart();

        assertEquals("Remove", inventoryPage.backpackActionText());
        assertEquals(1, inventoryPage.cartItemCount());

        CartPage cartPage = inventoryPage.openCart();
        assertEquals(1, cartPage.itemCount());
        assertTrue(cartPage.containsProduct("Sauce Labs Backpack"));

        inventoryPage = cartPage.continueShopping();
        inventoryPage.removeBackpackFromCart();
        assertEquals("Add to cart", inventoryPage.backpackActionText());
        assertEquals(0, inventoryPage.cartItemCount());
        assertEquals(0, inventoryPage.openCart().itemCount());
    }

    @Test
    @DisplayName("TC05 — ordenar productos por precio de menor a mayor")
    void sortProductsByPriceAscending() {
        InventoryPage inventoryPage = loginAsStandardUser();
        inventoryPage.sortByPriceAscending();

        List<String> names = inventoryPage.productNames();
        List<String> prices = inventoryPage.productPriceTexts();
        assertEquals(
                List.of("$7.99", "$9.99", "$15.99", "$15.99", "$29.99", "$49.99"),
                prices,
                "Todo el catálogo debe quedar ordenado de menor a mayor precio"
        );
        assertEquals("Sauce Labs Onesie", names.get(0));
        assertEquals("$7.99", prices.get(0));
        assertEquals("Sauce Labs Bike Light", names.get(1));
        assertEquals("$9.99", prices.get(1));
        assertEquals("Sauce Labs Backpack", names.get(names.size() - 2));
        assertEquals("$29.99", prices.get(prices.size() - 2));
        assertEquals("Sauce Labs Fleece Jacket", names.get(names.size() - 1));
        assertEquals("$49.99", prices.get(prices.size() - 1));
    }

    private InventoryPage loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentials("standard_user", "secret_sauce");
        return loginPage.submitValidLogin();
    }
}
