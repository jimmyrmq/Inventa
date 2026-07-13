package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Proveedor;
import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.*;

public class DesktopProveedor implements IPanelDesktop<Proveedor> {
    private CrearFrameInterno internalFrame;
    private PanelProveedor panelProveedor;
    private final String ID = AppContext.getInstance().getString("Proveedor.ID");

    public DesktopProveedor(){
        panelProveedor = new PanelProveedor();

        this.internalFrame = new CrearFrameInterno(panelProveedor.getPanel(), CONSTANTS.LANG.getValue("proveedor.label.titulo"),null,false, ID);//, "16/product.png"
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
