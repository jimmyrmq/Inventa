package com.djm.inventa.admin.modelo;

public class Proveedor {
    private Integer ID;
    private String codigo;
    private String razonSocial;
    private String nombreContacto;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String correo;
    private String fechaRegistro;
    private String nota;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return razonSocial;
    }

    public void setNombre(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String infoData() {
        return "Proveedor{" +
                "ID=" + ID +
                ", codigo='" + codigo + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono1='" + telefono1 + '\'' +
                ", telefono2='" + telefono2 + '\'' +
                ", correo='" + correo + '\'' +
                ", nota='" + nota + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
    @Override
    public String toString() {
        String str = codigo + " - " + razonSocial;
        if(codigo == null)
            str = razonSocial;
        return  str;
    }
}
