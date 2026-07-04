package com.djm.inventa.admin.compra;

import com.djm.inventa.admin.compra.exception.CompraExcepcion;
import com.djm.inventa.admin.compra.modelo.Compra;
import com.djm.inventa.admin.compra.vista.FrameRunDev;

import java.math.BigDecimal;

public class OrdenCompraApplication {
    public static void main(String[] args) {
        try {
            Compra compra = new Compra(1, 1, 15, 5, new BigDecimal("100.5"));
            if(compra.compraValida()){
                System.out.println("Compra compra realizada com sucesso "+compra);
            }else{
                System.out.println("Compra compra no válida");
            }
        } catch (CompraExcepcion exc) {
            System.out.println("Error al comprar compra: "+exc.getMessage());
        }

        FrameRunDev frameMain = new FrameRunDev();
    }
}