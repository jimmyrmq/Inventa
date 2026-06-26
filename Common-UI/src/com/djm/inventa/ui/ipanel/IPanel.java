package com.djm.inventa.ui.ipanel;

import javax.swing.*;

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

    void setUIManager(IUIManager uiManager);


    default void onViewShown() {}
}
