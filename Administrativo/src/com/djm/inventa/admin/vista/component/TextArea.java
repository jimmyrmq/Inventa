package com.djm.inventa.admin.vista.component;

import com.djm.ui.component.text.LimitedCharacter;

import javax.swing.JTextArea;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class TextArea extends JTextArea {
    public TextArea(int row, int columns){
        super(row, columns);
        setTabSize(0);
        setOpaque(true);
        setLineWrap(true);
        //tNota.setWrapStyleWord(true); // Ajustar al final de las palabras
        //setFont(UIManager.getFont("TextField.font"));
    }
    public void setLimitText(int l) {
        setDocument(new LimitedCharacter(l));
    }
}
