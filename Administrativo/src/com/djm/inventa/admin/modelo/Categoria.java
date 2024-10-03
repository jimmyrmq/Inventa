package com.djm.inventa.admin.modelo;

import java.awt.Color;

public class Categoria extends RegistroSimple{
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getNombre() ;
    }
}
