package com.djm.inventa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class FormatDecimalDocument extends PlainDocument {

    private static final Logger log = LoggerFactory.getLogger(FormatDecimalDocument.class);

    private static final int MAX_DECIMALES = 2;
    private static final int DEFAULT_LIMIT = 15;

    private final int limit;

    private final DecimalFormat formato;
    private final String decimal;
    private final String agrupador;

    private boolean formateando = false;

    public FormatDecimalDocument() {
        this(DEFAULT_LIMIT, false);
    }

    public FormatDecimalDocument(boolean mostrarDecimalConCero) {
        this(DEFAULT_LIMIT, mostrarDecimalConCero);
    }

    public FormatDecimalDocument(int limit, boolean mostrarDecimalConCero) {
        this.limit = limit;

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();

        formato = (DecimalFormat) NumberFormat.getNumberInstance();
        formato.setDecimalFormatSymbols(symbols);
        formato.setGroupingUsed(true);
        formato.setMinimumFractionDigits(mostrarDecimalConCero ? MAX_DECIMALES : 0);
        formato.setMaximumFractionDigits(MAX_DECIMALES);

        decimal = String.valueOf(symbols.getDecimalSeparator());
        agrupador = String.valueOf(symbols.getGroupingSeparator());
    }


    @Override
    public void insertString(int offs, String str, AttributeSet attrs)
            throws BadLocationException {

        if (formateando || str == null) {
            return;
        }

        String textoActual = getText(0, getLength());
        String txt = getText(0, getLength());

        if (textoActual.length() >= limit) {
            return;
        }

        if (str.length() == 1) {

            str = str.replace(agrupador, decimal);

            if ((!str.equals(decimal) && !Character.isDigit(str.charAt(0)))
                    || (str.equals(decimal) && textoActual.contains(decimal))) {
                return;
            }
        }

        super.insertString(offs, str, attrs);
    }

    public String obtenerTextoFormateado() {

        String texto = getTextSeguro();

        if (texto.isBlank()) {
            return formato.format(BigDecimal.ZERO);
        }

        try {
            texto = texto.replace(agrupador, "")
                    .replace(decimal, ".");

            BigDecimal valor = new BigDecimal(texto);

            return formato.format(valor);

        } catch (NumberFormatException e) {
            log.error("Error convirtiendo número [{}]", texto, e);
            return formato.format(BigDecimal.ZERO);
        }
    }


    private String getTextSeguro() {
        try {
            return getText(0, getLength());
        } catch (BadLocationException e) {
            return "";
        }
    }

    public void setTextoFormateado(String texto) throws BadLocationException {

        try {
            formateando = true;

            super.remove(0, getLength());
            super.insertString(0, texto, null);

        } finally {
            formateando = false;
        }
    }
}