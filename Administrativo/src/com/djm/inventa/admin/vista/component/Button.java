package com.djm.inventa.admin.vista.component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Color;

public class Button extends JButton {
    public Button(ImageIcon imageIcon){
        super(imageIcon);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setBackground(UIManager.getColor("CheckBox.background"));
        setForeground(Color.BLACK);
        setHideActionText(true);
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.BOTTOM);
    }

    @Override
    public void updateUI(){
        super.updateUI();
        setBackground(UIManager.getColor("CheckBox.background"));//Panel.background
    }
}
