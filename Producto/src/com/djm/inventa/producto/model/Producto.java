package com.djm.inventa.producto.model;

import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.stock.model.MovimientoStock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Producto implements Cloneable{
    private Integer ID;
    private String codigo;
    private String codigoBarra;
    private String nombre;
    private String unidadMedida;
    private String modelo;
    private String serie;
    private Integer utilidad;
    private BigDecimal precioCosto;
    private BigDecimal precio1;
    private BigDecimal precio2;
    private BigDecimal precio3;
    private Integer cantMayor;//Cantidad para aplicar precio mayorista
    private Boolean precioIncluyeImpuesto;
    private Boolean disponible = true;
    private BigDecimal cantidadDisponible;//Cantidad de stock
    private BigDecimal stockCritico;
    private Boolean noRequiereStock = true;//Es un servicio
    private Boolean reqAprobPrecioEspecial = true;//Requiere aprobacion para precio especial
    private Categoria categoria;
    private Marca marca;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime  fechaCreacion;
    private MovimientoStock movimientoStock;
    private String nota;

    public Producto() { }

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

    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }

    public BigDecimal getPrecio1() {
        return precio1;
    }

    public void setPrecio1(BigDecimal precio1) {
        this.precio1 = precio1;
    }

    public BigDecimal getPrecio2() {
        return precio2;
    }

    public void setPrecio2(BigDecimal precio2) {
        this.precio2 = precio2;
    }

    public BigDecimal getPrecio3() {
        return precio3;
    }

    public void setPrecio3(BigDecimal precio3) {
        this.precio3 = precio3;
    }

    public Integer getCantMayor() {
        return cantMayor;
    }

    public void setCantMayor(Integer cantMayor) {
        this.cantMayor = cantMayor;
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

    public MovimientoStock getStock(){
        return this.movimientoStock;
    }
    public void setStock(MovimientoStock movimientoStock){
        this.movimientoStock = movimientoStock;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public BigDecimal getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(BigDecimal cantidadStock) {
        this.cantidadDisponible = cantidadStock;
    }

    public BigDecimal getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(BigDecimal stockCritico) {
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

    public String getNota() {
        return this.nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Boolean isReqAprobPrecioEspecial() {
        return reqAprobPrecioEspecial;
    }

    public void setReqAprobPrecioEspecial(Boolean reqAprobPrecioEspecial) {
        this.reqAprobPrecioEspecial = reqAprobPrecioEspecial;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ID=" + ID +
                ", codigo='" + codigo + '\'' +
                ", codigoBarra='" + codigoBarra + '\'' +
                ", nombre='" + nombre + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", modelo='" + modelo + '\'' +
                ", serie='" + serie + '\'' +
                ", utilidad=" + utilidad +
                ", precioCosto=" + precioCosto +
                ", precio1=" + precio1 +
                ", precio2=" + precio2 +
                ", precio3=" + precio3 +
                ", cantMayor=" + cantMayor +
                ", precioIncluyeImpuesto=" + precioIncluyeImpuesto +
                ", disponible=" + disponible +
                ", cantidadDisponible=" + cantidadDisponible +
                ", stockCritico=" + stockCritico +
                ", noRequiereStock=" + noRequiereStock +
                ", reqAprobPrecioEspecial=" + reqAprobPrecioEspecial +
                ", categoria=" + categoria +
                ", marca=" + marca +
                ", fechaActualizacion=" + fechaActualizacion +
                ", fechaCreado=" + fechaCreacion +
                ", nota='" + nota + '\'' +
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
