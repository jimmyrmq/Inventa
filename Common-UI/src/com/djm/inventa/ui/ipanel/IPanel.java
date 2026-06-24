package com.djm.inventa.ui.ipanel;

import javax.swing.*;

public interface IPanel{
    String getId();     // único por vista
    String getTitle();      // título de la ventana

    default TipoVista getTipoVista() {
        return TipoVista.INTERNAL_FRAME;
    }

    JPanel getPanel();

    ImageIcon getIcon();

    void clearForm();

    void setUIManager(IUIManager uiManager);


    default void onViewShown() {}
}
