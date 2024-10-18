package com.djm.inventa.admin.vista.component;

import com.djm.ui.component.table.ModeloTabla;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;

public class Table<E>  extends JTable {
    private TableRowSorter<TableModel> sorter;
    private ModeloTabla modeloTabla;

    public Table(ModeloTabla modelo, int height) {
        super(modelo);

        this.modeloTabla = modelo;
        this.setShowGrid(false);
        this.setFillsViewportHeight(false);
        this.setShowHorizontalLines(true);
        this.setShowVerticalLines(false);
        //this.setBackground(this.tableUI.getBackground());
        this.setRowSelectionAllowed(true);
        this.setOpaque(true);
        this.setAutoResizeMode(0);
        //this.setGridColor(this.tableUI.getGridColor());
        this.setSelectionMode(0);
        this.setRowHeight(25);
        this.sorter = new TableRowSorter(modelo);
        this.setRowSorter(this.sorter);
        int dimX = 0;
        TableColumn column = null;
        int[] anchoColum = this.modeloTabla.getModelCustom().getWidthCell();

        for (int i = 0; i < anchoColum.length; ++i) {
            this.sorter.setSortable(i, false);
            column = this.getColumnModel().getColumn(i);
            column.setMinWidth(anchoColum[i]);
            column.setPreferredWidth(anchoColum[i]);
            dimX += anchoColum[i];
        }

        this.setPreferredScrollableViewportSize(new Dimension(dimX, height));
    }


    public JScrollPane getPanel() {
        return getPanel(null);
    }

    public JScrollPane getPanel(Border border) {
        //border = new CustomBorder(1,Color.BLUE,2,Color.BLUE,3,Color.BLUE,4,Color.BLUE);//new LineBorder(Color.BLUE, 3);//
        JScrollPane jsp = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);

        jsp.setBorder(border);
        jsp.getViewport().setBackground(UIManager.getColor("Table.background"));
        /*jsp.updateUI();
        jsp.repaint();
        jsp.revalidate();
        jsp.getViewport().updateUI();
        jsp.getViewport().repaint();
        jsp.getViewport().revalidate();*/

        return jsp;
    }


    public E getSelectedItem() {
        setEnabled(false);
        E object = null;
        int indexTableProductSelect = getSelectionModel().getLeadSelectionIndex();
        //System.out.println(">> "+index);
        if (indexTableProductSelect != -1) {
            int[] selection = getSelectedRows();
            if (selection.length == 1) {
                int row = convertRowIndexToModel(selection[0]);
                if (row != -1) {
                    Object obj = this.modeloTabla.getValue(row);
                    object = (E) obj;
                    //System.out.println(">> " + producto.getCategoria() + " " + producto.getNombre() + " " + producto.getNota());
                }
            }
        }

        setEnabled(true);
        return object;
    }

    public E getValue(int row) {
        E object = null;

        if (row != -1) {
            Object obj = this.modeloTabla.getValue(row);
            object = (E) obj;
        }

        return object;
    }

    public int getSelectedIndex() {
        int i = getSelectionModel().getLeadSelectionIndex();

        return i;
    }

    public void setRowSelectionInterval(int row) {
        setRowSelectionInterval(row, row);
    }

    public void soter(String text) {
        if (text == null || text.trim().isEmpty())
            sorter.setRowFilter(null);
        else
            sorter.setRowFilter(RowFilter.regexFilter(text));

        updateUI();
    }

    public void soter(RowFilter rowFilter) {
        sorter.setRowFilter(rowFilter);
    }

    public TableRowSorter getSorter(){
        return sorter;
    }
    public void clearTable(){
        modeloTabla.clearTable();
    }

    public void addRow(E e){
        modeloTabla.addRow(e);
    }
    public void editRow(E e, int row){
        modeloTabla.editRow(e,row);
    }

    public void removeRow(int row){
        modeloTabla.removeRow(row);
    }
}
