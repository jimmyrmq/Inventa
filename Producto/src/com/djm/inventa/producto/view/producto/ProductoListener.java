package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.stock.view.StockRapidoGUI;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.ui.component.OptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoListener implements ActionListener {
    private PanelManagerProducto panelManagerProducto;
    private IUIManager iuiManager;

    public ProductoListener(PanelManagerProducto iPanel){
        IUIManager value = (IUIManager) AppContext.getInstance().get("iuimagener");
        this.iuiManager =  (value instanceof IUIManager s) ? s : null;

        panelManagerProducto = iPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CANCELAR".equals(action)){
            if(panelManagerProducto != null) {
                //panelProducto.onCancelar();
                if (panelManagerProducto.hasFormData()) {
                    int n0 = OptionPane.questionYesOrKey( CONSTANTS.i18n.getValue("producto.mensaje.confirmar_cancelar"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//
                    if(n0 == OptionPane.OK) {
                        panelManagerProducto.clearForm();
                    }
                }
                else if (iuiManager != null) {
                    iuiManager.closeView(AppContext.getInstance().getString("Producto.ID"));
                }
            }
        }
        else if("GUARDAR_PRODUCTO".equals(action)){
            if(panelManagerProducto != null && panelManagerProducto.hasFormData()) {

                ProductoDAO productoDB = new ProductoDAO();

                //panelProducto.getValue();

                Producto producto = panelManagerProducto.getDataForm();
                try {
                    productoDB.guardarProducto(producto);
                } catch (ProductoException exc) {
                    OptionPane.error(exc);
                }

                panelManagerProducto.clearForm();
            }
            else {
                OptionPane.information( CONSTANTS.i18n.getValue("producto.mensaje.campos_incompletos"));
            }
        }
        else if("BUTTON_ELIMINAR".equals(action)){
            if(panelManagerProducto != null){
                //Global.panelDesktop.delProductoList(panelProducto.getValue());
                panelManagerProducto.clearForm();
            }
        }
        else if("AGREGAR_STOCK_RAPIDO".equals(action) || "EDITAR_STOCK_RAPIDO".equals(action) ){
            int cant = 0;
            boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);
            boolean agragar = "AGREGAR_STOCK_RAPIDO".equals(action);
            if (editar && this.panelManagerProducto.isData()){
                cant = panelManagerProducto.getValue().getCantidadDisponible();
            }
            StockRapidoGUI stock = new StockRapidoGUI(cant);
            if(stock.isAcept()){
                panelManagerProducto.setCantidadDisponible(stock.getCantidadEntrante(), agragar);
            }
        }
    }
}
