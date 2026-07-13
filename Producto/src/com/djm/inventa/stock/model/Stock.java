package com.djm.inventa.stock.model;

import java.time.LocalDateTime;

public class Stock {
    private Integer ID;
    private Integer productoID;
    private Integer almacenID;
    private Integer cantidad;
    private LocalDateTime fecha;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getProductoID() {
        return productoID;
    }

    public void setProductoID(Integer productoID) {
        this.productoID = productoID;
    }

    public Integer getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Integer almacenID) {
        this.almacenID = almacenID;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void addCantidad(Integer  cantidad){
        if(this.cantidad != null)
            this.cantidad += cantidad;
        else
            this.cantidad = cantidad;
    }

    public void setCantidad(Integer cantidad) {
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
                "ID=" + ID +
                ", productoID=" + productoID +
                ", almacenID=" + almacenID +
                ", cantidad=" + cantidad +
                '}';
    }
}
