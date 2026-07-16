package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.stock.model.StockProducto;
import com.djm.inventa.stock.persistence.StockProductoDAO;
import com.djm.inventa.stock.service.StockManager;
import com.djm.inventa.stock.view.StockRapidoGUI;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.ui.component.OptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

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

                    if(!producto.isNoRequiereStock()) {
                        if(producto.getID() != null) {
                            StockProducto stockProducto = new StockProducto();

                            StockProductoDAO stockProductoDAO = new StockProductoDAO();
                            stockProducto.setProductoId(producto.getID());
                            stockProducto.setAlmacenId(1);
                            stockProducto.setCantidad(producto.getCantidadDisponible());
                            stockProducto.setStockMinimo(producto.getStockCritico());

                            boolean isNuevo = !stockProductoDAO.existeProducto(producto.getID());

                            if (isNuevo) {
                                stockProductoDAO.nuevoRegistro(stockProducto);
                            } else {
                                stockProductoDAO.actualizarCantidad(producto.getID(), producto.getCantidadDisponible());
                            }
                        }else
                            OptionPane.information( CONSTANTS.i18n.getValue("producto.mensaje.idnoencontrado"));
                    }

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
            boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);
            boolean agregar = "AGREGAR_STOCK_RAPIDO".equals(action);

            BigDecimal cantActual;

            if (editar && this.panelManagerProducto.isData()){
                cantActual = panelManagerProducto.getValue().getCantidadDisponible();
            }
            else
                cantActual = panelManagerProducto.getDataForm().getCantidadDisponible();

            StockRapidoGUI stock = new StockRapidoGUI(cantActual);
            if(stock.isAcept()){
                Integer productoId = null;

                if (this.panelManagerProducto.isData()) {
                    productoId = panelManagerProducto.getValue().getID();
                }

                if(productoId == null){
                    OptionPane.information( CONSTANTS.i18n.getValue("producto.mensaje.idnoencontrado"));
                    return;
                }

                BigDecimal stockNuevo = BigDecimal.ZERO;

                StockManager stockManager = new StockManager();
                try {
                    Integer almacen_id = 1;
                    BigDecimal cantNueva = stock.getCantidadEntrante();

                    BigDecimal stockBD = stockManager.obtenerStockActual(productoId, almacen_id);

                    if (stockBD != null ){
                        if (stockBD.compareTo(cantNueva) == 0) {
                            stockNuevo = stockBD;
                        }
                        else if (cantActual.compareTo(stockBD) != 0) {
                            stockNuevo = stockBD;
                            throw new ProductoException(CONSTANTS.i18n.getValue("producto.stock.mensaje.err.stock_dif"));
                        }
                        else{
                            MovimientoStock movimientoStock = stockManager.obtenerMovimientoStock(cantNueva, cantActual,
                                    productoId, almacen_id, editar, agregar);

                            stockNuevo = movimientoStock.getStockNuevo();

                            //Registramos el movimiento
                            stockManager.registrarMovimientoStock(movimientoStock);
                        }
                    }
                }catch (ProductoException exc) {
                    OptionPane.error(CONSTANTS.i18n.getValue("stock.error.registro") + "\n" + exc.getMessage());
                }
                finally {
                    panelManagerProducto.setCantidadDisponible(stockNuevo);//, agregar);
                }
            }
        }
    }
}
