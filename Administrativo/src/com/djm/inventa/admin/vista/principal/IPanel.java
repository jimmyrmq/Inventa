package com.djm.inventa.admin.vista.principal;

import javax.swing.JPanel;
import java.awt.event.ActionListener;

public abstract class IPanel<E> {
    private E e;
    public abstract JPanel getPanel();
    public abstract E getData();
    public boolean isData(){
        return e != null;
    }
    public abstract void actionListener(ActionListener al);
    public void insertData(E e){
        this.e = e;
    }

    public abstract void clearForm();
}
