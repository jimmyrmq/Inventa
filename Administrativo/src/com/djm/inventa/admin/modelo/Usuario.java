package com.djm.inventa.admin.modelo;

public class Usuario {
    private Integer ID;
    private String nombre;
    private String email;
    private Integer nivelAcceso;
    private String clave;
    private Boolean habilitado;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(Integer nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", nivelAcceso=" + nivelAcceso +
                ", clave='" + clave + '\'' +
                ", habilitado=" + habilitado +
                '}';
    }
}
