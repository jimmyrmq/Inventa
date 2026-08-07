package com.djm.inventa.ui.component.table;

import java.util.ArrayList;
import java.util.List;

public class ColumnGroup {

    private String text;
    private List<Object> columns = new ArrayList<>();

    public ColumnGroup(String text) {
        this.text = text;
    }

    public void add(Object obj) {
        columns.add(obj);
    }

    public String getText() {
        return text;
    }

    public List<Object> getColumns() {
        return columns;
    }
}
