package com.djm.inventa.admin.modelo;

public class Promocion {
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Promocion{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
