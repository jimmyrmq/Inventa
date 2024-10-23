package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.principal.IData;
import com.djm.inventa.admin.vista.stock.StockRapidoGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ProductoListener implements ActionListener {
    private final String ID = PropiedadesSistema.getString("Producto.ID");
    private PanelProducto panelProducto;
    public ProductoListener(PanelProducto iPanel){
        panelProducto = iPanel;//Global.panelDesktop.getIPanel(ID);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CANCELAR".equals(action)){

            if(panelProducto != null) {
                if (panelProducto.isData()) {
                    panelProducto.clearForm();
                } else
                    Global.panelDesktop.cerrarVentana(ID);
            }
        }
        else if("GUARDAR_PRODUCTO".equals(action)){
            if(panelProducto != null){
                Producto producto = panelProducto.getData();

                if(producto.getID() == null){

                    Random random = new Random();
                    int randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    producto.setID(randomNumber);
                }

                Global.panelDesktop.setProductoList(producto);
                panelProducto.clearForm();
            }
        }
        else if("BUTTON_ELIMINAR".equals(action)){
            if(panelProducto != null){
                Global.panelDesktop.delProductoList(panelProducto.getData());
                panelProducto.clearForm();
            }
        }
        else if("AGREGAR_STOCK_RAPIDO".equals(action) || "EDITAR_STOCK_RAPIDO".equals(action) ){
            int cant = 0;
            boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);
            boolean agragar = "AGREGAR_STOCK_RAPIDO".equals(action);
            if (editar && this.panelProducto.isData()){
                cant = panelProducto.getData().getCantidadDisponible();
            }
            StockRapidoGUI stock = new StockRapidoGUI(cant);
            if(stock.isAcept()){
                panelProducto.setCantidadDisponible(stock.getCantidadEntrante(), agragar);
            }
        }
    }
}
