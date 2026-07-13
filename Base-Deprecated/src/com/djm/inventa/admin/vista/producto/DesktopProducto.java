package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Producto;
import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;

public class DesktopProducto implements IPanelDesktop<Producto> {
    private JInternalFrame internalFrame;
    private PanelProducto pProducto;
    private final String ID = AppContext.getInstance().getString("Producto.ID");

    public DesktopProducto(){

        pProducto = new PanelProducto();

        this.internalFrame = new CrearFrameInterno(pProducto.getPanel(), CONSTANTS.LANG.getValue("producto.label.titulo"), null,false, ID);//"16/product.png"

    }

    @Override
    public JInternalFrame getDesktopPane() { return internalFrame;}

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void insertData(Producto producto) {
        pProducto.insertData(producto);
    }

}
