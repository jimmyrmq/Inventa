package com.djm.inventa.admin;

import com.djm.common.GlobalFrame;
import com.djm.util.Image;
import com.djm.inventa.admin.vista.principal.VentanaPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.awt.Font;
import java.util.Enumeration;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            run();
        });
    }
    private static void run(){
        Image.init(App.class,"icon");
        lookAndFeel();
        VentanaPrincipal frame = new VentanaPrincipal();

        GlobalFrame.getInstance().setFrame(frame);
        frame.setVisible(true);
    }

    private static void lookAndFeel(){

        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.setLookAndFeel(new FlatLightLaf());

            //Cambiamos la letra
            Font font = UIManager.getFont("Label.font");
            Font font2 = font.deriveFont(11f);
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            // Enumerar las propiedades
            Enumeration<Object> keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                //Object value = defaults.get(key);
                if(String.valueOf(key).indexOf("font")!=-1) {
                    UIManager.put(key, font2);
                }
            }
            //SwingUtilities.updateComponentTreeUI(GlobalFrame.getInstance().getFrame());
        } catch (Exception exc) {
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exc2) {
                exc.printStackTrace();
            }
        }
    }
}