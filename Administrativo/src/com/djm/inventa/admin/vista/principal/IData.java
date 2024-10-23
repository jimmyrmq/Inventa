package com.djm.inventa.admin.vista.principal;

public abstract class IData <E>{
    private E e;
    public abstract E getData();
    public boolean isData(){
        return e != null;
    }
    public void insertData(E e){
        this.e = e;
    }
}
