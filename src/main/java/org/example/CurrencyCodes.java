package org.example;

import java.util.Arrays;
import java.util.List;

/**
 * Enum/Util con las monedas habilitadas para el desafío.
 */
public final class CurrencyCodes {
    private CurrencyCodes() {}

    public static final List<String> SUPPORTED = Arrays.asList(
            "USD", // Dólar estadounidense (base común)
            "ARS", // Peso argentino
            "BOB", // Boliviano boliviano
            "BRL", // Real brasileño
            "CLP", // Peso chileno
            "COP"  // Peso colombiano
    );

    public static boolean isSupported(String code) {
        return !SUPPORTED.contains(code.toUpperCase());
    }

    public static void printSupported() {
        System.out.println("Monedas soportadas: " + String.join(", ", SUPPORTED));
    }
}
