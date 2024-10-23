package com.djm.inventa.admin.vista.principal;

import javax.swing.JPanel;
import java.awt.event.ActionListener;

public interface IPanel{
    JPanel getPanel();

    void actionListener(ActionListener al);

    void clearForm();
}
