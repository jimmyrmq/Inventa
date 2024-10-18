package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.principal.IPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ProductoListener implements ActionListener {
    private final String ID = PropiedadesSistema.getString("Producto.ID");
    private IPanel <Producto> iProd;
    public ProductoListener(IPanel iPanel){
        iProd = iPanel;//Global.panelDesktop.getIPanel(ID);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("BUTTON_CANCELAR".equals(action)){

            if(iProd != null) {
                if (iProd.isData()) {
                    iProd.clearForm();
                } else
                    Global.panelDesktop.cerrarVentana(ID);
            }
        }
        else if("GUARDAR_PRODUCTO".equals(action)){
            if(iProd != null){
                Producto producto =iProd.getData();

                if(producto.getID() == null){

                    Random random = new Random();
                    int randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    producto.setID(randomNumber);
                }

                Global.panelDesktop.setProductoList(producto);
                iProd.clearForm();
            }
        }
        else if("BUTTON_ELIMINAR".equals(action)){
            if(iProd != null){
                Global.panelDesktop.delProductoList(iProd.getData());
                iProd.clearForm();
            }
        }
    }
}
