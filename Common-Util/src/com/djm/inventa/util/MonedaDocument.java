package com.djm.inventa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class MonedaDocument extends PlainDocument {
    private static final Logger log = LoggerFactory.getLogger(MonedaDocument.class);
    private final JTextField jtf;
    private final int limit;

    //private final NumberFormat numberFormat;
    private final DecimalFormat formato;
    private final DecimalFormatSymbols symbols;
    private final int maxDecimales = 2;
    private final String decimal;
    private final String agrupador;
    private boolean formateando = false;

    public MonedaDocument(JTextField textField) {
        this(textField, 15);
    }

    public MonedaDocument(JTextField textField, int limit) {
        this.limit = limit;
        this.jtf = textField;
        this.jtf.setDocument(this);

        symbols = DecimalFormatSymbols.getInstance();

        formato = (DecimalFormat) NumberFormat.getNumberInstance();

        formato.setDecimalFormatSymbols(symbols);
        formato.setGroupingUsed(true);
        formato.setMinimumFractionDigits(maxDecimales);
        formato.setMaximumFractionDigits(maxDecimales);

        //numberFormat = NumberFormat.getNumberInstance();

        decimal = String.valueOf(symbols.getDecimalSeparator());
        agrupador = String.valueOf(symbols.getGroupingSeparator());

        //DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
        //NumberFormat numberFormat = NumberFormat.getCurrencyInstance();//Este agrega la moneda

        agregarFocus();

    }

    private void agregarFocus() {

        this.jtf.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent var1) {
                String text = jtf.getText();

                if (text == null || text.isBlank()){
                   //jtf.setText("0"+decimal+"00");
                    //jtf.setText(formato.format(BigDecimal.ZERO));
                    cambiarTexto(formato.format(BigDecimal.ZERO));
                }
                else {
                    text = formatearNumero(text);
                    if (text == null){// || text.equals("ERROR_FORMATO")) {
                        //jtf.setText(formato.format(BigDecimal.ZERO));
                        cambiarTexto(formato.format(BigDecimal.ZERO));
                        jtf.requestFocus();
                    }
                    else {
                        //cambiarTexto(text);
                        jtf.setText(text);
                    }
                }
            }

            public void focusGained(FocusEvent var1) {
                //if (jtf.getText().equals("0,00")) {
                if (jtf.getText().equals(formato.format(BigDecimal.ZERO))){
                    jtf.selectAll();
                }
            }
        });
    }

    public void insertString(int offs, String str, AttributeSet attributeSet) throws BadLocationException {
        String txt = this.jtf.getText();

        if (formateando || str == null) {
            return;
        }

        int l = txt.length();
        if (l <= this.limit) {
            if (str.length() == 1) {
                if (str.indexOf(decimal) != -1) {
                    int i = str.lastIndexOf(agrupador);
                    if (i != -1) {
                        char[] chars = str.toCharArray();
                        //chars[i] = decimal.charAt(0);
                        str = new String(chars);
                    }
                }

                //str = str.replace(".", ",");
                str = str.replace(agrupador, decimal);

                if ((!str.equals(decimal) && !Character.isDigit(str.charAt(0))) ||
                        (str.equals(decimal) && txt.indexOf(decimal) != -1)) {
                    return;
                }
            }

            super.insertString(offs, str, attributeSet);
        }
    }

    private String formatearNumero(String textIn) throws NumberFormatException {

        if (textIn == null ||textIn.isBlank()) {
            return formato.format(BigDecimal.ZERO);//textIn;
        }

        textIn = textIn.replace(agrupador, "");
        textIn = textIn.replace(decimal, ".");

        try {
            BigDecimal valor = new BigDecimal(textIn);
            return formato.format(valor);
        }catch(NumberFormatException exc){
            log.error("Error convirtiendo número [{}]", textIn, exc);
            return null;
        }

        //String nf = numberFormat.format(valor);
        //rtn = validNumber(nf);

    }

    /*private String validNumber(String nf){
        StringBuffer sb = new StringBuffer();
        char ch;
        for(int i = 0;i<nf.length();i++){
            ch = nf.charAt(i);
            if(Character.isDigit(ch) || ch == '.'|| ch == ','|| ch == '-')
                sb.append(ch);
        }

        return sb.toString();
    }*/

    private void cambiarTexto(String texto) {

        try {
            formateando = true;
            jtf.setText(texto);
        }
        finally {
            formateando = false;
        }

    }

}
