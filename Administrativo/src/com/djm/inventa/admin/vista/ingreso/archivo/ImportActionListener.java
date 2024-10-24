package com.djm.inventa.admin.vista.ingreso.archivo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("SELECT_FILE".equals(command)){

        }
    }
}
