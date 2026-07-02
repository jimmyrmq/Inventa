package com.djm.inventa.admin.compra.modelo;

import java.time.LocalDate;

public class RecepcionOrdenCompra {

    private Long id;
    private OrdenCompra ordenCompra;
    private String numeroRemito;
    private LocalDate fechaRecepcion;
    private String observaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getNumeroRemito() {
        return numeroRemito;
    }

    public void setNumeroRemito(String numeroRemito) {
        this.numeroRemito = numeroRemito;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "RecepcionCompraOC{" +
                "id=" + id +
                ", ordenCompra=" + ordenCompra +
                ", numeroRemito='" + numeroRemito + '\'' +
                ", fechaRecepcion=" + fechaRecepcion +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
