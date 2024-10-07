package com.djm.inventa.admin.vista.principal;

public enum AparienciaLookFeel {
    Dark("DARK"),
    Light("LIGTH");
    private String descripcion;

    AparienciaLookFeel(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
