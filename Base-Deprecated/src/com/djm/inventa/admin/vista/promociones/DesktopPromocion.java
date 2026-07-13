package com.djm.inventa.admin.vista.promociones;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Promocion;
import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;

public class DesktopPromocion implements IPanelDesktop<Promocion>{
    private CrearFrameInterno internalFrame;
    private PanelPromocion panelPromocion;
    private final String ID = AppContext.getInstance().getString("Promocion.ID");
    public DesktopPromocion(){
        panelPromocion = new PanelPromocion();

        this.internalFrame = new CrearFrameInterno(panelPromocion.getPanel(), CONSTANTS.LANG.getValue("promocion.label.titulo"), null,false, ID);//"16/prom.png"
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
