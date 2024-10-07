package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class DesktopProducto implements IPanelDesktop {
    private final String ID = "PRODUCTO";
    @Override
    public JInternalFrame getDesktopPane() {
        JInternalFrame internalFrame =new CrearFrameInterno(new PanelProducto(), CONSTANTS.LANG.getValue("producto.label.titulo"), "product.png",false, ID);

        internalFrame.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                internalFrame.requestFocusInWindow();
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {}
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                Global.panelDesktop.delVentana(ID);
            }
            @Override
            public void internalFrameIconified(InternalFrameEvent e) {}
            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {}
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {}
            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {}
        });

        return internalFrame;
    }

    @Override
    public String getID() {
        return ID;
    }
}
