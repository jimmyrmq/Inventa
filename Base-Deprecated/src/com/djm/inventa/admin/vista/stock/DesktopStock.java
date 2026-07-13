package com.djm.inventa.admin.vista.stock;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Producto;
import com.djm.inventa.ui.ipanel.CrearFrameInterno;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.JInternalFrame;
import java.awt.Dimension;

public class DesktopStock implements IPanelDesktop<Producto> {
    private final String ID =  AppContext.getInstance().getString("Stock.ID");
    private JInternalFrame internalFrame;
    private PanelStock panelStock;

    public DesktopStock(){
        panelStock = new PanelStock();

        this.internalFrame = new CrearFrameInterno(panelStock, CONSTANTS.LANG.getValue("stock.label.titulo"), null,false, ID);//"16/product.png"
        this.internalFrame.setResizable(true);

        Dimension dim = this.internalFrame.getPreferredSize();
        dim.height += 20;
        dim.width += 10;

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
