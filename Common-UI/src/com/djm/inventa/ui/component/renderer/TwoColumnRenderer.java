package com.djm.inventa.ui.component.renderer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.function.Function;

public class TwoColumnRenderer<T> extends JPanel
        implements ListCellRenderer<T> {

    private final JLabel leftLabel = new JLabel();
    private final JLabel rightLabel = new JLabel();

    private final Function<T, String> leftMapper;
    private final Function<T, String> rightMapper;

    public TwoColumnRenderer(
            Function<T, String> leftMapper,
            Function<T, String> rightMapper) {

        this.leftMapper = leftMapper;
        this.rightMapper = rightMapper;

        setLayout(new BorderLayout(2, 0));

        add(leftLabel, BorderLayout.WEST);
        add(rightLabel, BorderLayout.EAST);

        leftLabel.setOpaque(false);
        rightLabel.setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 3));

        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends T> list,
            T value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        if (value == null) {
            leftLabel.setText("");
            rightLabel.setText("");
            return this;
        }

        leftLabel.setText(leftMapper.apply(value));
        rightLabel.setText(rightMapper.apply(value));

        if (index == -1) {
            setOpaque(false);
            setBackground(UIManager.getColor("ComboBox.background"));

            leftLabel.setForeground(UIManager.getColor("ComboBox.foreground"));
            rightLabel.setForeground(UIManager.getColor("ComboBox.foreground"));

        }
        else if (isSelected) {
            setOpaque(true);
            setBackground(UIManager.getColor("ComboBox.selectionBackground"));
            leftLabel.setForeground(UIManager.getColor("ComboBox.selectionForeground"));
            rightLabel.setForeground(UIManager.getColor("ComboBox.selectionForeground"));
        }
        else {
            setOpaque(true);
            setBackground(UIManager.getColor("ComboBox.background"));

            leftLabel.setForeground(UIManager.getColor("ComboBox.foreground"));//new Color(0, 90, 200));
            rightLabel.setForeground(Color.GRAY);
        }

        return this;
    }
}