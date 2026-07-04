package com.djm.inventa.producto.vista;


import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelProducto{

    private JPanel panelProducto = new JPanel();

    public JPanel getPanel() {
        panelProducto.add(new JLabel("Producto"));
        return panelProducto;
    }
}
