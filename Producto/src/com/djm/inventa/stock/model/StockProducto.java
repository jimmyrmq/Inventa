package com.djm.inventa.stock.model;

import java.math.BigDecimal;

public class StockProducto {

    private Integer id;
    private Integer productoId;
    private Integer almacenId;
    private BigDecimal cantidad;
    private BigDecimal stockMinimo;
    private BigDecimal stockMaximo;

    public StockProducto() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Integer almacenId) {
        this.almacenId = almacenId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(BigDecimal stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public BigDecimal getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(BigDecimal stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    @Override
    public String toString() {
        return "StockProducto{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", almacenId=" + almacenId +
                ", cantidad=" + cantidad +
                ", stockMinimo=" + stockMinimo +
                ", stockMaximo=" + stockMaximo +
                '}';
    }
}
