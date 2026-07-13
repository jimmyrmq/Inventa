package com.djm.inventa.admin.util;

//Exception son excepciones que obligatoriamente tienen que poner el try catch
//RuntimeException no es obligatorio
public class CheckDataException extends Exception{//RuntimeException{//
    public CheckDataException(){
        super();
    }
    public CheckDataException(String message){
        super(message);
    }
}
