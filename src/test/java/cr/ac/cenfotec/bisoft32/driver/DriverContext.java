package cr.ac.cenfotec.bisoft32.driver;

import org.openqa.selenium.WebDriver;

public final class DriverContext {
    // Asocia el navegador al hilo actual para que el soporte de pruebas pueda recuperarlo.
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverContext() { }

    // Guarda el WebDriver que creó la prueba.
    public static void set(WebDriver driver) { DRIVER.set(driver); }

    // Devuelve el WebDriver para la prueba que se está ejecutando en este hilo.
    public static WebDriver get() { return DRIVER.get(); }

    // Elimina la referencia cuando la prueba termina.
    public static void clear() { DRIVER.remove(); }
}
