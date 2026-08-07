package com.djm.inventa.modelo;

public class AtributoValor {
    private Integer id;
    private Atributo atributo;
    private String valor;

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;/*"AtributoValor{" +
                "id=" + id +
                ", atributo=" + atributo +
                ", valor='" + valor + '\'' +
                '}';*/
    }
}
