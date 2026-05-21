package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.admin.modelo.Proveedor;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.ui.component.OptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProveedorListener  implements ActionListener {
    private final String ID = PropiedadesSistema.getString("Proveedor.ID");
    private PanelProveedor panelProveedor;

    public ProveedorListener(PanelProveedor iPanel){
        panelProveedor = iPanel;//Global.panelDesktop.getIPanel(ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "BUTTON_CANCELAR":
                if (panelProveedor != null) {
                    if (panelProveedor.isData()) {
                        int n0 = OptionPane.questionYesOrKey( CONSTANTS.LANG.getValue("proveedor.mensaje.confirmar_cancelar"));
                        if (n0 == OptionPane.OK) {
                            panelProveedor.clearForm();
                        }
                    } else
                        Global.panelDesktop.cerrarVentana(ID);
                }
                break;

            case "GUARDAR_PROVEEDOR":
                Proveedor proveedor = panelProveedor.getDataForm();
                System.out.println("Proveedor guardado: " + proveedor.infoData());
                
                // Actualizar o agregar el proveedor en la tabla
                panelProveedor.updateOrAddProveedorInTable(proveedor);
                
                panelProveedor.clearForm();

                break;
        }
    }
}
