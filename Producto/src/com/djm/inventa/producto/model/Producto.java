package com.djm.inventa.producto.model;

import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.modelo.ProductoVariante;
import com.djm.inventa.modelo.UnidadMedida;
import com.djm.inventa.stock.model.MovimientoStock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Producto implements Cloneable{
    private Integer ID;
    private String codigo;
    private String nombre;
    private UnidadMedida unidadMedida;
    private String modelo;
    private Boolean precioIncluyeImpuesto;
    private Boolean disponible = true;
    private Boolean noRequiereStock = true;//Es un servicio
    private Boolean movimientoNegativo = true;//Requiere aprobacion para precio especial
    private Categoria categoria;
    private Marca marca;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime  fechaCreacion;
    private MovimientoStock movimientoStock;
    private String nota;
    private Boolean eliminado = false;
    private List<ProductoVariante> variantes;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public Boolean isNoRequiereStock() {
        return noRequiereStock;
    }

    public void setNoRequiereStock(Boolean noRequiereStock) {
        this.noRequiereStock = noRequiereStock;
    }

    public Boolean isMovimientoNegativo() {
        return movimientoNegativo;
    }

    public void setMovimientoNegativo(Boolean movimientoNegativo) {
        this.movimientoNegativo = movimientoNegativo;
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

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Boolean isEliminado() {
        return eliminado;
    }

    public List<ProductoVariante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<ProductoVariante> variantes) {
        this.variantes = variantes;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ID=" + ID +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precioIncluyeImpuesto=" + precioIncluyeImpuesto +
                ", disponible=" + disponible +
                ", noRequiereStock=" + noRequiereStock +
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
