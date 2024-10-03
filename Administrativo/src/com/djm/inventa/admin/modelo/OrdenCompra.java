package com.djm.inventa.admin.modelo;

import java.util.List;

public class OrdenCompra {
    private Documento documento;
    private List<DocumentoItem> ordenCompraItems;

    public OrdenCompra(){}

    public List<DocumentoItem> getDocumentoItems() {
        return ordenCompraItems;
    }

    public void setDocumentoItems(List<DocumentoItem> ordenCompraItems) {
        this.ordenCompraItems = ordenCompraItems;
    }
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "documento=" + documento +
                ", ordenCompraItems=" + ordenCompraItems +
                '}';
    }
}
