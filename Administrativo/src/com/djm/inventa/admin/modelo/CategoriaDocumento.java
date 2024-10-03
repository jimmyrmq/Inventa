package com.djm.inventa.admin.modelo;

public class CategoriaDocumento extends RegistroSimple{
    @Override
    public String toString() {
        return "CategoriaDocumento{" +
                "ID = "+getID()+" "+
                "nombre = \'"+getNombre()+"\'}";
    }
}
