package com.djm.inventa.admin.compra.modelo;

import java.time.LocalDate;

public class InformacionEntregaOC {
    private LocalDate fecha;
    private String direccion;
    private String deposito;
    private String metodoEnvio;
    private String observaciones;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "InformacionEntregaOC{" +
                "fecha=" + fecha +
                ", direccion='" + direccion + '\'' +
                ", deposito='" + deposito + '\'' +
                ", metodoEnvio='" + metodoEnvio + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
