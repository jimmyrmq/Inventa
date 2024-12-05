package com.djm.inventa.admin.vista.promociones;

import com.djm.inventa.admin.modelo.Promocion;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;

public class DesktopPromocion implements IPanelDesktop<Promocion>{
    private CrearFrameInterno internalFrame;
    private PanelPromocion panelPromocion;
    private final String ID = PropiedadesSistema.getString("Promocion.ID");
    public DesktopPromocion(){
        panelPromocion = new PanelPromocion();

        this.internalFrame = new CrearFrameInterno(panelPromocion.getPanel(), CONSTANTS.LANG.getValue("promocion.label.titulo"), "16/prom.png",false, ID);
        this.internalFrame.setDimY(20);

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
    public void insertData(Promocion promocion) {}
}
