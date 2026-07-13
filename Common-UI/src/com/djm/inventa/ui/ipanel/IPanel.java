package com.djm.inventa.ui.ipanel;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public interface IPanel{
    String getId();     // único por vista
    String getTitle();      // título de la ventana

    default TipoVista getTipoVista() {
        return TipoVista.INTERNAL_FRAME;
    }

    JPanel getPanel();

    default ImageIcon getIcon(){
        return null;
    }

    void clearForm();

    void setActionListener(ActionListener actionListener);

    default void onViewShown() {}

    default void onGuardar() {}

    default void onCancelar() {}

    void init();

}
