package com.djm.inventa.admin.vista.ordencompra;

import com.djm.inventa.admin.modelo.OrdenCompra;
import com.djm.inventa.admin.vista.ipanel.IPanelDataAction;

import javax.swing.*;
import java.awt.*;

public class PanelOrdenCompra extends IPanelDataAction<OrdenCompra> {
    private JPanel panelPrincipal;
    private JTextField tNumero, tFecha;

    public PanelOrdenCompra(){
        panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false);

    }

    private JPanel pDatos(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        return panel;
    }

    @Override
    public OrdenCompra getData() {
        return null;
    }

    @Override
    public void actionEsc() {

    }

    @Override
    public JPanel getPanel() {
        return panelPrincipal;
    }

    @Override
    public void clearForm() {

    }
}
