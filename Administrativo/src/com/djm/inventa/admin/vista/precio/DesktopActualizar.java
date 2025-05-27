package com.djm.inventa.admin.vista.precio;

import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;

public class DesktopActualizar implements IPanelDesktop<Object> {
    private JInternalFrame internalFrame;
    private PanelActualizarPrecio actualizarPrecio;

    public DesktopActualizar(){
         actualizarPrecio = new PanelActualizarPrecio();
        this.internalFrame = new CrearFrameInterno(actualizarPrecio.getPanel(), CONSTANTS.LANG.getValue("producto.actualizarprecio.titulo"), "16/product.png",false, PropiedadesSistema.getString("ActualizarPrecio.ID"), true);

    }
    @Override
    public JInternalFrame getDesktopPane() {
        return internalFrame;

    }

    @Override
    public String getID() {
        return PropiedadesSistema.getString("ActualizarPrecio.ID");
    }

    @Override
    public void insertData(Object o) {

    }
}
