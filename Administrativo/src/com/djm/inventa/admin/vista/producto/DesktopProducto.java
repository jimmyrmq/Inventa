package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class DesktopProducto implements IPanelDesktop {
    private JInternalFrame internalFrame;
    private JPanel toolBar;
    private JPanel producto;
    public DesktopProducto(){

        producto = new PanelProducto();

        this.internalFrame =new CrearFrameInterno(producto, CONSTANTS.LANG.getValue("producto.label.titulo"), "product.png",false, PropiedadesSistema.getString("Producto.ID"));
        this.toolBar = new ToolBarProducto();
    }

    @Override
    public JPanel getToolBar(){
        return toolBar;
    }

    @Override
    public JInternalFrame getDesktopPane() { return internalFrame;}

    @Override
    public JPanel getPanel() {
        return producto;
    }

    @Override
    public String getID() {
        return PropiedadesSistema.getString("Producto.ID");
    }

    @Override
    public String getTitulo() {
        return CONSTANTS.LANG.getValue("producto.label.titulo");
    }
}
