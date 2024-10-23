package com.djm.inventa.admin.vista.stock;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.modelo.ProductoStock;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.util.Utils;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Table;
import com.djm.inventa.admin.vista.component.renderer.StatusIconProductRenderer;
import com.djm.inventa.admin.vista.component.renderer.TipoEtiqueta;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.vista.principal.IPanelDesktop;
import com.djm.inventa.admin.vista.producto.DesktopProducto;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.TextField;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PanelStock extends JPanel implements ActionListener {
    private final String ID = PropiedadesSistema.getString("Stock.ID");
    private ModelTableStockCustom mpc;
    private Table<Producto> tabla;
    private JTextField tBuscar;
    private ImageIcon lupaText;
    private JCheckBox stockCritico;
    private JButton bCerrar, bVerDetalle, bImprimir, bStockRapido;
    private JPopupMenu popupMenu;
    private JMenuItem agragrStock, editarStock;
    public PanelStock() {
        super(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // top, left, bottom, right

        pTable();
        tBuscar();

        stockCritico = new JCheckBox(CONSTANTS.LANG.getValue("label.stockcritico"));
        stockCritico.setActionCommand("STOCK_CRITICO");
        stockCritico.addActionListener(this);

        add(tBuscar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 1.0f, 0.0f));
        add(stockCritico, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 5, 0.0f, 0.0f));
        add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 0, 0, 0, 0.0f, 1.0f));
        add(pButton(), LayoutPanel.constantePane(0, 2, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 5, 5, 0, 5, 1.0f, 0.0f));

        loadTable();
    }

    private JPanel pButton(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        menuIngreso();

        bCerrar = new JButton(CONSTANTS.LANG.getValue("button.cerrar"));
        bImprimir = new JButton(CONSTANTS.LANG.getValue("button.imprimir"));
        bVerDetalle = new JButton(CONSTANTS.LANG.getValue("button.detalle"));
        bStockRapido = new JButton(CONSTANTS.LANG.getValue("button.stockRapido"));

        bStockRapido.setActionCommand("STOCK_RAPIDO");
        bCerrar.setActionCommand("CERRAR");
        bVerDetalle.setActionCommand("VER_DETALLE");
        bCerrar.addActionListener(this);
        bVerDetalle.addActionListener(this);

        bStockRapido.addActionListener(ae->{
            popupMenu.show(bStockRapido, 0,bStockRapido.getHeight());
        });

        cancelEsc();

        panel.add(bVerDetalle, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bImprimir, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bStockRapido, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 15, 0, 0, 1.0f, 0.0f));
        panel.add(bCerrar, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private void pTable(){

        mpc = new ModelTableStockCustom();

        ModeloTabla<ProductoStock> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 400);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //loadTable();

        tabla.setDefaultRenderer(EtiquetaComponent.class, new StatusIconProductRenderer(TipoEtiqueta.NONE, 7));

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    Producto prod = tabla.getSelectedItem();

                    if(prod!=null) {
                        verDetalle(prod);
                    }
                }
            }
        });

        tabla.addKeyListener(new KeyAdapter() {            @Override
        public void keyPressed(KeyEvent e) {
            /*if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                //eliminarProducto();
            }
            else */if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Producto prod = tabla.getSelectedItem();
                if(prod!=null) {
                    verDetalle(prod);
                }
            }
        }
        });
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

    private void menuIngreso(){
        popupMenu = new JPopupMenu();
        agragrStock = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.agregarstock"));
        editarStock = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.editarstock"));

        agragrStock.setActionCommand("AGREGAR_STOCK_RAPIDO");
        editarStock.setActionCommand("EDITAR_STOCK_RAPIDO");

        editarStock.addActionListener(this);
        agragrStock.addActionListener(this);

        popupMenu.add(agragrStock);
        popupMenu.add(editarStock);
    }

    private void verDetalle(Producto producto){
        Producto producto1 = (Producto) producto.clone();
        IPanelDesktop iPanelDesktop = new DesktopProducto();
        Global.panelDesktop.addVentana(iPanelDesktop, producto1);
    }

    @Override
    public void updateUI(){
        Color bcol = new Color(190, 190, 190);
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            bcol = new Color(90, 90, 90);
        }

        lupaText = new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/buscar.png"),bcol, true));

        if(popupMenu != null) {
            popupMenu.updateUI();
            agragrStock.updateUI();
            editarStock.updateUI();
        }

        super.updateUI();

    }

    private void loadTable(){
        tabla.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        Thread thread = new Thread(()-> {
            tabla.setEnabled(false);
            //JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);


            Random random = new Random();
            int randomNumber;
            boolean serv;
            int i = 0;
            do {
                serv = random.nextBoolean();
                if(serv) {
                    i++;

                    Producto p1 = new Producto();
                    p1.setCodigo(Utils.generateAlphanumericCode(10));
                    p1.setNombre("Producto " + i);
                    p1.setDisponible(true);
                    p1.setPrecioIncluyeImpuesto(random.nextBoolean());
                    p1.setNoRequiereStock(false);
                    randomNumber = random.nextInt(50) + 1; // Genera un número entre 1 y 1000
                    p1.setUtilidad(randomNumber);

                    randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    p1.setCantidadDisponible(randomNumber);

                    randomNumber = random.nextInt(1000) + 1; // Genera un número entre 1 y 1000
                    p1.setStockCritico(randomNumber);

                    p1.setID(i);
                    tabla.addRow(p1);
                }
            }while(i <= 10);

            //aplicarFiltro();

            tabla.setEnabled(true);
            tabla.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });

        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if("EDITAR_STOCK_RAPIDO".equals(action) || "AGREGAR_STOCK_RAPIDO".equals(action)){
            int row = tabla.getSelectedRow();
            if(row != -1){
                int modeloFila = tabla.convertRowIndexToModel(row);

                Producto prod = tabla.getSelectedItem();
                int cant = 0;
                boolean editar = "EDITAR_STOCK_RAPIDO".equals(action);

                if(editar)
                    cant = prod.getCantidadDisponible();

                StockRapidoGUI stock = new StockRapidoGUI(cant);
                if(stock.isAcept()){

                    if(editar){
                        cant = stock.getCantidadEntrante();
                    }else
                        cant += stock.getCantidadEntrante();

                    prod.setCantidadDisponible(cant);
                    tabla.editRow(prod, modeloFila);
                }
            }else{
                JOptionPane.showMessageDialog(GlobalFrame.getInstance().getFrame(),CONSTANTS.LANG.getValue("stock.error.select_editar"),
                        CONSTANTS.LANG.getValue("stock.label.titulorapido"), JOptionPane.ERROR_MESSAGE);
            }
        }
        else if("CERRAR".equals(action)){
            Global.panelDesktop.cerrarVentana(ID);
        }
        else if("VER_DETALLE".equals(action)){
            int row = tabla.getSelectedRow();
            if(row != -1) {
                int modeloFila = tabla.convertRowIndexToModel(row);
                Producto prod = tabla.getSelectedItem();
                verDetalle(prod);
            }else{
                JOptionPane.showMessageDialog(GlobalFrame.getInstance().getFrame(),CONSTANTS.LANG.getValue("producto.tabla.seleccione"),
                        CONSTANTS.LANG.getValue("stock.label.titulorapido"), JOptionPane.ERROR_MESSAGE);
            }

        }
        else if("STOCK_CRITICO".equals(action)){

            boolean stckCrit = stockCritico.isSelected();
            if(stckCrit){
                List<Producto> productos = mpc.getListData();
                List<RowFilter<DefaultTableModel, Integer>> filters = new ArrayList<>();

                filters.add(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        int rowIndex = entry.getIdentifier();
                        Producto producto = productos.get(rowIndex);

                        boolean rtn =  producto.getCantidadDisponible() < producto.getStockCritico();

                        return rtn;
                    }
                });

                RowFilter<DefaultTableModel, Integer> filter = RowFilter.andFilter(filters);
                tabla.soter(filter);
            }else {
                tabla.getSorter().setRowFilter(null); // Muestra todas las filas
            }
        }
    }

    public void insertData(Producto producto) {
        int row = -1;
        List<Producto> productos = mpc.getListData();
        cont:
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getID() == producto.getID()) {
                row = i;
                break cont;
            }
        }

        if (row == -1) {
            tabla.addRow(producto);
        }
        else{
            tabla.editRow(producto, row);
        }

    }
    private void cancelEsc() {
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                Global.panelDesktop.cerrarVentana(ID);
            }
        };
        InputMap inputMap = bCerrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CANCELAR_CLEAR");
        ActionMap actionMap = bCerrar.getActionMap();
        actionMap.put("CANCELAR_CLEAR", action);
    }
}
