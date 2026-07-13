package com.djm.inventa.appshell.ui.menu;

import com.djm.inventa.appshell.startup.CONSTANTS;
import com.djm.inventa.core.AppContext;
import com.djm.inventa.ui.AparienciaLookFeel;
import com.djm.ui.GlobalFrame;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerMenu implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if(action.equals("DARK") || action.equals("LIGHT")){
            try {

                FlatLaf skin = action.equals("DARK")?new FlatDarkLaf():new FlatLightLaf();
                UIManager.setLookAndFeel(skin);

                Thread thread = new Thread(()->{
                    AparienciaLookFeel aparienciaLookFeel = action.equals("DARK")?AparienciaLookFeel.Dark:AparienciaLookFeel.Light;
                    CONSTANTS.CONFIG.setValue("lookandfeel",aparienciaLookFeel.getDescripcion());
                    AppContext.getInstance().setPropiedad("Apariencia.lookandfeel",aparienciaLookFeel);
                });
                thread.start();


                SwingUtilities.updateComponentTreeUI(GlobalFrame.getInstance().getFrame());

            } catch (Exception exc) {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exc2) {
                    exc.printStackTrace();
                }
            }
        }
    }
}
