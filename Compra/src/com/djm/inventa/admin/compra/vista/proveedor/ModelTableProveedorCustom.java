package com.djm.inventa.admin.compra.vista.proveedor;

import com.djm.inventa.admin.compra.core.CONSTANTS;
import com.djm.inventa.admin.compra.modelo.Proveedor;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTableProveedorCustom implements IModelTableCustom<Proveedor> {

    private LinkedList<Proveedor> datos = new LinkedList<>();
    private List<ObjectModelTable> listObject;

    private String[] columnName;

    private final Class[] columnClass = {String.class, String.class, String.class, String.class};

    public ModelTableProveedorCustom(){
        this.columnName = new String[] {
                CONSTANTS.i18n.getValue("label.codigo"),
                CONSTANTS.i18n.getValue("label.nombre"),
                CONSTANTS.i18n.getValue("proveedor.label.contacto"),
                CONSTANTS.i18n.getValue("label.telefono")
        };
    }

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<Proveedor> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (datos.size() > 0) {
            try {
                Proveedor aux = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                if (columnIndex == 0) {
                    return aux.getCodigo();
                } else if (columnIndex == 1) {
                    return aux.getNombre();
                } else if (columnIndex == 2) {
                    return aux.getNombreContacto();
                } else if (columnIndex == 3) {
                    return aux.getTelefono1();
                } else {
                    return null;
                }
            } catch (IndexOutOfBoundsException exc) {
                LoggerApp.error("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void editObject(Proveedor object, int row) {
        if (listObject != null) {
            listObject.clear();
        } else {
            listObject = new ArrayList<>();
        }

        if (row != -1) {
            listObject.add(new ObjectModelTable(object.getCodigo(), 0));
            listObject.add(new ObjectModelTable(object.getNombre(), 1));
            listObject.add(new ObjectModelTable(object.getNombreContacto(), 2));
            listObject.add(new ObjectModelTable(object.getTelefono1(), 3));
        }
    }

    @Override
    public int getCountCoulumn() {
        return columnName.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnName[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Proveedor aux = datos.get(rowIndex);

        if (columnIndex == 0) {
            aux.setCodigo((String) aValue);
        } else if (columnIndex == 1) {
            aux.setNombre((String) aValue);
        } else if (columnIndex == 2) {
            aux.setNombreContacto((String) aValue);
        } else if (columnIndex == 3) {
            aux.setTelefono1((String) aValue);
        }
    }

    @Override
    public Proveedor getValue(int row) {
        return datos.get(row);
    }

    @Override
    public List<ObjectModelTable> getValueObject() {
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int[] anchoColum = {100, 180, 150, 120};
        return anchoColum;
    }
}
