package com.djm.inventa.ui.ipanel;

import javax.swing.ImageIcon;

public interface IMenuContribution {

    String getMenuGrupo();
    String getMenuLabel();
    int getMenuOrden();
    ImageIcon getMenuIcono();

    void onClick(IUIManager uiManager);
}

