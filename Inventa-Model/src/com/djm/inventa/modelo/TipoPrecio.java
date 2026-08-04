package com.djm.inventa.modelo;

public class TipoPrecio {
    private Integer id;
    private String nombre;

    public Integer getID() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoPrecio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
