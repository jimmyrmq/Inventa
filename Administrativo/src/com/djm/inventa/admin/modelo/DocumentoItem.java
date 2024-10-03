package com.djm.inventa.admin.modelo;

public class DocumentoItem {
    private Integer ID;
    private Documento documento;
    private Producto producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double precioNeto;
    private Double total;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(Double precioNeto) {
        this.precioNeto = precioNeto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "DocumentoItem{" +
                "ID=" + ID +
                ", documento=" + documento +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioBruto=" + precioUnitario +
                ", precioNeto=" + precioNeto +
                ", total=" + total +
                '}';
    }
}
