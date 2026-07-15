package com.djm.inventa.stock.service;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.stock.model.TipoMovimiento;
import com.djm.inventa.stock.persistence.MovimientoStockDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockManager {

    public StockManager(){
    }

    public MovimientoStock registroStock(BigDecimal cantNueva, BigDecimal cantActual,
                                         Integer productoID, boolean editar, boolean agregar){
         BigDecimal stockNuevo= BigDecimal.ZERO;

        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setStockAnterior(cantActual);

        //Cambiar mas adelente
        movimientoStock.setUsuarioId(AppContext.getInstance().getInt("usuario.id"));
        movimientoStock.setAlmacenId(1);

        movimientoStock.setProductoId(productoID);

        if(editar){
            int resultado = cantActual.compareTo(cantNueva);
            TipoMovimiento tipoMovimiento = resultado > 0 ? TipoMovimiento.AJUSTE_POSITIVO: TipoMovimiento.AJUSTE_NEGATIVO;
            movimientoStock.setTipo(tipoMovimiento);

            if(TipoMovimiento.AJUSTE_POSITIVO == tipoMovimiento){
                movimientoStock.setObservacion(CONSTANTS.i18n.getValue("stock.info.observacion.ajuste_positivo"));
            }
            else if(TipoMovimiento.AJUSTE_NEGATIVO == tipoMovimiento){
                movimientoStock.setObservacion(CONSTANTS.i18n.getValue("stock.info.observacion.ajuste_negativo"));
            }
            stockNuevo = cantNueva;
        }
        else if(agregar){
            stockNuevo = cantActual.add(cantNueva);
            movimientoStock.setObservacion(CONSTANTS.i18n.getValue("stock.info.observacion.agregado_rapido"));
            movimientoStock.setTipo(TipoMovimiento.AGREGADO_RAPIDO.getCodigo());
        }

        movimientoStock.setFecha(LocalDateTime.now());
        movimientoStock.setCantidad(cantNueva);
        movimientoStock.setStockNuevo(stockNuevo);

        return movimientoStock;
    }

    public void registrarMovimientoStock(MovimientoStock movimientoStock) throws ProductoException{
        try {
            MovimientoStockDAO movimientoStockDAO = new MovimientoStockDAO();
            movimientoStockDAO.agregarStock(movimientoStock);
        }
        catch (ProductoException ex) {
            throw new ProductoException(ex.getMessage());
        }
    }
}
