package com.djm.inventa.producto.vista;

import com.djm.inventa.modelo.Producto;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.inventa.ui.ipanel.IUIManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelManagerProducto extends IPanelDataAction<Producto> {
    private PanelProducto  panelProducto;

    public PanelManagerProducto() {
        this.panelProducto = new PanelProducto();
    }

    @Override
    public Producto getDataForm() {
        return null;
    }

    @Override
    public void actionEsc() {

    }

    @Override
    public ImageIcon getIcon() {
        return IconManager.getIcon(getClass().getResource("/icons/product.png"));
    }

    @Override
    public String getId() {
        return "Producto";
    }

    @Override
    public String getTitle() {
        return "Producto";
    }

    @Override
    public JPanel getPanel() {
        return panelProducto.getPanel();
    }

    @Override
    public void clearForm() {

    }

    @Override
    public void setUIManager(IUIManager uiManager) {

    }
}
