package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.principal.Global;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoListener implements ActionListener {

    public ProductoListener(){
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CERRAR".equals(action)){
            Global.panelDesktop.cerrarVentana(PropiedadesSistema.getString("Producto.ID"));
        }
        else if("BUTTON_GUARDAR".equals(action)){

        }
    }
}
