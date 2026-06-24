package com.djm.inventa.admin.compra.modelo;


import com.djm.inventa.modelo.Almacen;
import com.djm.inventa.modelo.Cliente;
import com.djm.inventa.modelo.TipoDocumento;
import com.djm.inventa.modelo.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
/*
Datos que normalmente ve el usuario

Cabecera: Número de orden,Fecha,Proveedor,Estado,Condición de pago,Observaciones,Detalle

Producto: Cantidad,Precio unitario,Descuento,Impuesto,Subtotal

Totales

Subtotal
Descuento total
Impuestos
Total general
*/

public class DocumentoOC {
    private String numero;
    private Usuario usuario;
    private Cliente cliente;
    private Proveedor proveedor;
    private LocalDate fechaVencimiento;
    private TipoDocumento tipoDocumento;
    private Almacen almacen;
    private BigDecimal impuesto;
    private BigDecimal total;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private String observaciones;
    private List<ProductoItemOC> productoItem;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<ProductoItemOC> getProductoItem() {
        return productoItem;
    }

    public void setProductoItem(List<ProductoItemOC> productoItem) {
        this.productoItem = productoItem;
    }

    @Override
    public String toString() {
        return "Documento{" +
                ", numero='" + numero + '\'' +
                ", usuario=" + usuario +
                ", cliente=" + cliente +
                ", proveedor=" + proveedor +
                ", fechaVencimiento=" + fechaVencimiento +
                ", tipoDocumento=" + tipoDocumento +
                ", almacen=" + almacen +
                ", impuesto=" + impuesto +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", descuento=" + descuento +
                ", observaciones='" + observaciones + '\'' +
                ", productoItem=" + productoItem +
                '}';
    }
}
