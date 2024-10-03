package com.djm.inventa.admin.vista.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerMenu implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if(action.equals("CONFIGURACION")){
        }
        else if(action.equals("SALIR")){
            Salir.getInstance().exitSystem();
        }
    }
}
