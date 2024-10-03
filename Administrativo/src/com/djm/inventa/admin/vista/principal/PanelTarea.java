package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.vista.producto.PanelProducto;
import com.djm.util.LayoutPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelTarea extends JPanel {
    public PanelTarea(){
        setOpaque(false);
        setLayout(new GridBagLayout());
        //setBackground(Color.WHITE);//new Color(177, 177, 177));

        add(pTitulo(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 3, 0, 1.0f, 0.0f));
        add(new PanelProducto(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 3, 3, 3, 0, 1.0f, 1.0f));
    }
    private JPanel pTitulo(){
        Color color = UIManager.getColor("TextField.background");
        Font aux = UIManager.getFont("Label.font");
        Font font = aux.deriveFont(25f);

        JPanel panel= new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(color);

        JLabel label = new JLabel("Productos");
        label.setFont(font);

        panel.add(label, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 3, 3, 3, 0, 1.0f, 1.0f));

        return panel;
    }
}
