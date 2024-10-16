package com.djm.inventa.admin.vista.principal;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public interface IPanelDesktop {
        JInternalFrame getDesktopPane();
        JPanel getPanel();
        JPanel getToolBar();
        String getID();
        String getTitulo();
}
