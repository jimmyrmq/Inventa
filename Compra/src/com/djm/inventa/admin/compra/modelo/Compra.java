package com.djm.inventa.admin.compra.modelo;

import com.djm.inventa.admin.compra.exception.CompraExcepcion;

import java.math.BigDecimal;

public record Compra (int idCompra, int idProveedor, int idProducto, int cantidad, BigDecimal precio) {

    public Compra{
        if(cantidad < 0){
            throw new CompraExcepcion("La cantidad no puede ser negativa");
        }
    }

    public boolean compraValida(){
        return cantidad > 0 && precio.compareTo(BigDecimal.ZERO) > 0;
    }
}
