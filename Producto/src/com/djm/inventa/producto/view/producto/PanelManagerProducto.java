package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.ipanel.IPanelDataAction;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class PanelManagerProducto extends IPanelDataAction<Producto> {
    private PanelProducto  panelProducto;

    public PanelManagerProducto() {
        this.panelProducto = new PanelProducto(this);
    }

    @Override
    public Producto getDataForm() {
        // Obtiene un producto vacío o el existente del manager
        Producto producto = super.isData() ? super.getValue() : new Producto();

        // Llena el producto con los datos del formulario UI
        return panelProducto.getDataForm(producto);
    }

    @Override
    public void addButtonEsc() {
        panelProducto.addButtonEsc();
    }

    @Override
    public ImageIcon getIcon() {
        return IconManager.getIcon(getClass().getResource("/icons/product.png"));
    }

    @Override
    public String getId() {
        //Creamos el ID que identifica en todo el sistema la ventana
        final String id = "ID.Producto.Registrar";
        AppContext.getInstance().setPropiedad("Producto.ID", id);

        return id;
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
        panelProducto.clearForm();
        // Limpia los datos almacenados en el manager
        super.insertData(null);
    }

    /**
     * Verifica si hay datos en el panel de la UI
     */
    public boolean hasFormData() {
        return panelProducto.hasFormData();
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        cerrarEsc(panelProducto.getButtonCancelar());
        panelProducto.setActionListener(actionListener);
    }

    @Override
    public void init() {
        panelProducto.loadData();
    }

    public void setCantidadDisponible(int cantidadDisponible, boolean agregar) {
        panelProducto.setCantidadDisponible(cantidadDisponible, agregar);
    }
}
