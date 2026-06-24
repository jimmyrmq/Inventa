package com.djm.inventa.ui.ipanel;

import javax.swing.JInternalFrame;

public interface IPanelDesktop<E> {
        JInternalFrame getDesktopPane();
        String getID();
        void insertData(E e);
}
