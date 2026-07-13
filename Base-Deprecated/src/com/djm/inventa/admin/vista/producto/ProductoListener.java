package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Producto;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.stock.StockRapidoGUI;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.ui.component.OptionPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ProductoListener implements ActionListener {
    private final String ID = AppContext.getInstance().getString("Producto.ID");
    private PanelProducto panelProducto;
    private IUIManager iuiManager;

    public ProductoListener(IUIManager iuiManager,
            PanelProducto iPanel){
        this.iuiManager = iuiManager;
        panelProducto = iPanel;////Global.panelDesktop.getIPanel(ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CANCELAR".equals(action)){

            if(panelProducto != null) {
                if (panelProducto.isData()) {
                    int n0 = OptionPane.questionYesOrKey( CONSTANTS.LANG.getValue("producto.mensaje.confirmar_cancelar"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//
                    if(n0 == OptionPane.OK) {
                        panelProducto.clearForm();
                    }
                } else
                    iuiManager.closeView(ID);
            }
        }
        else if("GUARDAR_PRODUCTO".equals(action)){
            if(panelProducto != null && panelProducto.isData()) {
                //panelProducto.getValue();

                Producto producto = panelProducto.getDataForm();

                if(producto.getID() == null){

                    Random random = new Random();
                    int randomNumber = random.nextInt(1000) + 1; // Gernera un número ente 1 y 1000
                    producto.setID(randomNumber);
                }

                //Global.panelDesktop.setProductoList(producto);
                panelProducto.clearForm();
            }
            else {
                OptionPane.information( CONSTANTS.LANG.getValue("producto.mensaje.campos_incompletos"));
            }
        }
        else if("BUTTON_ELIMINAR".equals(action)){
            if(panelProducto != null){
                //Global.panelDesktop.delProductoList(panelProducto.getValue());
                panelProducto.clearForm();
            }
        }
        else if("AGREGAR_STOCK_RAPIDO".equals(action) || "EDITAR_STOCK_RAPIDO".equals(action) ){
            int cant = 0;
            boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);
            boolean agragar = "AGREGAR_STOCK_RAPIDO".equals(action);
            if (editar && this.panelProducto.isData()){
                cant = panelProducto.getValue().getCantidadDisponible();
            }
            StockRapidoGUI stock = new StockRapidoGUI(cant);
            if(stock.isAcept()){
                panelProducto.setCantidadDisponible(stock.getCantidadEntrante(), agragar);
            }
        }
    }
}
