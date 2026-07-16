package com.djm.inventa.producto.view.producto;

import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.ui.component.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DialogBuscarProducto extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private Producto productoSeleccionado;

    public DialogBuscarProducto(Frame owner, List<Categoria> categorias) {
        super(owner, CONSTANTS.i18n.getValue("producto.label.buscar"), true);

        setSize(800, 450);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField tfFiltro = new JTextField(25);

        // Combo Categoria
        DefaultComboBoxModel<Categoria> dmCat = new DefaultComboBoxModel<>();

        Categoria allCat = new Categoria();
        allCat.setID(-1);
        allCat.setNombre("Todas");

        dmCat.addElement(allCat);

        if (categorias != null) {
            for (Categoria c : categorias) dmCat.addElement(c);
        }
        JComboBox<Categoria> cbFilCategoria = new JComboBox<>(dmCat);
        cbFilCategoria.setPreferredSize(CONSTANTS.CDDIM);

        top.add(cbFilCategoria);
        top.add(tfFiltro);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Modelo", "Marca", "Categoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        JScrollPane jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton bAceptar = new JButton(CONSTANTS.i18n.getValue("button.aceptar"));
        JButton bCancelar = new JButton(CONSTANTS.i18n.getValue("button.cancelar"));
        bottom.add(bCancelar);
        bottom.add(bAceptar);
        add(bottom, BorderLayout.SOUTH);

        // Cargar productos
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> productos = dao.listarProductos();
            for (Producto p : productos) {
                String marca = p.getMarca() != null ? p.getMarca().getNombre() : "";
                String cat = p.getCategoria() != null ? p.getCategoria().getNombre() : "";
                model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getModelo(), marca, cat});
            }
        } catch (ProductoException exc) {
            OptionPane.error(exc.getMessage());
        }

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
            int row = table.getSelectedRow();
            if (row < 0) {
                OptionPane.error(CONSTANTS.i18n.getValue("producto.tabla.seleccione"));
                return;
            }
            int mrow = table.convertRowIndexToModel(row);
            String codigo = (String) model.getValueAt(mrow, 0);
            try {
                Producto p = new ProductoDAO().obtenerProducto(codigo);
                if (p != null) {
                    productoSeleccionado = p;
                    dispose();
                }
            } catch (ProductoException exc) {
                OptionPane.error(exc.getMessage());
            }
        });

        bCancelar.addActionListener(ae -> { productoSeleccionado = null; dispose(); });

        // Doble click para aceptar
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    bAceptar.doClick();
                }
            }
        });
    }

    public Producto getSelectedProducto() { return productoSeleccionado; }
}


