package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryItem {
    private final String from;
    private final String to;
    private final double amount;
    private final double rate;
    private final double result;
    private final LocalDateTime timestamp;

    public HistoryItem(String from, String to, double amount, double rate, double result) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.rate = rate;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %.2f %s → %.2f %s (tasa: %.6f)",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                amount, from, result, to, rate);
    }
}
