package com.djm.inventa.modelo;

public class CategoriaDocumento extends RegistroSimple{
    @Override
    public String toString() {
        return "CategoriaDocumento{" +
                "ID = "+getID()+" "+
                "nombre = \'"+getNombre()+"\'}";
    }
}
