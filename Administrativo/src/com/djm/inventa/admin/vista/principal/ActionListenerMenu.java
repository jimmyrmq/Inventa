package com.djm.inventa.admin.vista.principal;

import com.djm.common.GlobalFrame;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.producto.DesktopProducto;
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
        if(action.equals("PRODUCTO")){
            IPanelDesktop iPanelDesktop = new DesktopProducto();
            Global.panelDesktop.addVentana(iPanelDesktop);
        }
        else if(action.equals("DARK") || action.equals("LIGHT")){
            try {
                FlatLaf skin = action.equals("DARK")?new FlatDarkLaf():new FlatLightLaf();
                UIManager.setLookAndFeel(skin);

                AparienciaLookFeel aparienciaLookFeel = action.equals("DARK")?AparienciaLookFeel.Dark:AparienciaLookFeel.Light;

                PropiedadesSistema.setPropiedad("Apariencia.lookandfeel",aparienciaLookFeel);

                //BorderUtil.setColTextTile(UIManager.getColor("Label.foreground"));
                SwingUtilities.updateComponentTreeUI(GlobalFrame.getInstance().getFrame());
                /*PanelTarea.getPanel().repaint();
                PanelTarea.getPanel().updateUI();
                PanelTarea.getPanel().revalidate();
                PanelTarea.update();*/
            } catch (Exception exc) {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exc2) {
                    exc.printStackTrace();
                }
            }
        }
        else if(action.equals("SALIR")){
            Salir.getInstance().exitSystem();
        }
    }
}
