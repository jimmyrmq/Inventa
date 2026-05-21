package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.admin.modelo.Proveedor;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IPanelDesktop;

import javax.swing.*;

public class DesktopProveedor implements IPanelDesktop<Proveedor> {
    private CrearFrameInterno internalFrame;
    private PanelProveedor panelProveedor;
    private final String ID = PropiedadesSistema.getString("Proveedor.ID");

    public DesktopProveedor(){
        panelProveedor = new PanelProveedor();

        this.internalFrame = new CrearFrameInterno(panelProveedor.getPanel(), CONSTANTS.LANG.getValue("proveedor.label.titulo"), "16/product.png",false, ID);
        this.internalFrame.setDimY(10);

    }

    @Override
    public JInternalFrame getDesktopPane() {
        return this.internalFrame;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void insertData(Proveedor ordenCompra) {
        panelProveedor.insertData(ordenCompra);
    }
}
