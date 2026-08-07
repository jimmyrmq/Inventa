package com.djm.inventa.producto.view.producto.renderer;


import com.djm.inventa.ui.IconManager;
import com.djm.ui.LayoutPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class ThreeLabelRendererPrecio<T> extends JPanel
        implements ListCellRenderer<T> {

    private final JLabel label1 = new JLabel();
    private final JLabel label2 = new JLabel();
    private final JLabel labelIcon = new JLabel();
    private final JLabel label4 = new JLabel();

    private final Function<T, String> mapper1;
    private final Function<T, String> mapper2;
    private final Function<T, Boolean> mapper3;
    private final Function<T, String> mapper4;
    private final Color colorFont = UIManager.getColor("List.foreground");//new Color(0, 90, 200);

    public ThreeLabelRendererPrecio(
            Function<T, String> mapper1, //Tipo
            Function<T, String> mapper2, //Valor + Simbolo
            Function<T, Boolean> mapper3,
            Function<T, String> mapper4//Nombre Moneda
    ) {

        this.mapper1 = mapper1;
        this.mapper2 = mapper2;
        this.mapper3 = mapper3;
        this.mapper4 = mapper4;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 5));

        setOpaque(false);

        add(labelIcon, LayoutPanel.constantePane(0, 0, 1, 0, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 0, 0, 0, 0, 0.0f, 0.0f));
        add(label1, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        add(label2, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 0.0f, 0.0f));
        add(label4, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 0.0f, 0.0f));

        Font bold = UIManager.getFont("Label.font").deriveFont(Font.BOLD);

        label1.setFont(bold);
        label2.setFont(bold);

        label1.setForeground(colorFont);
        label2.setForeground(colorFont);

        labelIcon.setPreferredSize(new Dimension(16, 16));

        labelIcon.setForeground(UIManager.getColor("Label.foreground"));
        label4.setForeground(UIManager.getColor("Label.foreground"));

        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends T> list,
            T value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        label1.setText(mapper1.apply(value));
        label2.setText(mapper2.apply(value));
        label4.setText(mapper4.apply(value));

        Boolean mostrarIcono = mapper3.apply(value);

        if (Boolean.TRUE.equals(mostrarIcono)) {
            labelIcon.setIcon(IconManager.get16("key"));
            labelIcon.setText("");
        } else {
            labelIcon.setIcon(null);
            labelIcon.setText("");
        }

        if (isSelected) {
            setBackground(UIManager.getColor("List.selectionBackground"));

            label1.setForeground(UIManager.getColor("List.selectionForeground"));
            label2.setForeground(UIManager.getColor("List.selectionForeground"));
            label4.setForeground(UIManager.getColor("List.selectionForeground"));

        } else {
            setBackground(UIManager.getColor("List.background"));

            label1.setForeground(colorFont);
            label2.setForeground(colorFont);
            label4.setForeground(UIManager.getColor("List.foreground"));
        }

        return this;
    }
}