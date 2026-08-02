package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.stock.model.StockProducto;
import com.djm.inventa.stock.model.TipoMovimiento;
import com.djm.inventa.stock.persistence.MovimientoStockDAO;
import com.djm.inventa.stock.persistence.StockProductoDAO;
import com.djm.inventa.stock.service.StockManager;
import com.djm.inventa.stock.view.StockRapidoGUI;
import com.djm.ui.component.OptionPane;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductoManager {

    public ProductoManager(){}

    public boolean eliminarProducto( Producto producto) {
        boolean eliminar = false;
        Integer id = producto.getID();
        if(id != null) {
            int n0 = OptionPane.questionYesOrKey( CONSTANTS.i18n.getValueMsgFormat("producto.mensaje.producto.eliminado.question", producto.getCodigo(),producto.getNombre()));

            if(n0 == OptionPane.OK) {
                ProductoDAO productoDB = new ProductoDAO();
                try {
                    boolean eliminado = productoDB.eliminarProducto(id);
                    if (eliminado) {
                        OptionPane.information(CONSTANTS.i18n.getValue("producto.mensaje.producto.eliminado.ok"));
                        //Global.panelDesktop.delProductoList(panelProducto.getValue());
                        eliminar = true;
                    } else
                        OptionPane.information(CONSTANTS.i18n.getValue("producto.mensaje.producto.eliminado.error"));
                } catch (ProductoException exc) {
                    OptionPane.error(CONSTANTS.i18n.getValue("producto.mensaje.eliminar.false") + "\n" + exc.getMessage());
                }
            }
        }else
            OptionPane.information( CONSTANTS.i18n.getValue("producto.mensaje.idnoencontrado"));

        return eliminar;
    }

    public BigDecimal agregarStock(Integer productoId, BigDecimal cantActual, BigDecimal stockMinimo, boolean editar, boolean agregar ) {

        BigDecimal stockNuevo = BigDecimal.ZERO;

        StockRapidoGUI stock = new StockRapidoGUI(cantActual);
        if (stock.isAcept()) {

            if (productoId == null) {
                OptionPane.information(CONSTANTS.i18n.getValue("producto.mensaje.stock.idnoencontrado"));
                return null;
            }

            StockManager stockManager = new StockManager();
            try {
                Integer almacen_id = 1;
                BigDecimal cantNueva = stock.getCantidadEntrante();

                BigDecimal stockBD = stockManager.obtenerStockActual(productoId, almacen_id);

                if (stockBD != null) {
                    if (stockBD.compareTo(cantNueva) == 0) {
                        stockNuevo = stockBD;
                    }
                    else if (cantActual.compareTo(stockBD) != 0) {
                        stockNuevo = stockBD;
                        throw new ProductoException(CONSTANTS.i18n.getValue("producto.stock.mensaje.err.stock_dif"));
                    }
                    else {
                        MovimientoStock movimientoStock = stockManager.obtenerMovimientoStock(cantNueva, cantActual, stockMinimo,
                                productoId, almacen_id, editar, agregar);

                        stockNuevo = movimientoStock.getStockNuevo();

                        //Registramos el movimiento
                        stockManager.registrarMovimientoStock(movimientoStock);
                    }
                }
            } catch (ProductoException exc) {
                OptionPane.error(CONSTANTS.i18n.getValue("stock.error.registro") + "\n" + exc.getMessage());
            }
        }

        return stockNuevo;
    }

    public Integer guardarProducto(Producto producto) {

        if(producto == null)
            return null;

        ProductoDAO productoDB = new ProductoDAO();
        Integer productoId = null;
        //panelProducto.getValue();

        try {

            productoDB.guardarProducto(producto);

            if(producto.getID() != null) {
                productoId = producto.getID();
            }
            else {
                OptionPane.information(CONSTANTS.i18n.getValue("producto.mensaje.stock.idnoencontrado"));
                return null;
            }

            if(!producto.isNoRequiereStock()) {
                guardarStock(producto);
            }

        } catch (ProductoException exc) {
            OptionPane.error(exc);
        }

        return productoId;
    }

    private void guardarStock(Producto producto)throws ProductoException {
        Integer almacen_id = 1;
        Integer productoID = producto.getID();
        BigDecimal cantidad = producto.getCantidadDisponible();

        StockProducto stockProducto = new StockProducto();

        StockProductoDAO stockProductoDAO = new StockProductoDAO();
        stockProducto.setProductoId(productoID);
        stockProducto.setAlmacenId(almacen_id);
        stockProducto.setCantidad(cantidad);
        stockProducto.setStockMinimo(producto.getStockMinimo());

        boolean isNuevo = !stockProductoDAO.existeProducto(productoID);

        if (isNuevo) {
            stockProductoDAO.nuevoRegistro(stockProducto);

            MovimientoStock movimientoStock = new MovimientoStock();
            movimientoStock.setStockAnterior(BigDecimal.ZERO);

            //Cambiar mas adelente
            movimientoStock.setUsuarioId(AppContext.getInstance().getInt("usuario.id"));
            movimientoStock.setAlmacenId(almacen_id);

            movimientoStock.setProductoId(productoID);
            movimientoStock.setFecha(LocalDateTime.now());
            movimientoStock.setCantidad(cantidad);
            movimientoStock.setStockNuevo(cantidad);
            movimientoStock.setTipo(TipoMovimiento.AGREGADO_RAPIDO);
            movimientoStock.setObservacion(TipoMovimiento.AGREGADO_RAPIDO.getDescripcion());

            MovimientoStockDAO movimientoStockDAO =  new MovimientoStockDAO();
            movimientoStockDAO.agregarStock(movimientoStock);

        }
        else {
            stockProductoDAO.actualizarCantidad(producto.getID(), producto.getCantidadDisponible(), producto.getStockMinimo(), producto.getStockMaximo());
        }
    }

    public void cancelarForm() {
        //panelProducto.onCancelar();
    }
}
