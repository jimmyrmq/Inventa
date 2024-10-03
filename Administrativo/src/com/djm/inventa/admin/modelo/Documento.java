package com.djm.inventa.admin.modelo;

public class Documento {
    private Integer ID;
    private String numero;
    private Usuario usuario;
    private Cliente cliente;
    private Proveedor proveedor;
    private String fechaTransaccion;
    private String fechaVencimiento;
    private String fechaActualizacion;
    private TipoDocumento tipoDocumento;
    private Almacen almacen;
    private String nota;
    private Integer Estado;
    private Integer impuesto;
    private Double total;
    private Double subtotal;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

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

    public String getFechaEmision() {
        return fechaTransaccion;
    }

    public void setFechaEmision(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        this.Estado = estado;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "ID=" + ID +
                ", numero='" + numero + '\'' +
                ", usuario=" + usuario +
                ", cliente=" + cliente +
                ", proveedor=" + proveedor +
                ", fechaTransaccion='" + fechaTransaccion + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", tipoDocumento=" + tipoDocumento +
                ", almacen=" + almacen +
                ", nota='" + nota + '\'' +
                ", Estado=" + Estado +
                ", impuesto=" + impuesto +
                ", total=" + total +
                ", subtotal=" + subtotal +
                '}';
    }
}
