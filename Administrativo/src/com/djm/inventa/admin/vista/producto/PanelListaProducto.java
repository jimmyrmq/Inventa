package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.renderer.StatusIconProductRenderer;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.TextField;
import com.djm.ui.component.table.ModeloTabla;

import com.djm.inventa.admin.vista.component.Table;
import com.djm.util.FormatNumber;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelListaProducto extends JPanel implements ActionListener {
    private ModeloTabla<Producto> modelo;
    private ModelTableProductoCustom mpc;
    private Table<Producto> tabla;
    private TextField tBuscar;
    private final String carp = "16/";
    private ImageIcon lupaText;
    private JButton bDetalle;
    private JTextField tCodigo, tNombre, tNota, tPrecioVenta, tPrecioMayor, tPrecioEspecial, tCantDisponible, tStockCritico;
    private Producto producto =null;
    private JCheckBox sproducto,servicio;
    public PanelListaProducto(){
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        bDetalle = new JButton(CONSTANTS.LANG.getValue("button.detalle"));

        bDetalle.addActionListener((e)->{
            if(producto != null) {
                Producto producto1 = (Producto) producto.clone();
                clearForm();
                IPanelDesktop iPanelDesktop = new DesktopProducto();
                Global.panelDesktop.addVentana(iPanelDesktop, producto1);
            }
        });

        bDetalle.setEnabled(false);

        tBuscar();
        pTable();

        add(tBuscar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        add(pVista(), LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 5, 0, 0, 0, 0.0f, 1.0f));
        add(pDetalle(), LayoutPanel.constantePane(0, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 15, 0, 0, 0, 0.0f, 0.0f));
        add(bDetalle, LayoutPanel.constantePane(0, 4, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 15, 0, 0, 0, 0.0f, 0.0f));

    }
    private void tBuscar(){

        tBuscar = new TextField(23);

        tBuscar.putClientProperty("JTextField.placeholderText", CONSTANTS.LANG.getValue("producto.label.buscar_producto"));
        tBuscar.putClientProperty("JTextField.leadingIcon", lupaText);
        Dimension dim = new Dimension(280, 23);

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
                    cb = new Color(255,125,125);
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

        mpc = new ModelTableProductoCustom();
        modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 250);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadTable();

        tabla.setDefaultRenderer(EtiquetaComponent.class, new StatusIconProductRenderer(TipoEtiqueta.NONE, 2));

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    Producto prod = tabla.getSelectedItem();

                    if(prod!=null) {
                        fillDataProduct(prod);
                    }
                }
            }
        });
    }

    private JPanel pVista() {
        JPanel panel = new JPanel();//new GridBagLayout());
        panel.setOpaque(false);

        JLabel lMostrar = new JLabel(CONSTANTS.LANG.getValue("label.mostrar"));

        sproducto = new JCheckBox(CONSTANTS.LANG.getValue("label.producto"));
        servicio = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.servicios"));

        sproducto.setSelected(PropiedadesSistema.getBoolean("PanelProducto.producto"));
        servicio.setSelected(PropiedadesSistema.getBoolean("PanelProducto.servicio"));

        sproducto.setActionCommand("PRODUCTOS");
        servicio.setActionCommand("SERVICIOS");

        sproducto.addActionListener(this);
        servicio.addActionListener(this);

        panel.add(lMostrar);
        panel.add(sproducto);
        panel.add(servicio);

        return panel;
    }

    private JPanel pDetalle(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lCodigo = new JLabel(CONSTANTS.LANG.getValue("producto.label.codigo"));
        JLabel lNombre = new JLabel(CONSTANTS.LANG.getValue("producto.label.nombre"));
        JLabel lNota = new JLabel(CONSTANTS.LANG.getValue("producto.label.nota"));
        JLabel lPrecioVenta = new JLabel(CONSTANTS.LANG.getValue("producto.label.precioventa"));
        JLabel lPrecioMayor = new JLabel(CONSTANTS.LANG.getValue("producto.label.preciomayor"));
        JLabel lPrecioEspecial = new JLabel(CONSTANTS.LANG.getValue("producto.label.precioespecial"));
        JLabel lCantDisponible = new JLabel(CONSTANTS.LANG.getValue("producto.label.cantidad_disponible"));
        JLabel lStockCritico = new JLabel(CONSTANTS.LANG.getValue("producto.label.adv_stockcritico"));

        tCodigo = new JTextField(25);
        tNombre = new JTextField(25);
        tNota = new JTextField(25);
        tPrecioVenta = new JTextField(10);
        tPrecioMayor = new JTextField(10);
        tPrecioEspecial = new JTextField(10);
        tCantDisponible = new JTextField(10);
        tStockCritico = new JTextField(10);

        tCodigo.setEditable(false);
        tNombre.setEditable(false);
        tNota.setEditable(false);
        tPrecioVenta.setEditable(false);
        tPrecioMayor.setEditable(false);
        tPrecioEspecial.setEditable(false);
        tCantDisponible.setEditable(false);
        tStockCritico.setEditable(false);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lNombre, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lNota, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNota, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lPrecioVenta, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecioVenta, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lPrecioMayor, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecioMayor, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lPrecioEspecial, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecioEspecial, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lCantDisponible, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantDisponible, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lStockCritico, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tStockCritico, LayoutPanel.constantePane(1, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private void fillDataProduct(Producto producto){
        if(producto != null){
            this.producto = producto;
            tCodigo.setText(producto.getCodigo());
            tNombre.setText(producto.getNombre());
            tNota.setText(producto.getNota());
            tPrecioVenta.setText(FormatNumber.doubleToString(producto.getPrecio1()));
            tPrecioMayor.setText(FormatNumber.doubleToString(producto.getPrecio2()));
            tPrecioEspecial.setText(FormatNumber.doubleToString(producto.getPrecio3()));

            if(producto.getCantidadDisponible() != null)
                tCantDisponible.setText(String.valueOf(producto.getCantidadDisponible()));

            if(producto.getStockCritico() != null)
                tStockCritico.setText(String.valueOf(producto.getStockCritico()));

            bDetalle.setEnabled(true);
        }
    }

    private void clearForm(){
        this.producto = null;

        tCodigo.setText(null);
        tNombre.setText(null);
        tNota.setText(null);
        tPrecioVenta.setText(null);
        tPrecioMayor.setText(null);
        tPrecioEspecial.setText(null);
        tCantDisponible.setText(null);
        tStockCritico.setText(null);

        bDetalle.setEnabled(false);
    }

    private void loadTable(){
        tabla.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        Thread thread = new Thread(()-> {
            tabla.setEnabled(false);
            //JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);


            Random random = new Random();
            int randomNumber;
            boolean serv;
            for (int i = 1; i <= 10 ; i++) {
                Producto p1 = new Producto();
                p1.setID(i);
                serv = random.nextBoolean();

                p1.setCodigo(generateAlphanumericCode(10));
                p1.setNombre((serv?"Servicio ":"Producto ") + i);
                p1.setDisponible(random.nextBoolean());
                p1.setNoRequiereStock(serv);
                p1.setPrecioIncluyeImpuesto(random.nextBoolean());
                p1.setUtilidad(0);

                if(!serv) {
                    randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    p1.setCantidadDisponible(randomNumber);

                    randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    p1.setStockCritico(randomNumber);
                }

                tabla.addRow(p1);
            }

            aplicarFiltro();

            tabla.setEnabled(true);
            tabla.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });

        thread.start();
    }

    public void delProductoList(Producto producto){
        if(producto.getID() != null) {
            int row = getIndex(producto.getID());

            if (row != -1) {
                tabla.removeRow(row);
            }
        }
    }

    public void setProductoList(Producto producto){
        boolean newProd = true;
        if(producto.getID() != null) {
            int row = getIndex(producto.getID());

            if (row != -1) {
                newProd = false;
                tabla.editRow(producto, row);
            }
        }

        if(newProd) {
            tabla.addRow(producto);
        }
    }

    private int getIndex(int ID){

        int row = -1;

        List<Producto> productos = mpc.getListData();
        cont:
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getID() == ID) {
                row = i;
                break cont;
            }
        }

        return row;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Establecer el color y el grosor de la línea
        g.setColor(UIManager.getColor("ToolBar.separatorColor"));
        //g.fillRect(0, 0, 1, getHeight()); // Dibujar una línea vertical en el lado izquierdo
        g.drawLine(0, 0, 1, getHeight());
    }

    @Override
    public void updateUI(){
        Color bcol = new Color(190, 190, 190);
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            bcol = new Color(90, 90, 90);
        }

        if(tBuscar != null){
            tBuscar.setBackground(UIManager.getColor("TextField.background"));
            tBuscar.setForeground(UIManager.getColor("TextField.foreground"));
        }

        lupaText = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"buscar.png"),bcol, true));

        super.updateUI();
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public String generateAlphanumericCode(int length) {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom(); // Usar SecureRandom para una mejor aleatoriedad

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            code.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return code.toString();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if(ae.getSource() instanceof JCheckBox) {
            boolean disp = sproducto.isSelected();
            boolean serv = servicio.isSelected();

            if (action.equals("PRODUCTOS")) {
                PropiedadesSistema.setPropiedad("PanelProducto.producto", disp);
                CONSTANTS.CONFIG.setValue("panellistaproducto", disp);
            }
            else if (action.equals("SERVICIOS")) {
                PropiedadesSistema.setPropiedad("PanelProducto.servicio", serv);
                CONSTANTS.CONFIG.setValue("panellistaservicio", serv);
            }
            aplicarFiltro();
        }
    }

    private void aplicarFiltro(){

        boolean prod = sproducto.isSelected();//PropiedadesSistema.getBoolean("PanelProducto.mostrar_producto");
        boolean serv = servicio.isSelected();//PropiedadesSistema.getBoolean("PanelProducto.servicio");

        if(prod && serv){//Mostrar todo
            System.out.println("producto y servicio");
            tabla.getSorter().setRowFilter(null); // Muestra todas las filas
        }else {

            List<Producto> productos = mpc.getListData();
            List<RowFilter<DefaultTableModel, Integer>> filters = new ArrayList<>();

            if (prod && !serv) {
                filters.add(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        int rowIndex = entry.getIdentifier();
                        Producto p = productos.get(rowIndex);
                        return !p.isNoRequiereStock();
                    }
                });
            } else if (!prod && serv) {
                filters.add(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        int rowIndex = entry.getIdentifier();
                        Producto p = productos.get(rowIndex);
                        return p.isNoRequiereStock();
                    }
                });
            } else {
                filters.add(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        return false;
                    }
                });
            }

            RowFilter<DefaultTableModel, Integer> filter = RowFilter.andFilter(filters);
            tabla.soter(filter);
        }



        // Crear el filtro
        /*if (prod) {
            filters.add(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int rowIndex = entry.getIdentifier();
                    Producto p = productos.get(rowIndex);
                    return !p.isNoRequiereStock();
                }
            });
        }

        if (serv) {
            filters.add(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    int rowIndex = entry.getIdentifier();
                    Producto p = productos.get(rowIndex);
                    return p.isNoRequiereStock();
                }
            });
        }


        // Aplicar el filtro
        if (prod || serv) {
        } else {
            tabla.getSorter().setRowFilter(null); // Muestra todas las filas
        }*/
    }
}