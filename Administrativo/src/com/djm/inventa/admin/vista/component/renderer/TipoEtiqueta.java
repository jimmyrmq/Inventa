package com.djm.inventa.admin.vista.component.renderer;

import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.ITipoEtiqueta;

import java.awt.Color;

public enum TipoEtiqueta implements ITipoEtiqueta {

    NONE("N/A",new Color(128, 128, 128)),
    Disponible(CONSTANTS.LANG.getValue("etiquetacomponent.disponible"),new Color(82, 176, 48)),
    NoDisponible(CONSTANTS.LANG.getValue("etiquetacomponent.no_disponible"),new Color(239, 9, 9)),
    StockCritico(CONSTANTS.LANG.getValue("etiquetacomponent.stock_critico"),new Color(225, 43, 43)),
    SinStock(CONSTANTS.LANG.getValue("etiquetacomponent.sin_stock"),new Color(239, 9, 9)),
    Servicio(CONSTANTS.LANG.getValue("etiquetacomponent.servicio"),new Color(236, 89, 9)),
    OrdenCompra(CONSTANTS.LANG.getValue("documento.ordencompra"),new Color(9, 136, 239)),
    Inventario(CONSTANTS.LANG.getValue("documento.inventario"),new Color(82, 176, 48)),
    Recibido(CONSTANTS.LANG.getValue("documento.recibido"),new Color(82, 176, 48)),
    Solicitado(CONSTANTS.LANG.getValue("documento.solicitado"),new Color(82, 176, 48)),
    Abiero(CONSTANTS.LANG.getValue("documento.abierto"),new Color(76, 176, 48)),
    Deuda(CONSTANTS.LANG.getValue("documento.deuda"),new Color(176, 48, 48)),
    Pagado(CONSTANTS.LANG.getValue("documento.pagado"),new Color(176, 82, 48)),
    Cerrado(CONSTANTS.LANG.getValue("documento.cerrado"),new Color(239, 9, 9));

    private String title;
    private Color color;
    TipoEtiqueta(String title, Color color){
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public Color getColor() {
        return color;
    }
}
