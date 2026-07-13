package com.djm.inventa.admin.vista.defectuoso;

import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.core.AppContext;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.*;

public class DesktopDefectuoso implements IPanelDesktop<Void> {
    private CrearFrameInterno internalFrame;
    private PanelDefectuoso panelDefectuoso;
    private final String ID = AppContext.getInstance().getString("Defectuoso.ID");

    private DesktopDefectuoso(){
        panelDefectuoso = new PanelDefectuoso();

        this.internalFrame = new CrearFrameInterno(panelDefectuoso.getPanel(), CONSTANTS.LANG.getValue("defectuoso.label.titulo"), null,false, ID);//"16/prom.png"
        this.internalFrame.setDimY(20);
    }


    @Override
    public JInternalFrame getDesktopPane() {
        return internalFrame;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void insertData(Void unused) {}
}