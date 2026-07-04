package com.djm.inventa.producto.exception;

public class ProductoException extends Exception {
    public ProductoException(String message) {
        super(message);
    }

    public ProductoException(String message, Throwable cause) {
        super(message, cause);
    }
}
