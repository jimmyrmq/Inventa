package com.djm.inventa.admin.vista.ipanel;

import javax.swing.JInternalFrame;

public interface IPanelDesktop<E> {
        JInternalFrame getDesktopPane();
        String getID();
        void insertData(E e);
}
