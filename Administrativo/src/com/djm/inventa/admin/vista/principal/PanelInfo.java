package com.djm.inventa.admin.vista.principal;

import com.djm.util.LayoutPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelInfo extends JPanel {
    public PanelInfo(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        JLabel lUsuario = new JLabel("Jimmy Morales");
        add(lUsuario, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 3, 3, 3, 0, 1.0f, 0.0f));

    }
}
