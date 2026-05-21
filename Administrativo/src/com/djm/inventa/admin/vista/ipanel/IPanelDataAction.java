package com.djm.inventa.admin.vista.ipanel;

public abstract class IPanelDataAction<E> extends IPanelAction implements IPanel{
    private E e;

    public abstract E getDataForm();

    public E getValue(){
        return e;
    }

    public boolean isData(){
        return e != null;
    }

    public void init(){
        e = null;
    }

    public void insertData(E e){
        this.e = e;
    }
}
