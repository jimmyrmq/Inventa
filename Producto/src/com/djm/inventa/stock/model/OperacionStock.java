package com.djm.inventa.stock.model;
/*
 * Ejemplo 1: Compra
 * Factor = 1
 *   5 × 1 = 5
 *
 * Luego:
 *   20 + 5 = 25
 *Ejemplo 2: Venta
 *Factor = -1
 *   5 × (-1) = -5
 * Luego:
 *   20 + (-5) = 15
 Observa que siempre haces un add(), pero el signo de la cantidad cambia.
 */

public enum OperacionStock {
    SUMA (1),
    RESTA (-1),
    AJUSTE (0);

    private final int factor;

    OperacionStock(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return factor;
    }
}
