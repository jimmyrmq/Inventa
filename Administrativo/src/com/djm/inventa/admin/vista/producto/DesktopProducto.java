package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.IPanel;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;

import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;

public class DesktopProducto implements IPanelDesktop<Producto> {
    private JInternalFrame internalFrame;
    private IPanel<Producto> pProducto;
    public DesktopProducto(){

        pProducto = new PanelProducto();

        pProducto.actionListener(new ProductoListener(pProducto));

        this.internalFrame = new CrearFrameInterno(pProducto.getPanel(), CONSTANTS.LANG.getValue("producto.label.titulo"), "16/product.png",false, PropiedadesSistema.getString("Producto.ID"));

    }

    @Override
    public JInternalFrame getDesktopPane() { return internalFrame;}

    @Override
    public String getID() {
        return PropiedadesSistema.getString("Producto.ID");
    }

    @Override
    public void insertData(Producto producto) {
        pProducto.insertData(producto);
    }

    @Override
    public IPanel<Producto> getIPanel() {
        return pProducto;
    }
}
