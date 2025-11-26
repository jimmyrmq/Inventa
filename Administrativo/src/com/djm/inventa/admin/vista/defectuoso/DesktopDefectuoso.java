package com.djm.inventa.admin.vista.defectuoso;

import com.djm.inventa.admin.modelo.Promocion;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IPanelDesktop;

import javax.swing.*;

public class DesktopDefectuoso implements IPanelDesktop<Void> {
    private CrearFrameInterno internalFrame;
    private PanelDefectuoso panelDefectuoso;
    private final String ID = PropiedadesSistema.getString("Defectuoso.ID");

    private DesktopDefectuoso(){
        panelDefectuoso = new PanelDefectuoso();

        this.internalFrame = new CrearFrameInterno(panelDefectuoso.getPanel(), CONSTANTS.LANG.getValue("defectuoso.label.titulo"), "16/prom.png",false, ID);
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