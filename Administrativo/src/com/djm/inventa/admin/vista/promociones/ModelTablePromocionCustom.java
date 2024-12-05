package com.djm.inventa.admin.vista.promociones;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTablePromocionCustom implements IModelTableCustom<Producto> {

    private LinkedList<Producto> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private String[] columnName = {CONSTANTS.LANG.getValue("table.header.codigo"),
            CONSTANTS.LANG.getValue("table.header.descripcion"),
            CONSTANTS.LANG.getValue("table.header.fechaini"),
            CONSTANTS.LANG.getValue("table.header.fechafin"),
            CONSTANTS.LANG.getValue("table.header.estado")
    };

    private Class[] columnClass = {String.class, String.class, EtiquetaComponent.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<Producto> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (datos.size() > 0) {
            try {
                Producto aux = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                if (columnIndex == 0) {
                    return aux.getCodigo();
                } else if (columnIndex == 1) {
                    return aux.getNombre();
                } else if (columnIndex == 5) {
                    return aux.isDisponible() + "@" + aux.isNoRequiereStock() + "@" + aux.getCantidadDisponible() + "@" + aux.getStockCritico();
                } else return null;
            } catch (IndexOutOfBoundsException exc) {
                LoggerApp.error("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        } else return null;
    }

    @Override
    public void editObject(Producto object, int row) {
        if (listObject != null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if (row != -1) {
            String col2 = object.isDisponible() + "@" + object.isNoRequiereStock() + "@" + object.getCantidadDisponible() + "@" + object.getStockCritico();

            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.getCodigo(), 0));
            listObject.add(new ObjectModelTable(object.getNombre(), 1));
            listObject.add(new ObjectModelTable(col2, 5));
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

       Producto aux = datos.get(rowIndex);

        if (columnIndex == 0) {
            aux.setCodigo((String) aValue);
        } else if (columnIndex == 1) {
            aux.setNombre((String) aValue);
        } else if (columnIndex == 5) {
            String vls [] = ((String)aValue).split("@");

            aux.setDisponible(Boolean.valueOf(vls[0]));
            aux.setNoRequiereStock(Boolean.valueOf(vls[1]));
            aux.setCantidadDisponible(Integer.parseInt(vls[2]));
            aux.setStockCritico(Integer.parseInt(vls[3]));
        }
    }

    @Override
    public Producto getValue(int row) {
        Producto item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject() {
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int[] anchoColum = {120, 180, 110,110,100};
        return anchoColum;
    }
}