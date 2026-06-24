package com.djm.inventa.admin.compra.modelo;

public enum EstadoOC {
    BORRADOR("BORRADOR"),
    ENVIADA("ENVIADA"),
    PENDIENTE("Pendiente"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada"),
    CANCELADA("Cancelada"),
    COMPLETADA("Completada");

    private final String descripcion;

    EstadoOC(String descripcion) {
        this.descripcion =  descripcion;
    }

    public String getValue() {
        return descripcion;
    }
}
