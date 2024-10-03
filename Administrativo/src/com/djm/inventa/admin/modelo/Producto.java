package com.djm.inventa.admin.modelo;

import java.util.Date;

public class Producto implements Cloneable{
    private Integer ID;
    private String codigo;
    private String codigoBarra;
    private String nombre;
    private String unidadMedida;
    private String modelo;
    private String serie;
    private Integer utilidad;
    private Double precioCosto;
    private Double precio1;
    private Double precio2;
    private Double precio3;
    private Boolean precioIncluyeImpuesto;
    private Boolean disponible = true;
    private Integer cantidadDisponible;//Cantidad de stock
    private Integer stockCritico;
    private Boolean noRequiereStock = true;//Es un servicio
    private Categoria categoria;
    private Marca marca;
    private Proveedor proveedor;
    private Date fechaActualizacion;
    private Date fechaCreado;
    //private Stock stock;
    private String nota;

    public Producto() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Integer utilidad) {
        this.utilidad = utilidad;
    }

    public Double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(Double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public Double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(Double precio1) {
        this.precio1 = precio1;
    }

    public Double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(Double precio2) {
        this.precio2 = precio2;
    }

    public Double getPrecio3() {
        return precio3;
    }

    public void setPrecio3(Double precio3) {
        this.precio3 = precio3;
    }

    public Boolean isPrecioIncluyeImpuesto() {
        return precioIncluyeImpuesto;
    }

    public void setPrecioIncluyeImpuesto(Boolean precioIncluyeImpuesto) {
        this.precioIncluyeImpuesto = precioIncluyeImpuesto;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    /*public Stock getStock(){
        return this.stock;
    }
    public void setStock(Stock stock){
        this.stock = stock;
    }*/

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadStock) {
        this.cantidadDisponible = cantidadStock;
    }

    public Integer getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(Integer stockCritico) {
        this.stockCritico = stockCritico;
    }

    public Boolean isNoRequiereStock() {
        return noRequiereStock;
    }

    public void setNoRequiereStock(Boolean noRequiereStock) {
        this.noRequiereStock = noRequiereStock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getNota() {
        return this.nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ID=" + ID +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", modelo='" + modelo + '\'' +
                ", serie='" + serie + '\'' +
                ", precioCosto=" + precioCosto +
                ", utilidad=" + utilidad +
                ", precio1=" + precio1 +
                ", precio2=" + precio2 +
                ", precio3=" + precio3 +
                ", excento=" + precioIncluyeImpuesto +
                ", disponible=" + disponible +
                ", cantidadDisponible=" + cantidadDisponible +
                //", stock=" + stock +
                ", stockCritico=" + stockCritico +
                ", noRequiereStock=" + noRequiereStock +
                ", categoria=" + categoria +
                ", marca=" + marca +
                ", fechaActualizacion=" + fechaActualizacion +
                ", fechaCreado=" + fechaCreado +
                ", nota=" + nota +
                '}';
    }

    @Override
    public Object clone() {
        try {
            return super.clone(); // Llama al método clone() de la clase Object
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Nunca debe ocurrir porque estamos implementando Cloneable
        }
    }
}
