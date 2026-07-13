package com.djm.inventa.modelo;

public class Almacen extends RegistroSimple{
    @Override
    public String toString() {
        return "Almacen{" +
                "ID = "+getID()+" "+
                "nombre = \'"+getNombre()+"\'}";
    }
}
