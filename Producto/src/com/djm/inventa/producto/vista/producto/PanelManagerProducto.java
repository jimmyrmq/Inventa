package com.djm.inventa.producto.vista.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.modelo.Producto;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.inventa.ui.ipanel.IUIManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelManagerProducto extends IPanelDataAction<Producto> {
    private PanelProducto  panelProducto;
    private IUIManager iuiManager;

    public PanelManagerProducto() {
        this.panelProducto = new PanelProducto();
    }

    @Override
    public Producto getDataForm() {
        // Obtiene un producto vacío o el existente del manager
        Producto producto = super.isData() ? super.getValue() : new Producto();

        // Llena el producto con los datos del formulario UI
        return panelProducto.getDataForm(producto);
    }

    @Override
    public void actionEsc() {
        panelProducto.actionEsc();
    }

    @Override
    public ImageIcon getIcon() {
        return IconManager.getIcon(getClass().getResource("/icons/product.png"));
    }

    @Override
    public String getId() {
        final String id = "Producto_V1";
        AppContext.getInstance().setPropiedad("Producto.ID", id);
        return id;
    }

    @Override
    public String getTitle() {
        return "Producto";
    }

    @Override
    public JPanel getPanel() {
        cerrarEsc(panelProducto.getButtonCancelar());

        return panelProducto.getPanel();
    }

    @Override
    public void clearForm() {
        panelProducto.clearForm();
        // Limpia los datos almacenados en el manager
        super.insertData(null);
    }

    /**
     * Inserta un producto en el formulario (llamado desde PanelProducto)
     */
    public void insertarProductoEnFormulario(Producto producto) {
        // Almacena el producto en el manager (en la clase padre)
        super.insertData(producto);

        // Actualiza la UI del panel
        panelProducto.insertData(producto);
    }

    /**
     * Verifica si hay datos en el panel de la UI
     */
    public boolean hayDatosEnFormulario() {
        return super.isData() || panelProducto.isData();
    }

    @Override
    public void setUIManager(IUIManager uiManager) {
        this.iuiManager = uiManager;
        //Crear la accion aqui
        ProductoListener productoListener = new ProductoListener(iuiManager, this);
        panelProducto.setActionListener(productoListener);
    }

    public void setCantidadDisponible(int cantidadDisponible, boolean agregar) {
        panelProducto.setCantidadDisponible(cantidadDisponible, agregar);
    }
}
