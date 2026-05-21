package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.admin.modelo.Proveedor;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Table;
import com.djm.inventa.admin.vista.component.TextField;
import com.djm.inventa.admin.vista.component.renderer.StatusIconProductRenderer;
import com.djm.inventa.admin.vista.component.renderer.TipoEtiqueta;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PanelTableProveedor  extends JPanel {
    private ModelTableProveedorCustom mpc;
    private Table<Proveedor> tabla;
    private Proveedor proveedor;
    private TextField tBuscar;
    private ImageIcon lupaText;
    private final String carp = "16/";
    private final List<ProveedorSelectionListener> selectionListeners = new ArrayList<>();

    public PanelTableProveedor() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        tBuscar();
        pTable();

        add(tBuscar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 0.0f, 0.0f));
        add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 5, 0, 0, 0, 0.0f, 1.0f));
    }

    private void tBuscar(){

        tBuscar = new TextField(23);

        tBuscar.putClientProperty("JTextField.placeholderText", CONSTANTS.LANG.getValue("proveedor.label.buscar_producto"));
        tBuscar.putClientProperty("JTextField.leadingIcon", lupaText);
        Dimension dim = new Dimension(210, 23);

        tBuscar.setMinimumSize(dim);
        tBuscar.setPreferredSize(dim);
        tBuscar.setBackground(UIManager.getColor("TextField.background"));
        tBuscar.setForeground(UIManager.getColor("TextField.foreground"));

        this.tBuscar.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                int key = ke.getKeyCode();

                if (key == 40) {
                    tabla.setRowSelectionInterval(0);
                    tabla.requestFocus();
                }
            }

            public void keyReleased(KeyEvent ke) {
                tabla.soter(tBuscar.getText());

                Color cb = UIManager.getColor("TextField.background");
                Color fb = UIManager.getColor("TextField.foreground");
                if(tabla.getRowCount() ==  0){
                    cb = PropiedadesSistema.getColor("TextField.backgroundError");
                    fb = Color.WHITE;
                    //tBuscar.mostrarError(CONSTANTS.LANG.getValue("producto.noencontrado"),2000);
                }
                tBuscar.setForeground(fb);
                tBuscar.setBackground(cb);
                tBuscar.repaint();
            }

            public void keyTyped(KeyEvent ke) {
            }
        });
    }

    private void pTable(){

        mpc = new ModelTableProveedorCustom();

        ModeloTabla<Proveedor> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 210);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTable();

        tabla.setDefaultRenderer(EtiquetaComponent.class, new StatusIconProductRenderer(TipoEtiqueta.NONE, 2));

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    proveedor = tabla.getSelectedItem();
                    // notify listeners (e.g., parent form) that a proveedor was selected
                    fireProveedorSelected(proveedor);
                }
            }
        });

        tabla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = tabla.getSelectedRow();
                    if(row != -1){
                        proveedor = tabla.getSelectedItem();
                        // notify listeners when Enter is pressed
                        fireProveedorSelected(proveedor);
                    }
                }
            }
        });
    }

    // Listener support for notifying when a proveedor is selected (double click or Enter)
    public void addProveedorSelectionListener(ProveedorSelectionListener l){
        if(l != null)
            selectionListeners.add(l);
    }

    public void removeProveedorSelectionListener(ProveedorSelectionListener l){
        selectionListeners.remove(l);
    }

    private void fireProveedorSelected(Proveedor p){
        for(ProveedorSelectionListener l : selectionListeners){
            try{
                l.proveedorSelected(p);
            }catch(Exception ex){ /* ignore listener errors */ }
        }
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void loadTable(){
        tabla.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        Thread thread = new Thread(()-> {
            tabla.setEnabled(false);

            String[] empresas = {
                "Distribuidora ABC",
                "Proveedores Unidos S.A.",
                "Importadora Global",
                "Comercial Andina",
                "Suministros Industriales",
                "Distribución Nacional",
                "Proveedora del Sur",
                "Empresa Central de Abasto",
                "Logística y Distribución",
                "Comercial Metropolitana",
                "Proveedores Especializados",
                "Grupo de Distribución",
                "Servicios Comerciales",
                "Distribuidora Premium",
                "Proveedora de Confianza",
                "Importaciones Directas",
                "Comercio Mayorista",
                "Distribuidora Express",
                "Proveedora Regional",
                "Centro de Distribución"
            };

            String[] contactos = {
                "Juan García",
                "María López",
                "Carlos Rodríguez",
                "Ana Martínez",
                "Pedro Fernández",
                "Rosa González",
                "Luis Sánchez",
                "Elena Jiménez",
                "Miguel Díaz",
                "Laura Moreno",
                "Antonio Ruiz",
                "Isabel Flores",
                "Francisco Torres",
                "Carmen Ramos",
                "José Vargas",
                "Beatriz Herrera",
                "Fernando Mendoza",
                "Patricia Silva",
                "Ricardo Campos",
                "Mónica Castro"
            };

            String[] telefonos = {
                "555-0101", "555-0102", "555-0103", "555-0104", "555-0105",
                "555-0106", "555-0107", "555-0108", "555-0109", "555-0110",
                "555-0111", "555-0112", "555-0113", "555-0114", "555-0115",
                "555-0116", "555-0117", "555-0118", "555-0119", "555-0120"
            };

            for (int i = 1; i <= 20; i++) {
                Proveedor p = new Proveedor();
                p.setID(i);
                p.setCodigo("PROV-" + String.format("%05d", i));
                p.setNombre(empresas[i - 1]);
                p.setNombreContacto(contactos[i - 1]);
                p.setDireccion("Calle Principal " + i + ", Apartado " + (100 + i));
                p.setTelefono1(telefonos[i - 1]);
                p.setTelefono2("555-" + String.format("%04d", (2000 + i)));
                p.setCorreo("contacto" + i + "@empresa.com");
                p.setFechaRegistro("2024-" + String.format("%02d", ((i % 12) + 1)) + "-15");
                p.setNota("Proveedor " + i + " - Datos de prueba. Empresa confiable con buenas referencias.");

                tabla.addRow(p);
            }

            tabla.setEnabled(true);
            tabla.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });

        thread.start();
    }

    /**
     * Actualiza un proveedor existente en la tabla o lo agrega si es nuevo.
     * Si el proveedor tiene ID (no es null), busca la fila y la actualiza.
     * Si el proveedor no tiene ID, agrega una nueva fila.
     * @param p el Proveedor a actualizar o agregar
     */
    public void updateOrAddProveedor(Proveedor p) {
        if (p == null) return;

        // Si tiene ID, es un proveedor existente -> actualizar
        if (p.getID() != null) {
            // Buscar la fila con ese ID en el modelo
            int rowIndex = -1;
            for (int i = 0; i < mpc.getListData().size(); i++) {
                Proveedor prov = mpc.getListData().get(i);
                if (prov.getID() != null && prov.getID().equals(p.getID())) {
                    rowIndex = i;
                    break;
                }
            }

            // Si encontramos la fila, actualizar
            if (rowIndex != -1) {
                tabla.editRow(p, rowIndex);
                tabla.refreshUI();
            }
        } else {
            // Si no tiene ID, es un nuevo proveedor -> agregar
            tabla.addRow(p);
            tabla.refreshUI();
        }
    }

    @Override
    public void updateUI(){
        Color bcol = PropiedadesSistema.getColor("back.color.dark");//new Color(190, 190, 190);
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            bcol =  PropiedadesSistema.getColor("back.color.light");//new Color(90, 90, 90);
        }

        if(tBuscar != null){
            tBuscar.setBackground(UIManager.getColor("TextField.background"));
            tBuscar.setForeground(UIManager.getColor("TextField.foreground"));
        }

        lupaText = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"buscar.png"),bcol, true));

        super.updateUI();
    }
}
