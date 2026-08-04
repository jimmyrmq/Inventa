package com.djm.inventa.modelo;

public class UnidadMedida {
    private Integer id;
    private String nombre;
    private String abreviacion;

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

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", abreviacion='" + abreviacion + '\'' +
                '}';
    }
}
