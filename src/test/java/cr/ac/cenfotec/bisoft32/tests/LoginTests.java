package cr.ac.cenfotec.bisoft32.tests;

import cr.ac.cenfotec.bisoft32.pages.InventoryPage;
import cr.ac.cenfotec.bisoft32.pages.LoginPage;
import cr.ac.cenfotec.bisoft32.support.BaseTest;
import cr.ac.cenfotec.bisoft32.support.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ScreenshotOnFailureExtension.class)
class LoginTests extends BaseTest {
    @ParameterizedTest(name = "{0} — validar inicio de sesión")
    @CsvFileSource(resources = "/data/login-cases.csv", numLinesToSkip = 1)
    @DisplayName("TC01-TC03 — validaciones de inicio de sesión")
    void loginCases(String caseId, String username, String password, String expectedMessage) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentials(username, password);

        if ("TC01".equals(caseId)) {
            InventoryPage inventoryPage = loginPage.submitValidLogin();
            assertTrue(inventoryPage.isInventoryVisible(), "El inventario debe mostrarse");
            assertTrue(inventoryPage.containsProduct("Sauce Labs Backpack"), "Debe aparecer el producto de referencia");
        } else {
            loginPage.submitLogin();
            assertEquals(expectedMessage, loginPage.errorText());
            assertTrue(loginPage.inventoryIsAbsent(), "No se debe acceder al inventario");
        }
    }
}
