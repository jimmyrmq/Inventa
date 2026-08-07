package com.djm.inventa.modelo;

import java.awt.Color;

public class Categoria extends RegistroSimple{
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getNombre() ;
    }
}
