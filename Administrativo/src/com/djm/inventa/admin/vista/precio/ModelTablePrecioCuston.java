package com.djm.inventa.admin.vista.precio;

import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.precio.modelo.ProductoActualizar;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTablePrecioCuston  implements IModelTableCustom<ProductoActualizar> {
    private LinkedList<ProductoActualizar> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private String[] columnName = {"",CONSTANTS.LANG.getValue("table.header.codigoproducto"),
            CONSTANTS.LANG.getValue("table.header.descripcion"),
            CONSTANTS.LANG.getValue("table.header.precio1"),
            CONSTANTS.LANG.getValue("table.header.precio1Ant"),
            CONSTANTS.LANG.getValue("table.header.precio2"),
            CONSTANTS.LANG.getValue("table.header.precio2Ant"),
            CONSTANTS.LANG.getValue("table.header.precio3"),
            CONSTANTS.LANG.getValue("table.header.precio3Ant")
    };

    private Class[] columnClass = {Boolean.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class};

    @Override
    public Class[] getColumnClass() {
        return columnClass;
    }

    @Override
    public String[] getColumnName() {
        return columnName;
    }

    @Override
    public LinkedList<ProductoActualizar> getListData() {
        return this.datos;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (datos.size() > 0) {
            try {
                ProductoActualizar aux = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                if (columnIndex == 0) {
                    return aux.isSeleccionado();
                }else if (columnIndex == 1) {
                    return aux.getCodigo();
                } else if (columnIndex == 2) {
                    return aux.getNombre();
                } else if (columnIndex == 3) {
                    return aux.getPrecio1();
                } else if (columnIndex == 4) {
                    return aux.getPrecio1Ant();
                } else if (columnIndex == 5) {
                    return aux.getPrecio2();
                } else if (columnIndex == 6) {
                    return aux.getPrecio2Ant();
                } else if (columnIndex == 7) {
                    return aux.getPrecio3();
                } else if (columnIndex == 8) {
                    return aux.getPrecio3Ant();
                } else return null;
            } catch (IndexOutOfBoundsException exc) {
                LoggerApp.error("rowIndex: " + rowIndex + ", columnIndex:" + columnIndex + " -> " + exc);
                return null;
            }
        } else return null;
    }

    @Override
    public void editObject(ProductoActualizar object, int row) {
        if (listObject != null)
            listObject.clear();
        else
            listObject = new ArrayList<>();

        if (row != -1) {

            //DefaultTableModel model = (DefaultTableModel)GlobalProduct.getInstance().table.getModel();
            listObject.add(new ObjectModelTable(object.isSeleccionado(), 0));
            listObject.add(new ObjectModelTable(object.getCodigo(), 1));
            listObject.add(new ObjectModelTable(object.getNombre(), 2));
            listObject.add(new ObjectModelTable(object.getPrecio1(), 3));
            listObject.add(new ObjectModelTable(object.getPrecio1Ant(), 4));
            listObject.add(new ObjectModelTable(object.getPrecio2(), 5));
            listObject.add(new ObjectModelTable(object.getPrecio2Ant(), 6));
            listObject.add(new ObjectModelTable(object.getPrecio3(), 7));
            listObject.add(new ObjectModelTable(object.getPrecio3Ant(), 8));
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

        ProductoActualizar aux = datos.get(rowIndex);

        if (columnIndex == 0) {
            aux.setSeleccionado((boolean) aValue);
        } else if (columnIndex == 1) {
            aux.setCodigo((String) aValue);
        } else if (columnIndex == 2) {
            aux.setNombre((String) aValue);
        } else if (columnIndex == 3) {
            aux.setPrecio1((Double) aValue);
        }else if (columnIndex == 4) {
            aux.setPrecio1Ant((Double) aValue);
        }else if (columnIndex == 5) {
            aux.setPrecio2((Double) aValue);
        }else if (columnIndex == 6) {
            aux.setPrecio2Ant((Double) aValue);
        }else if (columnIndex == 7) {
            aux.setPrecio3((Double) aValue);
        }else if (columnIndex == 8) {
            aux.setPrecio3Ant((Double) aValue);
        }
    }

    @Override
    public ProductoActualizar getValue(int row) {
        ProductoActualizar item = datos.get(row);
        return item;
    }

    @Override
    public List<ObjectModelTable> getValueObject() {
        return listObject;
    }

    @Override
    public int[] getWidthCell() {
        int[] anchoColum = {30,120, 180, 100, 100, 100, 100, 100, 100};
        return anchoColum;
    }
}
