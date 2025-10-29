package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Lógica de interacción con el usuario (menú en consola).
 */
public class Conversor {

    private final ExchangeRateClient client;
    private final List<HistoryItem> history;

    public Conversor() {
        this.client = new ExchangeRateClient();
        this.history = new ArrayList<>();
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // para usar punto decimal

        boolean seguir = true;
        while (seguir) {
            System.out.println("\n==============================================");
            System.out.println("  🪙  Sea bienvenido/a al Conversor de Moneda");
            System.out.println("==============================================");
            System.out.println("1) Convertir entre dos monedas");
            System.out.println("2) Ver monedas soportadas");
            System.out.println("3) Ver historial de conversiones");
            System.out.println("4) Salir");
            System.out.print("Elija una opción: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    convertirFlujo(scanner);
                    break;
                case "2":
                    CurrencyCodes.printSupported();
                    break;
                case "3":
                    mostrarHistorial();
                    break;
                case "4":
                    seguir = false;
                    System.out.println("¡Gracias por usar el conversor! 👋");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void convertirFlujo(Scanner scanner) {
        try {
            System.out.println("\nIngrese el código de moneda ORIGEN (ej: USD): ");
            CurrencyCodes.printSupported();
            String from = scanner.nextLine().trim().toUpperCase();

            if (CurrencyCodes.isSupported(from)) {
                System.out.println("Moneda no soportada: " + from);
                return;
            }

            System.out.println("\nIngrese el código de moneda DESTINO (ej: ARS): ");
            String to = scanner.nextLine().trim().toUpperCase();

            if (CurrencyCodes.isSupported(to)) {
                System.out.println("Moneda no soportada: " + to);
                return;
            }

            if (from.equals(to)) {
                System.out.println("Las monedas ORIGEN y DESTINO no pueden ser iguales.");
                return;
            }

            System.out.print("\nIngrese el monto a convertir: ");
            String montoStr = scanner.nextLine().trim();
            double amount;
            try {
                amount = Double.parseDouble(montoStr);
                if (amount < 0) {
                    System.out.println("El monto debe ser positivo.");
                    return;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Formato de monto inválido. Use punto como separador decimal (ej: 1234.56).");
                return;
            }

            System.out.println("\nConsultando tasa en la API...");
            double rate = client.getConversionRate(from, to);
            double result = amount * rate;

            HistoryItem item = new HistoryItem(from, to, amount, rate, result);
            history.add(item);

            System.out.printf(Locale.US,
                    "\nResultado: %.2f %s → %.2f %s (tasa: %.6f)\n",
                    amount, from, result, to, rate);

        } catch (IOException ioe) {
            System.out.println("Error de red/IO: " + ioe.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.out.println("La operación fue interrumpida.");
        } catch (IllegalStateException ise) {
            System.out.println("Configuración faltante: " + ise.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private void mostrarHistorial() {
        if (history.isEmpty()) {
            System.out.println("\nNo hay conversiones registradas aún.");
            return;
        }
        System.out.println("\nHistorial de conversiones:");
        for (HistoryItem item : history) {
            System.out.println(" - " + item);
        }
    }
}
