package com.djm.inventa.ui.component.renderer;

import com.djm.ui.component.ITipoEtiqueta;

import java.awt.Color;

public enum TipoEtiqueta implements ITipoEtiqueta {

    NONE("N/A",new Color(128, 128, 128)),
    Disponible("etiquetacomponent.disponible",new Color(82, 176, 48)),
    NoDisponible("etiquetacomponent.no_disponible",new Color(239, 9, 9)),
    StockCritico("etiquetacomponent.stock_critico",new Color(225, 43, 43)),
    SinStock("etiquetacomponent.sin_stock",new Color(239, 9, 9)),
    Servicio("etiquetacomponent.servicio",new Color(236, 89, 9)),
    OrdenCompra("documento.ordencompra",new Color(9, 136, 239)),
    Inventario("documento.inventario",new Color(82, 176, 48)),
    Recibido("documento.recibido",new Color(82, 176, 48)),
    Solicitado("documento.solicitado",new Color(82, 176, 48)),
    Abiero("documento.abierto",new Color(76, 176, 48)),
    Deuda("documento.deuda",new Color(176, 48, 48)),
    Pagado("documento.pagado",new Color(176, 82, 48)),
    Cerrado("documento.cerrado",new Color(239, 9, 9));

    private String descripcion;
    private Color color;

    TipoEtiqueta(String title, Color color){
        this.descripcion = title;
        this.color = color;
    }

    public String getTitle() {
        return descripcion;
    }

    public Color getColor() {
        return color;
    }
}
