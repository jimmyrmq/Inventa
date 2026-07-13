package com.djm.inventa.admin.vista.ingreso.archivo;

public class ColumnSelected {
    private String nameColumn;
    private int index;

    public ColumnSelected(String nameColumn, int index){
        this.nameColumn = nameColumn;
        this.index = index;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return nameColumn;
    }
}
