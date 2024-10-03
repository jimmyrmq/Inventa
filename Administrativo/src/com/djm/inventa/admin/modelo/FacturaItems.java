package com.djm.inventa.admin.modelo;

public class FacturaItems {
    private Integer ID;
    private Producto producto;
    private Double cant;
    private Double precio;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getCantidad() {
        return cant;
    }

    public void setCant(Double cant) {
        this.cant = cant;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "FacturaItems{" +
                "ID=" + ID +
                ", producto=" + producto +
                ", cant=" + cant +
                ", precio=" + precio +
                '}';
    }
}
