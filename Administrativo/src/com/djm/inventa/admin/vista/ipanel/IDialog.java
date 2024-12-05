package com.djm.inventa.admin.vista.ipanel;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.vista.CONSTANTS;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

public abstract class IDialog<E> extends IAction{
    private E e;
    private boolean acept = false;
    private JDialog dialog;

    public IDialog(String title){
        createGUI(title);
    }
    public abstract JPanel createPane();
    private void createGUI(String title){
        dialog = new JDialog(GlobalFrame.getInstance().getFrame(), title,true);

        //dialog.setIconImage(getImagen("Imagen/icon_emp.png").getImage());
        dialog.setResizable(false);
        ///dialog.addWindowListener(this);
        //dialog.setSize(440,430);
    }

    public void addContentPane(JPanel panel, boolean visible){
        addContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    public void addContentPane(JPanel panel){
        Container content= dialog.getContentPane();
        content.setLayout(new FlowLayout());

        content.add(panel);
    }

    public void close(){
        dialog.setVisible(false);
        dialog.dispose();
    }

    public boolean isAcept() {
        return acept;
    }

    public void trueAcept(){
        acept = true;
    }

    public E getData(){
        return e;
    }
    public boolean isData(){
        return e != null;
    }
    public void insertData(E e){
        this.e = e;
    }
}
