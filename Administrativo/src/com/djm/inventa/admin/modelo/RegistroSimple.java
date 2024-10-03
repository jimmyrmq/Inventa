package com.djm.inventa.admin.modelo;

public class RegistroSimple implements SimpleData {

    private Integer ID;
    private String nombre;
    @Override
    public Integer getID() {
        return ID;
    }
    @Override
    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
