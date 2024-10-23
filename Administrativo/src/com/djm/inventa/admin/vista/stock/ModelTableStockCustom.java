package com.djm.inventa.admin.vista.stock;

import com.djm.inventa.admin.modelo.Categoria;
import com.djm.inventa.admin.modelo.Marca;
import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.modelo.Stock;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.table.IModelTableCustom;
import com.djm.ui.component.table.ObjectModelTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelTableStockCustom implements IModelTableCustom<Producto> {

    private LinkedList<Producto> datos = new LinkedList();
    private List<ObjectModelTable> listObject;
    private String[] columnName = {CONSTANTS.LANG.getValue("table.header.codigoproducto"),
            CONSTANTS.LANG.getValue("table.header.descripcion"),
            CONSTANTS.LANG.getValue("table.header.unidad"),
            CONSTANTS.LANG.getValue("table.header.marca"),
            CONSTANTS.LANG.getValue("table.header.categoria"),
            CONSTANTS.LANG.getValue("table.header.stock"),
            CONSTANTS.LANG.getValue("table.header.stockminimo"),
            ""
    };

    private Class[] columnClass = {String.class, String.class, String.class,Marca.class,Categoria.class,Integer.class, Integer.class, EtiquetaComponent.class};

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
                Producto producto = datos.get(rowIndex);

                // Se obtiene el campo apropiado según el valor de columnIndex
                if (columnIndex == 0) {
                    return producto.getCodigo();
                }
                else if (columnIndex == 1) {
                    return producto.getNombre();
                }
                else if (columnIndex == 2) {
                    return producto.getUnidadMedida();
                }
                else if (columnIndex == 3) {
                    return producto.getMarca()!=null?producto.getMarca().toString():null;
                }
                else if (columnIndex == 4) {
                    return producto.getCategoria()!=null?producto.getCategoria().toString():null;
                }
                else if (columnIndex == 5) {
                    return producto.getCantidadDisponible();
                }
                else if (columnIndex == 6) {
                    return producto.getStockCritico();
                }
                else if (columnIndex == 7) {
                    return producto.isDisponible() + "@" + producto.isNoRequiereStock() + "@" + producto.getCantidadDisponible() + "@" + producto.getStockCritico();
                }
                else return null;
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
            listObject.add(new ObjectModelTable(object.getUnidadMedida(), 2));
            listObject.add(new ObjectModelTable(object.getMarca(), 3));
            listObject.add(new ObjectModelTable(object.getCategoria(), 4));
            listObject.add(new ObjectModelTable(object.getCantidadDisponible(), 5));
            listObject.add(new ObjectModelTable(object.getStockCritico(), 6));
            listObject.add(new ObjectModelTable(col2, 7));
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
        Producto producto = datos.get(rowIndex);

        if (columnIndex == 0) {
            producto.setCodigo((String) aValue);
        } else if (columnIndex == 1) {
            producto.setNombre((String) aValue);
        }else if (columnIndex == 2) {
            producto.setUnidadMedida((String) aValue);
        }else if (columnIndex == 3) {
            producto.setMarca((Marca) aValue);
        }else if (columnIndex == 4) {
            producto.setCategoria((Categoria) aValue);
        } else if (columnIndex == 5) {
            producto.setCantidadDisponible((Integer) aValue);
        } else if (columnIndex == 6) {
            producto.setStockCritico((Integer) aValue);
        } else if (columnIndex == 7) {
            String vls [] = ((String)aValue).split("@");

            producto.setDisponible(Boolean.valueOf(vls[0]));
            producto.setNoRequiereStock(Boolean.valueOf(vls[1]));
            producto.setCantidadDisponible(Integer.parseInt(vls[2]));
            producto.setStockCritico(Integer.parseInt(vls[3]));
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
        int[] anchoColum = {120, 180, 100, 150, 150, 100,100,100};
        return anchoColum;
    }
}