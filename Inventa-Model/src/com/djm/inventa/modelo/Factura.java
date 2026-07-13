package com.djm.inventa.modelo;

import java.util.Date;
import java.util.List;

public class Factura {
    private Integer ID;
    private Date fecha;
    private Cliente cliente;
    private List<FacturaItems> items;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<FacturaItems> getItems() {
        return items;
    }

    public void setItems(List<FacturaItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "ID=" + ID +
                ", fecha=" + fecha +
                ", cliente=" + cliente +
                ", items=" + items +
                '}';
    }
}
