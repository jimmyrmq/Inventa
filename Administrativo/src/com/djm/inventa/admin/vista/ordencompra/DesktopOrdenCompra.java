package com.djm.inventa.admin.vista.ordencompra;

import com.djm.inventa.admin.modelo.OrdenCompra;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IPanelDesktop;

import javax.swing.*;

public class DesktopOrdenCompra implements IPanelDesktop<OrdenCompra> {
    private JInternalFrame internalFrame;
    private PanelOrdenCompra panelOrdenProducto;
    private final String ID = PropiedadesSistema.getString("OrdenCompra.ID");

    @Override
    public JInternalFrame getDesktopPane() {
        panelOrdenProducto = new PanelOrdenCompra();

        this.internalFrame = new CrearFrameInterno(panelOrdenProducto.getPanel(), CONSTANTS.LANG.getValue("ordencompra.label.titulo"), "16/product.png",false, ID);

        return this.internalFrame;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void insertData(OrdenCompra ordenCompra) {
        panelOrdenProducto.insertData(ordenCompra);
    }
}
