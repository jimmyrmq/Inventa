package com.djm.inventa.modelo;

import java.math.BigDecimal;
import java.util.List;

public class ProductoVariante {
    private Integer id;
    private String SKU;
    private String codigoBarra;
    private String urlImagen;
    private List<AtributoValor> listAtributos;

    private Boolean disponible;

    private BigDecimal cantidadStock;
    private BigDecimal cantidadMinina;
    private BigDecimal cantidadMaxima;
    private BigDecimal cantidadMayor;

    private List<PrecioProducto> precioProductos;

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public List<AtributoValor> getListAtributos() {
        return listAtributos;
    }

    public void setListAtributos(List<AtributoValor> listAtributos) {
        this.listAtributos = listAtributos;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public BigDecimal getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(BigDecimal cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public BigDecimal getCantidadMinina() {
        return cantidadMinina;
    }

    public void setCantidadMinina(BigDecimal cantidadMinina) {
        this.cantidadMinina = cantidadMinina;
    }

    public BigDecimal getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(BigDecimal cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public BigDecimal getCantidadMayor() {
        return cantidadMayor;
    }

    public void setCantidadMayor(BigDecimal cantidadMayor) {
        this.cantidadMayor = cantidadMayor;
    }

    public List<PrecioProducto> getPrecioProductos() {
        return precioProductos;
    }

    public void setPrecioProductos(List<PrecioProducto> precioProductos) {
        this.precioProductos = precioProductos;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return "ProductoVariante{" +
                "id=" + id +
                ", SKU='" + SKU + '\'' +
                ", codigoBarra='" + codigoBarra + '\'' +
                ", disponible=" + disponible +
                ", cantidadStock=" + cantidadStock +
                ", cantidadMinina=" + cantidadMinina +
                ", cantidadMaxima=" + cantidadMaxima +
                ", cantidadMayor=" + cantidadMayor +
                ", precioProductos=" + precioProductos +
                '}';
    }
}
