package com.djm.inventa.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoStock {

    private Integer id;
    private Integer usuarioId;
    private Integer productoId;
    private Integer almacenId;
    private BigDecimal cantidad;
    private byte tipo;
    private LocalDateTime fecha;
    private String observacion;
    private BigDecimal stockAnterior;
    private BigDecimal stockNuevo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo.getCodigo();
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(BigDecimal stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public BigDecimal getStockNuevo() {
        return stockNuevo;
    }

    public void setStockNuevo(BigDecimal stockNuevo) {
        this.stockNuevo = stockNuevo;
    }

    public void addCantidad(BigDecimal  cantidad){
        if(this.cantidad != null)
            this.cantidad = this.cantidad.add(cantidad);
        else
            this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /*@Override
    public String toString() {
        return String.valueOf(cantidad);
    }*/

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", productoId=" + productoId +
                ", almacenId=" + almacenId +
                ", cantidad=" + cantidad +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", observacion='" + observacion + '\'' +
                ", stockAnterior=" + stockAnterior +
                ", stockNuevo=" + stockNuevo +
                '}';
    }
}
