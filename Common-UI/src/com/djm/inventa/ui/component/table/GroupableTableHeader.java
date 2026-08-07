package com.djm.inventa.ui.component.table;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class GroupableTableHeader extends JTableHeader {


    private final List<ColumnGroup> groups = new ArrayList<>();

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
    }

    public void addColumnGroup(ColumnGroup group) {
        groups.add(group);
    }

    public List<ColumnGroup> getColumnGroups() {
        return groups;
    }
}
