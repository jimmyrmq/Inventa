package com.djm.inventa.admin.vista.principal;

import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;

public interface IPanelDesktop<E> {
        JInternalFrame getDesktopPane();
        String getID();

        void insertData(E e);

        IPanel<E> getIPanel();
}
