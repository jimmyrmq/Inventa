package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.ui.component.OptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ProductoListener implements ActionListener{

    private PanelManagerProducto panelManagerProducto;
    private IUIManager iuiManager;
    private final ProductoManager productoManager;

    public ProductoListener(PanelManagerProducto iPanel){
        IUIManager value = (IUIManager) AppContext.getInstance().get("iuimagener");
        this.iuiManager =  (value instanceof IUIManager s) ? s : null;

        this.productoManager = new ProductoManager();
        this.panelManagerProducto = iPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CANCELAR".equals(action)){
            if (panelManagerProducto != null && panelManagerProducto.hasFormData()) {
                int n0 = OptionPane.questionYesOrKey( CONSTANTS.i18n.getValue("producto.mensaje.confirmar_cancelar"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//
                if(n0 == OptionPane.OK) {
                    //productoManager.cancelarForm();
                    panelManagerProducto.clearForm();
                }
            }
            else if (iuiManager != null) {
                iuiManager.closeView(AppContext.getInstance().getString("Producto.ID"));
            }
        }
        else if("GUARDAR_PRODUCTO".equals(action)){
            if(panelManagerProducto != null && panelManagerProducto.hasFormData()) {
                Integer productoId = this.productoManager.guardarProducto(
                        panelManagerProducto.getDataForm());

                if(productoId != null) {
                    //panelManagerProducto.setIdProducto(productoId);
                    panelManagerProducto.clearForm();
                }
            }
            else {
                OptionPane.information( CONSTANTS.i18n.getValue("producto.mensaje.campos_incompletos"));
            }
        }
        else if("BUTTON_ELIMINAR".equals(action)){
            if(panelManagerProducto != null) {
                if(this.productoManager.eliminarProducto(
                        panelManagerProducto.getDataForm())){
                    panelManagerProducto.clearForm();
                }
            }
        }
        else if("AGREGAR_STOCK_RAPIDO".equals(action) || "EDITAR_STOCK_RAPIDO".equals(action) ){

            boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);
            boolean agregar = "AGREGAR_STOCK_RAPIDO".equals(action);

            BigDecimal cantActual;
            BigDecimal stockMinimo;
            if (editar && this.panelManagerProducto.isData()) {
                cantActual = panelManagerProducto.getValue().getCantidadDisponible();
                stockMinimo = panelManagerProducto.getValue().getStockMinimo();
            } else {
                cantActual = panelManagerProducto.getDataForm().getCantidadDisponible();
                stockMinimo = panelManagerProducto.getDataForm().getStockMinimo();
            }

            Integer productoId = null;

            if (this.panelManagerProducto.isData()) {
                productoId = panelManagerProducto.getValue().getID();
            }

            BigDecimal stockNuevo = this.productoManager.agregarStock(
                    productoId,
                    cantActual,
                    stockMinimo,
                    editar,
                    agregar);
            if(stockNuevo != null) {
                panelManagerProducto.setCantidadDisponible(stockNuevo);//, agregar);
            }
        }
    }
}
