package com.djm.inventa.producto.model;

import com.djm.inventa.stock.model.Stock;

public class ProductoStock {
    private Producto producto;
    private Stock stock;

    public ProductoStock(){}

    public ProductoStock(Producto producto, Stock stock) {
        this.producto = producto;
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
