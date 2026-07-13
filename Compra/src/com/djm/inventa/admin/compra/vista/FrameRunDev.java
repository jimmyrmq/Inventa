package com.djm.inventa.admin.compra.vista;

import com.djm.inventa.admin.compra.vista.proveedor.PanelProveedor;
import com.djm.inventa.core.i18n.I18nManager;
import com.djm.ui.LayoutPanel;

import javax.swing.*;
import java.awt.*;

public class FrameRunDev {
    public FrameRunDev(){
        I18nManager.initForDev("orden_compra", "compra");

        JFrame frame = new JFrame("Orden de Compra");

        Container content= frame.getContentPane();
        content.setLayout(new GridBagLayout());
        content.add(new PanelProveedor().getPanel(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(600, 500);
        frame.setMinimumSize(dimension);
        //frame.setPreferredSize(dimension);
        //frame.setSize(dimension);
        frame.pack();
        frame.setDefaultLookAndFeelDecorated(true);
        //frame.setBackground(new Color(158,162,144));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
