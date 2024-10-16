package com.djm.inventa.admin.vista.producto;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.modelo.Categoria;
import com.djm.inventa.admin.modelo.Marca;
import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.modelo.Proveedor;
import com.djm.inventa.admin.modelo.Stock;
import com.djm.inventa.admin.util.BorderUtil;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.TextField;
import com.djm.inventa.admin.vista.component.TextArea;
import com.djm.ui.component.Button;
import com.djm.ui.component.ToggleButton;
import com.djm.util.FormatNumber;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelProducto extends JPanel {
    private TextField tCodigo,tCodigoBarra, tNombre,tUnidadMedida, tModelo, tSerie, tCosto,tPrecio1,tPrecio2,tPrecio3, tStockCritico,
            tCantidadDisponible, tUtilidad;
    private Button bAddMarca, bAddCantidad, bAddProveedor, bBuscar;
    private JButton bGuardar, bCancelar;
    private JLabel lUtilidadAdv, lPrecio1Adv, lPrecio2Adv, lPrecio3Adv,lInfo;
    private ToggleButton bCodigoBarra;
    private TextArea tNota;
    private JComboBox<Proveedor> cbProveedor;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Marca> cbMarca;
    private DefaultComboBoxModel<Proveedor> dcbProveedor;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private DefaultComboBoxModel<Marca> dcbMarca;
    private final Dimension CDDIM = new Dimension(227,23);
    private JCheckBox disponible, noRequiereStock, precioImpuesto;
    private Color greenButton = new Color(77, 170, 71);
    private LostFocusPrecio lostFocusPrecio = new LostFocusPrecio();
    private ProcesadorProducto procesadorProducto = new ProcesadorProducto();
    private Color color1 = UIManager.getColor("Panel.background");
    private Color color2 = UIManager.getColor("TextField.background");
    private Color color3 = UIManager.getColor("TextField.foreground");
    private JPanel pDetalles, pPrecio, pStock;
    //private ImageIcon iok, icancel;
    private final ProductoListener productoListener = new ProductoListener();
    private Producto producto = null;

    public PanelProducto(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        pDetalles = panelDatelle();
        pPrecio = panelPrecio();
        pStock = panelStock();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        panel.add(pDetalles, LayoutPanel.constantePane(0, 1, 1, 3, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 10, 0, 0.0f, 0.0f));
        panel.add(pPrecio, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        panel.add(pStock, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        panel.add(getPanelButton(), LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 10, 0, 10, 1.0f, 1.0f));

        add(panel, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
    }


    public JPanel getPanelButton(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        //iok = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/ok16.png") ,color3,false));
        //icancel = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/closed.png") ,color3,false));

        bGuardar = new JButton(CONSTANTS.LANG.getValue("button.guardar"));//, iok);//,"F5",null);//,new ImageIcon("com.djm.inventa.icon/ok.png"));
        bCancelar = new JButton(CONSTANTS.LANG.getValue("button.cancelar"));//, icancel);//,new ImageIcon("com.djm.inventa.icon/close.png"));
        //bGuardar.setForeground(new Color(66, 89, 147));

        bCancelar.setActionCommand("BUTTON_CANCELAR");
        bGuardar.setActionCommand("BUTTON_GUARDAR");

        lInfo = new JLabel();
        lInfo.setIcon(Image.getIcon("info.png"));
        lInfo.setVisible(false);

        cancelEsc();
        guardarF5();

        bGuardar.addActionListener(productoListener);
        bCancelar.addActionListener(productoListener);

        bGuardar.setFocusable(true);

        panel.add(bCancelar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LAST_LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(bGuardar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LAST_LINE_END, 5, 5, 0, 0, 0.0f, 1.0f));

        return panel;
    }

    private JPanel panelDatelle() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.detalle")));

        JLabel lCodigo = new JLabel(CONSTANTS.LANG.getValue("producto.label.codigo"));
        JLabel lCodigoBarra = new JLabel(CONSTANTS.LANG.getValue("producto.label.codigobarra"));
        JLabel lNombre = new JLabel(CONSTANTS.LANG.getValue("producto.label.nombre"));
        JLabel lUnidad= new JLabel(CONSTANTS.LANG.getValue("producto.label.unidad"));
        JLabel lModelo= new JLabel(CONSTANTS.LANG.getValue("producto.label.modelo"));
        JLabel lSerie= new JLabel(CONSTANTS.LANG.getValue("producto.label.serie"));
        JLabel lMarca = new JLabel(CONSTANTS.LANG.getValue("producto.label.marca"));
        JLabel lCategoria = new JLabel(CONSTANTS.LANG.getValue("producto.label.categoria"));
        JLabel lProveedor = new JLabel(CONSTANTS.LANG.getValue("producto.label.proveedor"));
        JLabel lNota= new JLabel(CONSTANTS.LANG.getValue("producto.label.nota"));

        bBuscar = new Button(Image.getIcon("20/ok.png"),true);
        bBuscar.setPaintBack(false);
        bBuscar.setColorFilter(greenButton);
        bBuscar.setActionCommand("BUSCAR_PRODUCTO");
        //bBuscar.addActionListener(this);
        bBuscar.setButtonIcon(true);
        bBuscar.setToolTipText(CONSTANTS.LANG.getValue("producto.button.buscar"));
        bBuscar.setFocusable(false);
        bBuscar.setColorBackIn(color1);
        bBuscar.setColorBackSelected(color2);

        bCodigoBarra = new ToggleButton(Image.getIcon("barcode.png"));
        bCodigoBarra.setToolTipText(CONSTANTS.LANG.getValue("producto.inf.buscarporcodbarra"));
        bCodigoBarra.setColorIn(color2);
        bCodigoBarra.setColorFilter(color3);
        bCodigoBarra.setFocusable(false);

        bCodigoBarra.addActionListener((e)->{
            boolean edo = bCodigoBarra.isSelected();
            String value = edo?"producto.inf.buscarporcodbarra":"producto.inf.buscarcodigo";
            tCodigo.setPlaceHolder(CONSTANTS.LANG.getValue(value));
            tCodigo.requestFocus();
        });

        bAddMarca = new Button(Image.getIcon("16/add.png"),true);
        bAddMarca.setPaintBack(false);
        bAddMarca.setColorFilter(greenButton);
        bAddMarca.setForeground(Color.RED);
        bAddMarca.setActionCommand("ADD_MARCA_PRODUCTO");
        //bAddMarca.addActionListener(this);
        bAddMarca.setButtonIcon(true);
        bAddMarca.setToolTipText(CONSTANTS.LANG.getValue("marca.add"));
        bAddMarca.setFocusable(false);
        bAddMarca.setColorBackIn(color1);
        bAddMarca.setColorBackSelected(color2);

        bAddProveedor = new Button(Image.getIcon("16/add.png"));
        bAddProveedor.setPaintBack(false);
        bAddProveedor.setColorFilter(greenButton);
        bAddProveedor.setActionCommand("ADD_PROVEEDOR");
        //bAddProveedor.addActionListener(this);
        bAddProveedor.setButtonIcon(true);
        bAddProveedor.setToolTipText(CONSTANTS.LANG.getValue("proveedor.add"));
        bAddProveedor.setFocusable(false);
        bAddProveedor.setColorBackIn(color1);
        bAddProveedor.setColorBackSelected(color2);

        tCodigo = new TextField(20,20);
        tCodigoBarra = new TextField(27,40,true);
        tNombre = new TextField(27,50);
        tUnidadMedida = new TextField(5,10);
        tModelo = new TextField(27,20);
        tSerie = new TextField(27,20);

        tCodigo.setToolTipText(CONSTANTS.LANG.getValue("producto.inf.buscarcodigo"));
        tCodigo.setPlaceHolder(CONSTANTS.LANG.getValue("producto.inf.buscarcodigo"));

        Border border = tCodigo.getBorder();
        Color color = tCodigo.getSelectionColor();
        Color colBack = tCodigo.getBackground();

        tNota = new TextArea(2,27);
        tNota.setBackground(colBack);
        tNota.setLimitText(512);
        tNota.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Ignorar Enter (KeyEvent.VK_ENTER) y Tab (KeyEvent.VK_TAB)
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    e.consume(); // Ignorar el evento
                    //tNota.requestFocusInWindow();
                    //tCosto.requestFocus();//>>>>>>>
                }
            }
        });

        JScrollPane jsp = new JScrollPane(tNota, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(border);
        jsp.getViewport().setOpaque(true);
        jsp.getViewport().setBackground(colBack);
        jsp.setBackground(colBack);
        jsp.setOpaque(false);
        jsp.setBorder(null);

        tNota.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1, color);
                jsp.setViewportBorder(matte);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
                jsp.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //Border matte  = BorderFactory.createMatteBorder(1, 1, 1, 1,GlobalUI.getInstance().getTheme().getTextUI().getColorBorder());// GlobalUI.getInstance().getTheme().getColorBorderField());
                jsp.setViewportBorder(border);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
                jsp.repaint();
            }
        });

        dcbProveedor = new DefaultComboBoxModel<Proveedor> ();

        cbProveedor = new JComboBox<>(dcbProveedor);
        cbProveedor.setPreferredSize(CDDIM);

        dcbCategoria = new DefaultComboBoxModel<Categoria> ();

        cbCategoria = new JComboBox<>(dcbCategoria);
        cbCategoria.setPreferredSize(CDDIM);

        dcbMarca = new DefaultComboBoxModel<Marca> ();

        cbMarca = new JComboBox<>(dcbMarca);
        cbMarca.setPreferredSize(CDDIM);

        disponible = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.habilitado"));
        disponible.setOpaque(false);
        disponible.setSelected(true);

        noRequiereStock = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.no_requiere_stock"));
        noRequiereStock.setOpaque(false);
        noRequiereStock.setActionCommand("NO_REQUERE_STOCK");
        //noRequiereStock.addActionListener(this);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bBuscar, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 4, 0, 0, 0.0f, 0.0f));
        panel.add(bCodigoBarra, LayoutPanel.constantePane(3, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 3, 0, 0, 0.0f, 0.0f));

        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(1, 1, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lNombre, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 2, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUnidad, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUnidadMedida, LayoutPanel.constantePane(1, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lModelo, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tModelo, LayoutPanel.constantePane(1, 4, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lSerie, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tSerie, LayoutPanel.constantePane(1, 5, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lMarca, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbMarca, LayoutPanel.constantePane(1, 6, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddMarca, LayoutPanel.constantePane(4, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 3, 0, 0, 0.0f, 0.0f));

        panel.add(lCategoria, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 7, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lProveedor, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbProveedor, LayoutPanel.constantePane(1, 8, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddProveedor, LayoutPanel.constantePane(4, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 3, 0, 0, 0.0f, 0.0f));

        panel.add(disponible, LayoutPanel.constantePane(1, 9, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(noRequiereStock, LayoutPanel.constantePane(1, 10, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lNota, LayoutPanel.constantePane(0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(jsp, LayoutPanel.constantePane(1, 11, 4, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 1.0f, 1.0f));

        return panel;
    }

    private JPanel panelPrecio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.precio")));

        JLabel lPrecioCosto = new JLabel(CONSTANTS.LANG.getValue("producto.label.preciocompra"));
        JLabel  lUtilidad= new JLabel(CONSTANTS.LANG.getValue("producto.label.utilidad"));
        JLabel  lPrecio1= new JLabel(CONSTANTS.LANG.getValue("producto.label.precioventa"));
        JLabel  lPrecio2= new JLabel(CONSTANTS.LANG.getValue("producto.label.preciomayor"));
        JLabel  lPrecio3= new JLabel(CONSTANTS.LANG.getValue("producto.label.precioespecial"));

        precioImpuesto = new JCheckBox(CONSTANTS.LANG.getValue("producto.label.precioIncluyeImpuesto"));
        precioImpuesto.setSelected(true);
        precioImpuesto.setOpaque(false);
        //precioImpuesto.addActionListener(this);
        precioImpuesto.setActionCommand("PRECIO_IMPUESTO");

        tCosto = new TextField(10,10);
        tUtilidad=new TextField(10,3,true);
        tPrecio1 = new TextField(10,10);
        tPrecio2 = new TextField(10,10);
        tPrecio3 = new TextField(10,10);

        lUtilidadAdv = new JLabel(Image.getIcon("warning22.png"));
        lPrecio1Adv = new JLabel(Image.getIcon("warning22.png"));
        lPrecio2Adv = new JLabel(Image.getIcon("warning22.png"));
        lPrecio3Adv = new JLabel(Image.getIcon("warning22.png"));

        InfoListener infoListener = new InfoListener();
        lUtilidadAdv.addMouseListener(infoListener);
        lPrecio1Adv.addMouseListener(infoListener);
        lPrecio2Adv.addMouseListener(infoListener);
        lPrecio3Adv.addMouseListener(infoListener);

        lUtilidadAdv .setVisible(false);
        lPrecio1Adv .setVisible(false);
        lPrecio2Adv.setVisible(false);
        lPrecio3Adv.setVisible(false);

        lUtilidadAdv.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_utildadexcesiva"));
        lPrecio1Adv.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));
        lPrecio2Adv.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));
        lPrecio3Adv.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));

        tPrecio1.addFocusListener(lostFocusPrecio);
        tPrecio2.addFocusListener(lostFocusPrecio);
        tPrecio3.addFocusListener(lostFocusPrecio);

        new ListenerMoneda(tCosto,10);
        new ListenerMoneda(tPrecio1,10);
        new ListenerMoneda(tPrecio2,10);
        new ListenerMoneda(tPrecio3,10);

        tUtilidad.setActionCommand("UTILIDAD");
        //tUtilidad.addActionListener(this);

        tCosto.setText("0,00");
        tUtilidad.setText("0");
        tPrecio1.setText("0,00");
        tPrecio2.setText("0,00");
        tPrecio3.setText("0,00");

        tUtilidad.addFocusListener(new FocusListener(){
            public void focusLost(FocusEvent fe){
                if(procesadorProducto.calculoUtilidadPrecio(tUtilidad, lUtilidadAdv, tCosto,tPrecio1)){
                    procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);
                }
            }
            public void focusGained(FocusEvent fe){
                tUtilidad.selectAll();
            }
        });

        tCosto.addFocusListener(new FocusListener(){
            public void focusLost(FocusEvent fe){
                procesadorProducto.calculoUtilidadPrecio(tUtilidad, lUtilidadAdv, tCosto,tPrecio1);
            }
            public void focusGained(FocusEvent fe){
                tCosto.selectAll();
            }
        });

        tPrecio1.addFocusListener(new FocusListener(){
            public void focusLost(FocusEvent fe){
                procesadorProducto.calculoPrecioUtilidad( tCosto, tUtilidad, lUtilidadAdv, tPrecio1);
            }
            public void focusGained(FocusEvent fe){
                tPrecio1.selectAll();}
        });

        panel.add(lPrecioCosto, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCosto, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUtilidad, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tUtilidad, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lUtilidadAdv, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio1, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio1Adv, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio2, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2Adv, LayoutPanel.constantePane(2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio3, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3Adv, LayoutPanel.constantePane(2, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(precioImpuesto, LayoutPanel.constantePane(1, 5, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 1.0f, 0.0f));

        return panel;
    }
    private JPanel panelStock() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBackground(Color.RED);
        panel.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.stock")));

        bAddCantidad = new Button(Image.getIcon("16/add.png"));
        bAddCantidad.setPaintBack(false);
        bAddCantidad.setColorFilter(greenButton);
        bAddCantidad.setActionCommand("ADD_CANTIDAD_PRODUCTO");
        //bAddCantidad.addActionListener(this);
        bAddCantidad.setButtonIcon(true);
        bAddCantidad.setToolTipText(CONSTANTS.LANG.getValue("stock.add"));
        bAddCantidad.setFocusable(false);
        bAddCantidad.setColorBackIn(color1);
        bAddCantidad.setColorBackSelected(color2);

        JLabel lAdvertencia = new JLabel(CONSTANTS.LANG.getValue("producto.label.stockcritico"));
        JLabel lDisponible = new JLabel(CONSTANTS.LANG.getValue("producto.label.cantidad_disponible"));

        tStockCritico = new TextField(7,10,true);
        tStockCritico.setText("0");
        tStockCritico.setName("STOCK_ENTER");

        tCantidadDisponible = new TextField(7);
        tCantidadDisponible.setEditable(false);
        tCantidadDisponible.setText("0");
        //tCantidadDisponible.addActionListener(this);
        tCantidadDisponible.setActionCommand("STOCK_ENTER");

        panel.add(lDisponible, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadDisponible, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddCantidad, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 0.0f));
        panel.add(lAdvertencia, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStockCritico, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }
   /* private JPanel pTitulo(){
        Color color = new Color(48, 103, 222);//UIManager.getColor("TextField.background");
        Font aux = UIManager.getFont("Label.font");
        Font font = aux.deriveFont(20f);

        JPanel panel= new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(color);

        JLabel label = new JLabel(CONSTANTS.LANG.getValue("producto.label.titulo"));
        label.setFont(font);
        label.setForeground(Color.WHITE);

        panel.add(label, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 3, 3, 3, 0, 1.0f, 1.0f));

        return panel;
    }*/
    protected void clear(){
        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));

        this.producto = null;

        tPrecio1.borderNoError();
        tPrecio2.borderNoError();
        tPrecio3.borderNoError();

        //tableProductGUI.enabledPane(true);

        //isEditingProduct = false;

        tCodigo.setText(null);
        tCodigoBarra.setText(null);
        tNombre.setText(null);
        tUnidadMedida.setText(null);
        tModelo.setText(null);
        tSerie.setText(null);

        disponible.setSelected(true);
        noRequiereStock.setSelected(false);

        tStockCritico.setEditable(true);
        bAddCantidad.setEnabled(true);

        tNota.setText(null);
        tCosto.setText("0,00");
        tUtilidad.setText("0");

        tPrecio1.setText("0,00");
        tPrecio2.setText("0,00");
        tPrecio3.setText("0,00");

        lInfo.setText(null);
        lInfo.setVisible(false);

        lUtilidadAdv.setVisible(false);
        lPrecio1Adv.setVisible(false);
        lPrecio2Adv.setVisible(false);
        lPrecio3Adv.setVisible(false);

        tPrecio1.setToolTipText(null);
        tPrecio2.setToolTipText(null);
        tPrecio3.setToolTipText(null);

        precioImpuesto.setSelected(true);

        if(cbCategoria.getItemCount() > 0)
            cbCategoria.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        if(cbProveedor.getItemCount() > 0)
            cbProveedor.setSelectedIndex(0);

        tStockCritico.setText("0");
        tCantidadDisponible.setText("0");

        tCodigo.requestFocus();

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public Producto getProducto(){
        if(this.producto != null)
            this.producto = new Producto();

        String cod = tCodigo.getText();
        String codBarra = tCodigoBarra.getText();
        String nombre = tNombre.getText();
        String unidMed = tUnidadMedida.getText();
        String modelo = tModelo.getText();
        String serie = tSerie.getText();
        boolean disp = disponible.isSelected();
        boolean noReqStock = noRequiereStock.isSelected();
        String nota = tNota.getText();
        String costo = tCosto.getText();
        String utilidad = tUtilidad.getText();
        String precio1 = tPrecio1.getText();
        String precio2 = tPrecio2.getText();
        String precio3 = tPrecio3.getText();
        boolean isImpuesto = precioImpuesto.isSelected();

        if((cod == null || cod.trim().isEmpty()) && (codBarra != null &&!codBarra.trim().isEmpty())){
            cod = codBarra;
            tCodigo.setText(cod);
        }

        Marca marca = (Marca)dcbMarca.getSelectedItem();
        Categoria categoria = (Categoria) dcbCategoria.getSelectedItem();
        Proveedor proveedor = (Proveedor)dcbProveedor.getSelectedItem();

        String stockCrititco = tStockCritico.getText();

        Double c = 0.0;
        if(costo!=null && !costo.trim().isEmpty()) {
            c = FormatNumber.stringToDouble(costo);
        }

        Double p1 = 0.0;
        if(precio1!= null && !precio1.trim().isEmpty()) {
            p1 = FormatNumber.stringToDouble(precio1);
        }

        Double p2 = 0.0;
        if(precio2!= null && !precio2.trim().isEmpty()){
            p2 = FormatNumber.stringToDouble(precio2);
        }

        Double p3 = 0.0;
        if(precio3!= null && !precio3.trim().isEmpty()) {
            p3 = FormatNumber.stringToDouble(precio3);
        }

        Integer scritico = 0;
        if(stockCrititco !=null && !stockCrititco.trim().isEmpty()) {
            scritico = Integer.parseInt(stockCrititco);
        }

        int util = 0;
        if(utilidad != null && !utilidad.trim().isEmpty()) {
            try {
                util = Integer.parseInt(utilidad);
            }catch (NumberFormatException exc){
                LoggerApp.error("Error al tratar de convertir la utilidad en entero: "+exc.getMessage());
            }
        }

        producto.setCodigo(cod);
        producto.setCodigoBarra(codBarra);
        producto.setNombre(nombre);
        producto.setUnidadMedida(unidMed);
        producto.setModelo(modelo);
        producto.setSerie(serie);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        producto.setDisponible(disp);
        producto.setNoRequiereStock(noReqStock);
        producto.setNota(nota);
        producto.setUtilidad(util);
        producto.setPrecioCosto(c);
        producto.setPrecio1(p1);
        producto.setPrecio2(p2);
        producto.setPrecio3(p3);
        producto.setPrecioIncluyeImpuesto(isImpuesto);
        producto.setStockCritico(scritico);
        producto.setProveedor(proveedor);

        return this.producto;
    }

    public void insertProducto(Producto producto, Stock stock){
        this.producto = producto;

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));

        eText(false);

        tCodigo.setText(producto.getCodigo());
        tCodigoBarra.setText(producto.getCodigoBarra());
        tNombre.setText(producto.getNombre());
        tUnidadMedida.setText(producto.getUnidadMedida());
        tModelo.setText(producto.getModelo());
        tSerie.setText(producto.getSerie());
        tCosto.setText(FormatNumber.doubleToString(producto.getPrecioCosto()));
        tUtilidad.setText(String.valueOf(producto.getUtilidad()));
        tPrecio1.setText(FormatNumber.doubleToString(producto.getPrecio1()));
        tPrecio2.setText(FormatNumber.doubleToString(producto.getPrecio2()));
        tPrecio3.setText(FormatNumber.doubleToString(producto.getPrecio3()));
        tStockCritico.setText(String.valueOf(producto.getStockCritico()));
        disponible.setSelected(producto.isDisponible());
        noRequiereStock.setSelected(producto.isNoRequiereStock());
        precioImpuesto.setSelected(producto.isPrecioIncluyeImpuesto());
        tNota.setText(producto.getNota());

        Marca marca = producto.getMarca();
        Categoria categoria = producto.getCategoria();
        Proveedor proveedor = producto.getProveedor();

        int sizeList;

        if(categoria != null) {
            sizeList = dcbCategoria.getSize();
            cont:
            for (int i = 0; i < sizeList; i++) {
                Categoria c = dcbCategoria.getElementAt(i);
                if (c.getID() == categoria.getID()) {
                    cbCategoria.setSelectedIndex(i);
                    break cont;
                }
            }
        }

        if(marca != null) {
            sizeList = dcbMarca.getSize();
            cont:
            for (int i = 0; i < sizeList; i++) {
                Marca m = dcbMarca.getElementAt(i);
                if (m.getID() == marca.getID()) {
                    cbMarca.setSelectedIndex(i);
                    break cont;
                }
            }
        }

        if(proveedor != null) {
            sizeList = dcbProveedor.getSize();
            cont:
            for (int i = 0; i < sizeList; i++) {
                Proveedor prod = dcbProveedor.getElementAt(i);
                if (prod.getID() == proveedor.getID()) {
                    cbProveedor.setSelectedIndex(i);
                    break cont;
                }
            }
        }

        boolean excesivo = producto.getUtilidad() > 100;
        tUtilidad.setForeground(excesivo ? Color.RED : UIManager.getColor("Textfield.foreground"));
        lUtilidadAdv.setVisible(excesivo);

        procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);

        if (stock != null) {
            tCantidadDisponible.setText(String.valueOf(stock.getCantidad()));
        }

        eText(true);

        tNombre.requestFocus();

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void eText(boolean enabled){
        tCodigo.setEnabled(enabled);
        bBuscar.setEnabled(enabled);

        tCodigoBarra.setEnabled(enabled);
        tNombre.setEnabled(enabled);
        tUnidadMedida.setEnabled(enabled);
        tModelo.setEnabled(enabled);
        tSerie.setEnabled(enabled);

        cbMarca.setEnabled(enabled);
        bAddMarca.setEnabled(enabled);
        cbCategoria.setEnabled(enabled);
        cbProveedor.setEnabled(enabled);

        disponible.setEnabled(enabled);
        noRequiereStock.setEnabled(enabled);
        tNota.setEnabled(enabled);

        tCosto.setEnabled(enabled);
        tUtilidad.setEnabled(enabled);
        tPrecio1.setEnabled(enabled);
        tPrecio2.setEnabled(enabled);
        tPrecio3.setEnabled(enabled);
        precioImpuesto.setEnabled(enabled);

        tCantidadDisponible.setEnabled(enabled);
        tStockCritico.setEnabled(enabled);
        bAddCantidad.setEnabled(enabled);
    }


    private class LostFocusPrecio implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            //procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);
        }
    }
    private void cancelEsc() {
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        };
        InputMap inputMap = bCancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CANCELAR_CLEAR");
        ActionMap actionMap = bCancelar.getActionMap();
        actionMap.put("CANCELAR_CLEAR", action);
    }

    private void guardarF5() {
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_F5,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                //guardarProducto();
            }
        };
        InputMap inputMap = bCancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "GUARDAR_PRODUCTO");
        ActionMap actionMap = bCancelar.getActionMap();
        actionMap.put("GUARDAR_PRODUCTO", action);
    }


    @Override
    public void updateUI(){
        super.updateUI();

        color1 = UIManager.getColor("Panel.background");
        color2 = UIManager.getColor("TextField.background");
        color3 = UIManager.getColor("TextField.foreground");

        if(pDetalles != null)
            pDetalles.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.detalle")));

        if(pStock != null)
            pStock.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.stock")));

        if(pPrecio != null)
            pPrecio.setBorder(new BorderUtil(CONSTANTS.LANG.getValue("producto.border.title.precio")));

        Color colButton = PropiedadesSistema.getColor("Label.colorDarker");
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = color3;
        }

        if(bCodigoBarra != null)
            bCodigoBarra.setColorFilter(colButton);

        /*iok = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/ok16.png") ,colButton,false));
        icancel = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/closed.png") ,colButton,false));

        if(bCancelar != null) {
            bCancelar.setIcon(icancel);
            bCancelar.updateUI();
        }

        if(bGuardar != null) {
            bGuardar.setIcon(iok);
            bGuardar.updateUI();
        }*/

        revalidate();
        repaint();
    }
}
