package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Cliente simple para ExchangeRate-API.
 * Usa el endpoint "pair" para obtener la tasa entre dos monedas.
 *
 * Requiere la variable de entorno: EXCHANGE_RATE_API_KEY
 */
public class ExchangeRateClient {

    private final HttpClient httpClient;
    private final String apiKey;

    public ExchangeRateClient() {
        this.httpClient = HttpClient.newHttpClient();
        String key = System.getenv("EXCHANGE_RATE_API_KEY");
        if (key == null || key.isBlank()) {
            throw new IllegalStateException("Falta la variable de entorno EXCHANGE_RATE_API_KEY");
        }
        this.apiKey = key.trim();
    }

    /**
     * Obtiene la tasa de conversión entre 'from' y 'to'.
     * @param from Moneda origen (ej: "USD")
     * @param to   Moneda destino (ej: "ARS")
     * @return tasa (double)
     */
    public double getConversionRate(String from, String to) throws IOException, InterruptedException {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s",
                apiKey, from.toUpperCase(), to.toUpperCase());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Error HTTP " + response.statusCode() + " al consultar la API");
        }

        JsonElement root = JsonParser.parseString(response.body());
        JsonObject obj = root.getAsJsonObject();

        String result = obj.has("result") ? obj.get("result").getAsString() : "error";
        if (!"success".equalsIgnoreCase(result)) {
            String errorType = obj.has("error-type") ? obj.get("error-type").getAsString() : "desconocido";
            throw new IOException("La API respondió con error: " + errorType);
        }

        if (!obj.has("conversion_rate")) {
            throw new IOException("Respuesta sin 'conversion_rate'");
        }

        return obj.get("conversion_rate").getAsDouble();
    }

    /**
     * Convierte un monto usando la tasa remota.
     */
    public double convert(String from, String to, double amount) throws IOException, InterruptedException {
        double rate = getConversionRate(from, to);
        return amount * rate;
    }
}
