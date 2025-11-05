package com.djm.inventa.admin.vista.precio;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.principal.Global;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrecioListener implements ActionListener {

    private final String ID = PropiedadesSistema.getString("ActualizarPrecio.ID");
    private PanelActualizarPrecio panelActualizarPrecio;

    public PrecioListener(PanelActualizarPrecio iPanel){
        panelActualizarPrecio = iPanel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if(action.equals("BUTTON_CANCELAR")){

            if(panelActualizarPrecio != null) {
                Global.panelDesktop.cerrarVentana(ID);
            }
        }
    }
}
