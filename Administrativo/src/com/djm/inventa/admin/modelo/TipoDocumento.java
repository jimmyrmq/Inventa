package com.djm.inventa.admin.modelo;

public class TipoDocumento {
    private Integer ID;
    private CategoriaDocumento categoriaDocumento;
    private String nombre;
    private String codigo;
    private Almacen almacen;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public CategoriaDocumento getCategoriaDocumento() {
        return categoriaDocumento;
    }

    public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
        this.categoriaDocumento = categoriaDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public String toString() {
        return "TipoDocumento{" +
                "ID=" + ID +
                ", categoriaDocumento=" + categoriaDocumento +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", almacen=" + almacen +
                '}';
    }
}
