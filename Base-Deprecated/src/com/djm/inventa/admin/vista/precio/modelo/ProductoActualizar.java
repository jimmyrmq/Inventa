package com.djm.inventa.admin.vista.precio.modelo;

public class ProductoActualizar {
    private Integer ID;
    private String codigo;
    private String nombre;
    private Double precio1;
    private Double precio1Ant;
    private Double precio2;
    private Double precio2Ant;
    private Double precio3;
    private Double precio3Ant;
    private Boolean seleccionado;

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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(Double precio1) {
        this.precio1 = precio1;
    }

    public Double getPrecio1Ant() {
        return precio1Ant;
    }

    public void setPrecio1Ant(Double precio1Ant) {
        this.precio1Ant = precio1Ant;
    }

    public Double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(Double precio2) {
        this.precio2 = precio2;
    }

    public Double getPrecio2Ant() {
        return precio2Ant;
    }

    public void setPrecio2Ant(Double precio2Ant) {
        this.precio2Ant = precio2Ant;
    }

    public Double getPrecio3() {
        return precio3;
    }

    public void setPrecio3(Double precio3) {
        this.precio3 = precio3;
    }

    public Double getPrecio3Ant() {
        return precio3Ant;
    }

    public void setPrecio3Ant(Double precio3Ant) {
        this.precio3Ant = precio3Ant;
    }

    public Boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public String toString() {
        return "ProductoActualizar{" +
                "ID=" + ID +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio1=" + precio1 +
                ", precio1Ant=" + precio1Ant +
                ", precio2=" + precio2 +
                ", precio2Ant=" + precio2Ant +
                ", precio3=" + precio3 +
                ", precio3Ant=" + precio3Ant +
                ", seleccionado=" + seleccionado +
                '}';
    }
}
