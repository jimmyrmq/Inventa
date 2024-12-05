package com.djm.inventa.admin.vista.ipanel;

public abstract class IPanelDataAction<E> extends IPanelAction implements IPanel{
    private E e;
    public abstract E getData();
    public boolean isData(){
        return e != null;
    }
    public void insertData(E e){
        this.e = e;
    }
}
