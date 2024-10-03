package com.djm.inventa.admin.vista.producto;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

public class ListenerMoneda  extends PlainDocument {
    private JTextField jtf;
    private String txt;
    private int limit;

    public ListenerMoneda(JTextField textField) {
        this(textField, 15);
    }

    public ListenerMoneda(JTextField textField, int limit) {
        this.limit = limit;
        this.jtf = textField;
        this.jtf.setDocument(this);
        this.jtf.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent var1) {
                String text = jtf.getText();

                if (text.isEmpty()) {
                    jtf.setText("0,00");
                } else {
                    text = formatNumBs(text);
                    if (text == null || text.equals("ERROR_FORMATO")) {
                        jtf.setText("0,00");
                        jtf.requestFocus();
                    } else {
                        jtf.setText(text);
                    }
                }
            }

            public void focusGained(FocusEvent var1) {
                if (jtf.getText().equals("0,00")) {
                    jtf.selectAll();
                }
            }
        });
    }

    public void insertString(int offs, String str, AttributeSet attributeSet) throws BadLocationException {
        this.txt = this.jtf.getText();
        int l = this.txt.length();
        if (l <= this.limit) {
            if (str.length() == 1) {
                if (str.indexOf(",") != -1) {
                    int i = str.lastIndexOf(".");
                    if (i != -1) {
                        char[] chars = str.toCharArray();
                        chars[i] = ',';
                        str = new String(chars);
                    }
                }

                str = str.replace(".", ",");
                if (!str.equals(",") && !Character.isDigit(str.charAt(0))) {
                    return;
                }

                if (str.equals(",")) {
                    if (str.equals(",") & this.txt.indexOf(",") != -1) {
                        return;
                    }
                }
            }

            super.insertString(offs, str, attributeSet);
        }
    }

    private String formatNumBs(String textIn) throws NumberFormatException {
        String rtn = "0,00";
        if (textIn != null) {
            textIn = textIn.replace(".", "");
            textIn = textIn.replace(",", ".");
            double doubleAux = 0.0 ;
            try {
                doubleAux = Double.parseDouble(textIn);
            }catch(NumberFormatException exc){
                System.err.println(exc);
            }

            //DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            String nf = numberFormat.format(doubleAux);
            StringBuffer sb = new StringBuffer();
            char ch;
            for(int i = 0;i<nf.length();i++){
                ch = nf.charAt(i);
                if(Character.isDigit(ch) || ch == '.'|| ch == ','|| ch == '-')
                    sb.append(ch);
            }

            rtn = sb.toString();
        }

        return rtn;
    }
}
