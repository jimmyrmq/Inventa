package com.djm.inventa.producto.view.producto.renderer;


import com.djm.inventa.modelo.AtributoValor;
import com.djm.ui.LayoutPanel;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ThreeLabelRendererAtrib extends JPanel
        implements ListCellRenderer<AtributoValor> {

    private final JLabel label1 = new JLabel();
    private final JLabel label2 = new JLabel();

    private final Color colorFont = UIManager.getColor("List.foreground");//new Color(0, 90, 200);

    public ThreeLabelRendererAtrib() {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 5));

        setOpaque(false);

         add(label1, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
         add(label2, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 0.0f, 0.0f));

        Font bold = UIManager.getFont("Label.font").deriveFont(Font.BOLD);

        label1.setFont(bold);

        label1.setForeground(colorFont);
        label2.setForeground(colorFont);

        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends AtributoValor> list,
            AtributoValor value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        label1.setText(value.getAtributo().getNombre());
        label2.setText(value.getValor());

        if (isSelected) {
            setBackground(UIManager.getColor("List.selectionBackground"));

            label1.setForeground(UIManager.getColor("List.selectionForeground"));
            label2.setForeground(UIManager.getColor("List.selectionForeground"));

        } else {
            setBackground(UIManager.getColor("List.background"));

            label1.setForeground(colorFont);
            label2.setForeground(colorFont);
        }

        return this;
    }
}