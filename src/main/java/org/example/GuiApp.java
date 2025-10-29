package org.example;

import javax.swing.*;

public class GuiApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Look & Feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new ConverterFrame().setVisible(true);
        });
    }
}
