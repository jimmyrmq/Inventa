package com.djm.inventa.ui.component.table;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class GroupableHeaderRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column);

        label.setHorizontalAlignment(CENTER);
        label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        label.setBackground(UIManager.getColor("TableHeader.background"));
        label.setOpaque(true);

        return label;
    }
}
