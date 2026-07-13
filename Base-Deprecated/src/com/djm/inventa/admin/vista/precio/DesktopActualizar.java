package com.djm.inventa.admin.vista.precio;

import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.core.AppContext;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;

public class DesktopActualizar implements IPanelDesktop<Object> {
    private JInternalFrame internalFrame;
    private PanelActualizarPrecio actualizarPrecio;

    public DesktopActualizar(){
         actualizarPrecio = new PanelActualizarPrecio();
        this.internalFrame = new CrearFrameInterno(actualizarPrecio.getPanel(), CONSTANTS.LANG.getValue("producto.actualizarprecio.titulo"), null,false, AppContext.getInstance().getString("ActualizarPrecio.ID"), true);//"16/product.png"

    }
    @Override
    public JInternalFrame getDesktopPane() {
        return internalFrame;

    }

    @Override
    public String getID() {
        return AppContext.getInstance().getString("ActualizarPrecio.ID");
    }

    @Override
    public void insertData(Object o) {

    }
}
