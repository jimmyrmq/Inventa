package com.djm.inventa.admin.vista.component;

import com.djm.ui.component.text.FieldNumeric;
import com.djm.ui.component.text.LimitedCharacter;
import com.djm.util.Image;

import javax.swing.JTextField;

public class TextField extends JTextField {
    public TextField(int column){
        this(column, -1);
    }
    public TextField(int column, int limit){
        this(column, limit, false);
    }
    public TextField(int column, int limit, boolean num){
        super(column);

        if(num)
            numericTextOnly();

        if(limit != -1 || limit >= 0)
            setDocument(new LimitedCharacter(limit));
    }

    public void setPlaceHolder(String text){
        putClientProperty("JTextField.placeholderText", text);
    }
    public void setImageLeft(String dirIco) {
        putClientProperty("JTextField.leadingIcon", Image.getIcon(dirIco));
    }

    public void setImageRight(String dirIco) {
        putClientProperty("JTextField.trailingIcon", Image.getIcon(dirIco));
    }

    public void showClearButton(boolean clearButton){
        putClientProperty("JTextField.showClearButton", Boolean.valueOf(clearButton));
    }
    public void numericTextOnly(){
        addKeyListener(new FieldNumeric());
    }

    public void borderNoError(){
        putClientProperty("JComponent.outline", "default");
    }

    public void borderError(){
        putClientProperty("JComponent.outline", "error");
    }
}
