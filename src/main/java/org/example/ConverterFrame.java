package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Locale;

public class ConverterFrame extends JFrame {

    private final JComboBox<String> comboFrom;
    private final JComboBox<String> comboTo;
    private final JTextField txtAmount;
    private final JButton btnConvert;
    private final JLabel lblResult;
    private final DefaultListModel<String> historyModel;
    private final JList<String> historyList;

    private final ExchangeRateClient client;
    private final DecimalFormat resultFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.US);

    public ConverterFrame() {
        super("Conversor de Monedas — Swing + HttpClient + Gson");

        // Cliente API (usa la variable de entorno EXCHANGE_RATE_API_KEY)
        client = new ExchangeRateClient();

        // Formato de salida
        resultFormat.applyPattern("#,##0.00######");

        // Componentes
        comboFrom = new JComboBox<>(CurrencyCodes.SUPPORTED.toArray(new String[0]));
        comboTo   = new JComboBox<>(CurrencyCodes.SUPPORTED.toArray(new String[0]));
        txtAmount = new JTextField(12);
        btnConvert = new JButton("Convertir");
        lblResult = new JLabel("Resultado: —");
        historyModel = new DefaultListModel<>();
        historyList = new JList<>(historyModel);

        // Estado inicial
        comboFrom.setSelectedItem("USD");
        comboTo.setSelectedItem("ARS");
        txtAmount.setText("100");

        // Layout principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior (formulario)
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(16, 16, 8, 16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; form.add(new JLabel("Moneda origen:"), gc);
        gc.gridx = 1; gc.gridy = 0; form.add(comboFrom, gc);

        gc.gridx = 0; gc.gridy = 1; form.add(new JLabel("Moneda destino:"), gc);
        gc.gridx = 1; gc.gridy = 1; form.add(comboTo, gc);

        gc.gridx = 0; gc.gridy = 2; form.add(new JLabel("Monto:"), gc);
        gc.gridx = 1; gc.gridy = 2; form.add(txtAmount, gc);

        gc.gridx = 0; gc.gridy = 3; gc.gridwidth = 2;
        btnConvert.setPreferredSize(new Dimension(120, 36));
        form.add(btnConvert, gc);

        // Panel resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(0, 16, 8, 16));
        lblResult.setFont(lblResult.getFont().deriveFont(Font.BOLD, 16f));
        resultPanel.add(lblResult, BorderLayout.CENTER);

        // Panel historial
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(0, 16, 16, 16));
        historyPanel.add(new JLabel("Historial de conversiones:"), BorderLayout.NORTH);
        historyList.setVisibleRowCount(8);
        historyPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);

        // Ensamblar
        add(form, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(historyPanel, BorderLayout.SOUTH);

        // Acción botón
        btnConvert.addActionListener(e -> whenConvert());
    }

    private void whenConvert() {
        String from = (String) comboFrom.getSelectedItem();
        String to   = (String) comboTo.getSelectedItem();
        String amountStr = txtAmount.getText().trim();

        if (from == null || to == null) {
            showError("Seleccione monedas válidas.");
            return;
        }
        if (from.equals(to)) {
            showError("Las monedas ORIGEN y DESTINO no pueden ser iguales.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount < 0) {
                showError("El monto debe ser positivo.");
                return;
            }
        } catch (NumberFormatException ex) {
            showError("Monto inválido. Use punto como separador decimal (ej: 1234.56).");
            return;
        }

        btnConvert.setEnabled(false);
        lblResult.setText("Consultando tasa…");

        // Llamada HTTP en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            double rate;
            double result;

            @Override
            protected Void doInBackground() throws Exception {
                rate = client.getConversionRate(from, to);
                result = amount * rate;
                return null;
            }

            @Override
            protected void done() {
                btnConvert.setEnabled(true);
                try {
                    get(); // Propaga excepción si hubo
                    String formatted = String.format(
                            Locale.US, "Resultado: %s %s → %s %s  (tasa: %s)",
                            resultFormat.format(amount), from,
                            resultFormat.format(result), to,
                            resultFormat.format(rate)
                    );
                    lblResult.setText(formatted);
                    historyModel.addElement(formatted);
                } catch (Exception ex) {
                    showError("Error al convertir: " + ex.getMessage());
                    lblResult.setText("Resultado: —");
                }
            }
        };
        worker.execute();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}
