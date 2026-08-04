package com.djm.inventa.producto.view.producto;

import com.djm.inventa.modelo.ProductoVariante;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class ModelTableVarianteCustom implements IModelTableCustom<ProductoVariante> {

    private LinkedList<ProductoVariante> datos = new LinkedList();
    private List<ObjectModelTable> listObject;

    //"Codigo", "Nombre", "Modelo", "Marca", "Categoria"
    private String[] columnName = {
            "SKU","Cod. Barra","Atributo","Valor","Cant. Min.","Cant. Max.","Cant. May.", "Cant. Disp."
            /*CONSTANTS.i18n.getValue("label.nombre"),
            CONSTANTS.i18n.getValue("producto.label.modelo"),
            CONSTANTS.i18n.getValue("producto.label.marca"),
            CONSTANTS.i18n.getValue("producto.label.categoria")*/
    };


    private Class[] columnClass = {String.class, String.class, String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<ProductoVariante> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (datos.size() > 0) {
            try {
                ProductoVariante aux = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                //"SKU","Cod. Barra","Atributo","Valor","Cant. Min.","Cant. Max.","Cant. May.", "Cant. Disp."
                if (columnIndex == 0) {
                    return aux.getSKU();
                } else if (columnIndex == 1) {
                    return aux.getCodigoBarra();
                } else if (columnIndex == 2) {
                    return aux.getAtributoProducto().getAtributo().getNombre();
                } else if (columnIndex == 3) {
                    return aux.getAtributoProducto().getValor();
                } else if (columnIndex == 4) {
                    return aux.getCantidadMinina();
                } else if (columnIndex == 5) {
                    return aux.getCantidadMaxima();
                } else if (columnIndex == 6) {
                    return aux.getCantidadMayor();
                } else if (columnIndex == 7) {
                    return aux.getCantidadStock();
                } else return null;
            } catch (IndexOutOfBoundsException exc) {
                LoggerApp.error("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        } else return null;
    }

    @Override
    public void editObject(ProductoVariante object, int row) {
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
    public ProductoVariante getValue(int row) {
        ProductoVariante item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject() {
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int[] anchoColum = {100, 150, 80,70,70,70,70,70};
        return anchoColum;
    }
}