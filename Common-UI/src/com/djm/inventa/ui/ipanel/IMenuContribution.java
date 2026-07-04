package com.djm.inventa.ui.ipanel;

import javax.swing.JMenuItem;

public interface IMenuContribution {
    JMenuItem getMenu();
    String getMenuGrupo();
    int getMenuOrden();

    default boolean getNuevoGrupo() {
        return false;
    }

    void onClick(IUIManager uiManager);
}

