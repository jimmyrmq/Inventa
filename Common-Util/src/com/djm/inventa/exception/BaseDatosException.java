package com.djm.inventa.exception;

public class BaseDatosException extends Exception{
    private String titulo;

    public BaseDatosException(String message) {
        super(message);
    }

    public BaseDatosException(String titulo, String message) {
        super(message);
        this.titulo = titulo;
    }
}
