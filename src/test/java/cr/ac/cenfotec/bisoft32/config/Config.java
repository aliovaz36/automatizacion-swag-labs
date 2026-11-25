package cr.ac.cenfotec.bisoft32.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Config {
    private static final Properties PROPERTIES = load();

    private Config() { }

    private static Properties load() {
        Properties properties = new Properties();
        String externalFile = System.getProperty("config.file");
        try (InputStream input = externalFile == null
                ? Config.class.getClassLoader().getResourceAsStream("config.properties")
                : Files.newInputStream(Path.of(externalFile))) {
            if (input == null) {
                throw new IllegalStateException("No se encontró config.properties");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer config.properties", exception);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, PROPERTIES.getProperty(key));
    }

    public static int waitSeconds() {
        return Integer.parseInt(get("wait.seconds"));
    }
}
