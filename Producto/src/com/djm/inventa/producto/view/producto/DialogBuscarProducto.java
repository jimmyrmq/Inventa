package com.djm.inventa.producto.view.producto;

import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.ui.component.TextField;
import com.djm.ui.GlobalFrame;
import com.djm.ui.LayoutPanel;
import com.djm.ui.component.OptionPane;
import com.djm.inventa.ui.component.Table;
import com.djm.ui.component.table.ModeloTabla;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DialogBuscarProducto extends JDialog {
    private ModelTableProductoCustom mpc;
    private Table<Producto> tabla;
    private TableRowSorter sorter;
    private Producto productoSeleccionado;

    public DialogBuscarProducto(JFrame owner, List<Categoria> categorias) {
        super(owner, CONSTANTS.i18n.getValue("producto.label.buscar"), true);

        setLayout(new GridBagLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        TextField tfFiltro = new TextField(25);

        tfFiltro.setToolTipText(CONSTANTS.i18n.getValue("producto.label.filter.producto"));
        tfFiltro.setPlaceHolder(CONSTANTS.i18n.getValue("producto.label.filter.producto"));

        // Combo Categoria
        DefaultComboBoxModel<Categoria> dmCat = new DefaultComboBoxModel<>();

        Categoria allCat = new Categoria();
        allCat.setID(-1);
        allCat.setNombre(CONSTANTS.i18n.getValue("producto.label.filter.categoria.todos"));

        dmCat.addElement(allCat);

        if (categorias != null) {
            for (Categoria c : categorias) dmCat.addElement(c);
        }

        JComboBox<Categoria> cbFilCategoria = new JComboBox<>(dmCat);
        cbFilCategoria.setPreferredSize(CONSTANTS.CDDIM);

        top.add(cbFilCategoria);
        top.add(tfFiltro);

        pTable();

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton bAceptar = new JButton(CONSTANTS.i18n.getValue("button.aceptar"));
        bAceptar.setOpaque(false);

        JButton bCancelar = new JButton(CONSTANTS.i18n.getValue("button.cancelar"));
        bCancelar.setOpaque(false);

        disposeEsc(bCancelar);

        bottom.add(bCancelar);
        bottom.add(bAceptar);

        add(top, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 10, 0.0f, 0.0f));
        add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 5, 10, 0, 10, 0.0f, 1.0f));
        add(bottom, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 10, 5, 10, 0.0f, 0.0f));

        // Filtrado combinado
        Runnable aplicarFiltro = () -> {
            List<RowFilter<Object,Object>> filters = new ArrayList<>();
            String txt = tfFiltro.getText().trim();
            if (!txt.isEmpty()) {
                String expr = "(?i).*" + Pattern.quote(txt) + ".*";
                filters.add(RowFilter.regexFilter(expr, 0, 1, 2));
            }

            Categoria selC = (Categoria) cbFilCategoria.getSelectedItem();
            if (selC != null && selC.getID() != null && selC.getID() != -1) {
                String cname = selC.getNombre();
                if (cname != null && !cname.isEmpty()) {
                    filters.add(RowFilter.regexFilter("^" + Pattern.quote(cname) + "$", 4));
                }
            }

            if (filters.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.andFilter(filters));
            }
        };

        tfFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltro.run(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltro.run(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltro.run(); }
        });

        cbFilCategoria.addActionListener(ae -> aplicarFiltro.run());

        // Aceptar
        bAceptar.addActionListener(ae -> {
            productoSeleccionado = tabla.getSelectedItem();
            dispose();
        });

        bCancelar.addActionListener(ae -> {productoSeleccionado = null; dispose(); });

        //setSize(880, 410);
        pack();
        Dimension screenSize = getPreferredSize();
        setMinimumSize(screenSize);

        setResizable(false);

        tfFiltro.requestFocus();
        tfFiltro.requestFocusInWindow();

        setLocationRelativeTo(GlobalFrame.getInstance().getFrame());
        setVisible(true);
    }

    private void pTable(){

        mpc = new ModelTableProductoCustom();

        ModeloTabla<Producto> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 250);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTable();

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    productoSeleccionado = tabla.getSelectedItem();
                    dispose();
                }
            }
        });

        tabla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = tabla.getSelectedRow();
                    if(row != -1){
                        productoSeleccionado = tabla.getSelectedItem();
                        dispose();
                    }
                }
            }
        });

        sorter = tabla.getSorter();
    }


    private void loadTable() {

        // Cargar productos
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> productos = dao.listarProductos();
            for (Producto p : productos) {
                tabla.addRow(p);
            }
        } catch (ProductoException exc) {
            OptionPane.error(exc.getMessage());
        }
    }

    public Producto getSelectedProducto() { return productoSeleccionado; }

    private void disposeEsc(JButton button){
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };

        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "DISPOSE_DIALOG");
        ActionMap actionMap = button.getActionMap();
        actionMap.put("DISPOSE_DIALOG", action);
    }
}


