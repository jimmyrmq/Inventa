package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.vista.principal.PanelDesktop;

public class Global {
    static {
        panelTitulo = new PanelTitulo();
        panelDesktop = new PanelDesktop();
    }
    public static PanelDesktop panelDesktop;
    public static PanelTitulo panelTitulo;

    private Global(){}


}
