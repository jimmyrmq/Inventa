package com.djm.inventa.util;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public final class DecimalField {

    private DecimalField() {
    }


    public static void configurar(JTextField campo) {
        configurar(campo, false);
    }

    public static void configurar(JTextField campo, boolean mostrarDecimalConCero) {

        String textoActual = campo.getText();

        FormatDecimalDocument document = new FormatDecimalDocument(mostrarDecimalConCero);

        campo.setDocument(document);

        if (textoActual != null && !textoActual.isBlank()) {
            campo.setText(textoActual);
        }

        campo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {

                String texto = document.obtenerTextoFormateado();

                try {
                    document.setTextoFormateado(texto);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                campo.selectAll();
            }
        });
    }
}