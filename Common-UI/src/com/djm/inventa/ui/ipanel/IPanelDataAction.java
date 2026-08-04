package com.djm.inventa.ui.ipanel;

import java.awt.Dimension;

public abstract class IPanelDataAction<E> extends IPanelAction{
    private E e;
    private Dimension userDimension = null;

    public abstract E getDataForm();

    public E getValue() {
        return e;
    }

    public boolean isData(){
        return e != null;
    }

    public void insertData(E e){
        this.e = e;
    }

    public Dimension getSize() {
        return userDimension;
    }

    public Dimension setSize(int width, int height) {
        userDimension = new Dimension(width,height);
        return userDimension;
    }
}