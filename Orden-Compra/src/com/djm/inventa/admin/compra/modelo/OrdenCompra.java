package com.djm.inventa.admin.compra.modelo;

import java.time.LocalDateTime;

public class OrdenCompra {
    private Long ID;
    private DocumentoOC documento;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCreacion;
    private EstadoOC estadoOC = EstadoOC.BORRADOR;
    private InformacionEntregaOC informacionEntregaOC;
    private CondicionesComercialesOC condicionesComercialesOC;

    public OrdenCompra(){}

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public DocumentoOC getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoOC documento) {
        this.documento = documento;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoOC getEstadoOC() {
        return estadoOC;
    }

    public void setEstadoOC(EstadoOC estadoOC) {
        this.estadoOC = estadoOC;
    }

    public InformacionEntregaOC getInformacionEntregaOC() {
        return informacionEntregaOC;
    }

    public void setInformacionEntregaOC(InformacionEntregaOC informacionEntregaOC) {
        this.informacionEntregaOC = informacionEntregaOC;
    }

    public CondicionesComercialesOC getCondicionesComercialesOC() {
        return condicionesComercialesOC;
    }

    public void setCondicionesComercialesOC(CondicionesComercialesOC condicionesComercialesOC) {
        this.condicionesComercialesOC = condicionesComercialesOC;
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "ID=" + ID +
                ", documento=" + documento +
                ", estadoOC=" + estadoOC +
                ", informacionEntregaOC=" + informacionEntregaOC +
                ", condicionesComercialesOC=" + condicionesComercialesOC +
                '}';
    }
}
