package com.djm.inventa.producto.view.producto;

import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.util.LinkedList;
import java.util.List;

public class ModelTableProductoCustom  implements IModelTableCustom<Producto> {

    private LinkedList<Producto> datos = new LinkedList();
    private List<ObjectModelTable> listObject;

    //"Codigo", "Nombre", "Modelo", "Marca", "Categoria"
    private String[] columnName = {
            CONSTANTS.i18n.getValue("label.codigo"),
            CONSTANTS.i18n.getValue("label.nombre"),
            CONSTANTS.i18n.getValue("producto.label.modelo"),
            CONSTANTS.i18n.getValue("producto.label.marca"),
            CONSTANTS.i18n.getValue("producto.label.categoria")
    };


    private Class[] columnClass = {String.class, String.class, String.class, Marca.class, Categoria.class};

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
                ////"Codigo", "Nombre", "Modelo", "Marca", "Categoria"
                if (columnIndex == 0) {
                    return aux.getCodigo();
                } else if (columnIndex == 1) {
                    return aux.getNombre();
                } else if (columnIndex == 2) {
                    return aux.getModelo();
                }else if (columnIndex == 3) {
                    return aux.getMarca();
                }else if (columnIndex == 4) {
                    return aux.getCategoria();
                } else return null;
            } catch (IndexOutOfBoundsException exc) {
                LoggerApp.error("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        } else return null;
    }

    @Override
    public void editObject(Producto object, int row) {
        /*if (listObject != null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if (row != -1) {
            String col2 = object.isDisponible() + "@" + object.isNoRequiereStock() + "@" + object.getCantidadDisponible() + "@" + object.getStockCritico();

            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.getCodigo(), 0));
            listObject.add(new ObjectModelTable(object.getNombre(), 1));
            listObject.add(new ObjectModelTable(col2, 2));
        }*/
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
/*
       Producto aux = datos.get(rowIndex);

        if (columnIndex == 0) {
            aux.setCodigo((String) aValue);
        } else if (columnIndex == 1) {
            aux.setNombre((String) aValue);
        } else if (columnIndex == 2) {
            String vls [] = ((String)aValue).split("@");

            aux.setDisponible(Boolean.valueOf(vls[0]));
            aux.setNoRequiereStock(Boolean.valueOf(vls[1]));
            aux.setCantidadDisponible(Integer.parseInt(vls[2]));
            aux.setStockCritico(Integer.parseInt(vls[3]));
        }*/
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
        int[] anchoColum = {180, 210, 150,150,150};
        return anchoColum;
    }
}