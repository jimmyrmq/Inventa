package com.djm.inventa.admin.compra.exception;

public class CompraExcepcion extends RuntimeException {

    public CompraExcepcion(String message) {
        super(message);
    }

    public CompraExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

    public CompraExcepcion(Throwable cause) {
        super(cause);
    }
}
