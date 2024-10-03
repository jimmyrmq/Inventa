package com.djm.inventa.admin.util;

import com.djm.ui.themes.global.GlobalUI;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class BorderUtil {

    public static Border getBorder(String title, int ar, int iz, int ab, int der){
        Color colTextTile = UIManager.getColor("Label.foreground");
        TitledBorder tbor= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(190,190,190)),title,TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.ABOVE_TOP,GlobalUI.getInstance().getTheme().getPanelUI().getFont(),colTextTile);//0,54,77

        javax.swing.border.Border border=BorderFactory.createCompoundBorder(tbor,BorderFactory.createEmptyBorder(ar,iz,ab,der));

        return border;
    }

    public static Border getBorder(String title){//
        javax.swing.border.Border border=getBorder(title,5,5,5,5);

        return border;
    }
}
