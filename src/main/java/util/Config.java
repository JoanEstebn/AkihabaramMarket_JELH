/**
 * Configuración para el uso de la ia mistralai/mistral-7b-instruct:free.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("No se encontró el archivo config.properties");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error cargando config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
