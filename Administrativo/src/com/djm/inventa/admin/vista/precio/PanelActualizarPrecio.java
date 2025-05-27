package com.djm.inventa.admin.vista.precio;

import com.djm.inventa.admin.modelo.Categoria;
import com.djm.inventa.admin.modelo.Proveedor;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Table;
import com.djm.inventa.admin.vista.ipanel.IPanelDataAction;
import com.djm.inventa.admin.vista.precio.modelo.ProductoActualizar;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import com.djm.inventa.admin.vista.component.TextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelActualizarPrecio extends IPanelDataAction<Object> {
    private ModelTablePrecioCuston mpc;
    private Table<ProductoActualizar> tabla;
    private JPanel pPrincipal;
    private JComboBox<Proveedor> cbProveedor;
    private JComboBox<Categoria> cbCategoria;
    private DefaultComboBoxModel<Proveedor> dcbProveedor;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private JRadioButton porcentaje, monto;
    private TextField tValor;
    private JCheckBox precio1,precio2,precio3;

    private JButton bSimular, bCancelar, bActualizar, bRestaurar;
    private ImageIcon iRestaurar;

    public PanelActualizarPrecio(){
        pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setSize(new Dimension(1200,1200));
        pPrincipal.setPreferredSize(new Dimension(1300,400));
        pPrincipal.setOpaque(false);

        bActualizar = new JButton(CONSTANTS.LANG.getValue("actualizar.button.actualizar"));
        bCancelar = new JButton(CONSTANTS.LANG.getValue("button.cancelar"));
        bActualizar.setEnabled(false);
        
        pPrincipal.add(pFiltro(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        pPrincipal.add(pMonto(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));

        pPrincipal.add(pTabla(), LayoutPanel.constantePane(1, 0, 2, 2, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 10, 0.0f, 0.0f));
        //pPrincipal.add(bActualizar,LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 5, 0, 0.0f, 0.0f));
        //pPrincipal.add(bCancelar,LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 10, 5, 10, 0.0f, 0.0f));
    }

    private JPanel pFiltro(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lCategoria = new JLabel(CONSTANTS.LANG.getValue("producto.label.categoria"));
        JLabel lProveedor = new JLabel(CONSTANTS.LANG.getValue("producto.label.proveedor"));

        dcbProveedor = new DefaultComboBoxModel<Proveedor>();

        Proveedor prov = new Proveedor();
        prov.setNombre("Todos");

        dcbProveedor.addElement(prov);

        cbProveedor = new JComboBox<>(dcbProveedor);
        cbProveedor.setPreferredSize(CONSTANTS.CDDIM);

        dcbCategoria = new DefaultComboBoxModel<Categoria> ();

        Categoria cat = new Categoria();
        cat.setNombre("Todos");

        dcbCategoria.addElement(cat);

        cbCategoria = new JComboBox<>(dcbCategoria);
        cbCategoria.setPreferredSize(CONSTANTS.CDDIM);

        panel.add(lCategoria, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lProveedor, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbProveedor, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel pMonto() {
        JPanel panel = new JPanel(new GridBagLayout()){
            @Override
            public void updateUI(){
                Color colButton = PropiedadesSistema.getColor("Label.colorDarker");
                if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
                    colButton = UIManager.getColor("TextField.foreground");
                }

                iRestaurar = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/back.png") ,colButton,false));

                if(bRestaurar!= null) {
                    bRestaurar.setIcon(iRestaurar);
                    bRestaurar.updateUI();
                }
                repaint();

                super.updateUI();
            }
        };
        panel.setOpaque(false);

        bSimular = new JButton(CONSTANTS.LANG.getValue("actualizar.button.simular"));
        bRestaurar = new JButton(iRestaurar);

        JLabel lValor = new JLabel(CONSTANTS.LANG.getValue("label.valor"));
        JLabel lMensaje = new JLabel(CONSTANTS.LANG.getValue("actualizar.label.actualizar"));

        String simbolo = PropiedadesSistema.getString("Moneda.Simbolo");

        porcentaje = new JRadioButton(CONSTANTS.LANG.getValue("actualizar.porcentaje"));
        monto = new JRadioButton(CONSTANTS.LANG.getValue("actualizar.precio",simbolo));

        porcentaje.setSelected(true);

        ButtonGroup bg = new ButtonGroup();
        bg.add(porcentaje);
        bg.add(monto);

        tValor = new TextField(10);

        precio1 = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.precio1").replace(":",""));
        precio2 = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.precio2").replace(":",""));
        precio3 = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.precio3").replace(":",""));

        precio1.setSelected(true);
        precio2.setSelected(true);
        precio3.setSelected(true);

        panel.add(precio1, LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(precio2, LayoutPanel.constantePane(0, 1,  3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(precio3, LayoutPanel.constantePane(0, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));

        panel.add(porcentaje, LayoutPanel.constantePane(0, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(monto, LayoutPanel.constantePane(2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));

        panel.add(lValor, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 8, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tValor, LayoutPanel.constantePane(1, 4, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));

        panel.add(bSimular, LayoutPanel.constantePane(3, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bRestaurar, LayoutPanel.constantePane(4, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lMensaje, LayoutPanel.constantePane(0, 6, 5, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel pTabla() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        mpc = new ModelTablePrecioCuston();

        ModeloTabla<ProductoActualizar> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 190);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTable();

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    ProductoActualizar prod = tabla.getSelectedItem();

                    if(prod!=null) {
                        //fillDataProduct(prod);
                    }
                }
            }
        });

        tabla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = tabla.getSelectedRow();
                    if(row != -1){
                        ProductoActualizar prod = tabla.getSelectedItem();

                        if(prod!=null) {
                            //fillDataProduct(prod);
                        }
                    }
                    //int col = tabla.getSelectedColumn();
                    //System.out.println("Se presionó Enter en la celda (" + row + ", " + col + ")");
                    // Puedes realizar otras acciones cuando se presiona Enter en una celda
                }
            }
        });

        panel.add(tabla.getPanel(),LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        Dimension dimension = tabla.getDimension();
        System.out.println(panel.getPreferredSize());
        //panel.setPreferredSize(dimension);
        //panel.setSize(dimension);
        return panel;
    }
    private void loadTable(){
        ProductoActualizar productoActualizar = new ProductoActualizar();
        productoActualizar.setSeleccionado(true);
        productoActualizar.setNombre("Producto 1");
        productoActualizar.setCodigo("Cod 1");
        productoActualizar.setPrecio1(1.0);
        productoActualizar.setPrecio1Ant(2.0);
        productoActualizar.setPrecio2(3.0);
        productoActualizar.setPrecio2Ant(4.0);
        productoActualizar.setPrecio3(5.0);
        productoActualizar.setPrecio3Ant(6.0);
        System.out.println(productoActualizar);
        tabla.addRow(productoActualizar);
    }
    @Override
    public void actionEsc() {
        System.out.println("escape");
    }

    @Override
    public JPanel getPanel() {
        return pPrincipal;
    }

    @Override
    public void clearForm() {

    }

    @Override
    public Object getData() {
        return null;
    }
}
