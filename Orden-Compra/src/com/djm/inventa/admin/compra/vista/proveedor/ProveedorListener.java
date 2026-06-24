package com.djm.inventa.admin.compra.vista.proveedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProveedorListener  implements ActionListener {
    private PanelProveedor panelProveedor;

    public ProveedorListener(PanelProveedor iPanel){
        panelProveedor = iPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "BUTTON_CANCELAR":
                if (panelProveedor != null) {
                    panelProveedor.onCancelar();
                }
                break;

            case "GUARDAR_PROVEEDOR":
                if (panelProveedor != null) {
                    panelProveedor.onGuardar();
                }
                break;
        }
    }
}
