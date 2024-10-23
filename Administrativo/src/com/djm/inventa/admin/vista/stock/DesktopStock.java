package com.djm.inventa.admin.vista.stock;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.CrearFrameInterno;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;

import javax.swing.JInternalFrame;
import java.awt.Dimension;

public class DesktopStock implements IPanelDesktop<Producto> {
    private final String ID =  PropiedadesSistema.getString("Stock.ID");
    private JInternalFrame internalFrame;
    private PanelStock panelStock;
    public DesktopStock(){
        panelStock = new PanelStock();

        this.internalFrame = new CrearFrameInterno(panelStock, CONSTANTS.LANG.getValue("stock.label.titulo"), "16/product.png",false, ID);
        this.internalFrame.setResizable(true);

        Dimension dim = this.internalFrame.getPreferredSize();
        dim.height += 20;

        this.internalFrame.setPreferredSize(dim);
        this.internalFrame.setSize(dim);
        this.internalFrame.setMinimumSize(dim);

    }
    @Override
    public JInternalFrame getDesktopPane() {
        return this.internalFrame;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void insertData(Producto producto) {
        panelStock.insertData(producto);
    }
}
