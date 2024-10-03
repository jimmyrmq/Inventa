package com.djm.inventa.admin.modelo;

import java.util.Date;
import java.util.List;

public class Factura {
    private Integer ID;
    private Date fecha;
    private Cliente cliente;
    private List<Producto> items;

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

    public List<Producto> getItems() {
        return items;
    }

    public void setItems(List<Producto> items) {
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
