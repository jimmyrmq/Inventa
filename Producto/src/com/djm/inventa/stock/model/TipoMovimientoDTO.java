package com.djm.inventa.stock.model;

public class TipoMovimientoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Byte factor_stock;// 1 SUMA, -1 RESTA, 0 Ajuste
    //private String language_key;

    public Integer getId() {
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Byte getFactor_stock() {
        return this.factor_stock;
    }

    public void setFactor_stock(Byte factor_stock) {
        this.factor_stock = factor_stock;
    }

   /* public String getLanguage_key() {
        return this.language_key;
    }

    public void setLanguage_key(String language_key) {
        this.language_key = language_key;
    }*/

    @Override
    public String toString() {
        return this.nombre;
    }
}
