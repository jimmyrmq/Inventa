package com.djm.inventa.producto.model;

import com.djm.inventa.stock.model.MovimientoStock;

public class ProductoStock {
    private Producto producto;
    private MovimientoStock movimientoStock;

    public ProductoStock(){}

    public ProductoStock(Producto producto, MovimientoStock movimientoStock) {
        this.producto = producto;
        this.movimientoStock = movimientoStock;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public MovimientoStock getStock() {
        return movimientoStock;
    }

    public void setStock(MovimientoStock movimientoStock) {
        this.movimientoStock = movimientoStock;
    }
}
