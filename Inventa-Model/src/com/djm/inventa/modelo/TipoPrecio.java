package com.djm.inventa.modelo;

public class TipoPrecio {
    private Integer id;
    private String nombre;

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
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
        return nombre;/*"TipoPrecio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';*/
    }
}
