package com.djm.inventa.admin.compra.modelo;

public class CondicionesComercialesOC {
    private String condicionPago;// de pago (Contado, 30 días, 60 días, etc.)
    private String moneda;
    private String tipoCambio; //si aplica
    private String condiciones;// especiales
    private String garantias;//Garantías acordadas

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getGarantias() {
        return garantias;
    }

    public void setGarantias(String garantias) {
        this.garantias = garantias;
    }

    @Override
    public String toString() {
        return "CondicionesComercialesOC{" +
                "condicionPago='" + condicionPago + '\'' +
                ", moneda='" + moneda + '\'' +
                ", tipoCambio='" + tipoCambio + '\'' +
                ", condiciones='" + condiciones + '\'' +
                ", garantias='" + garantias + '\'' +
                '}';
    }
}
