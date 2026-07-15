package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.persistence.CategoriaDAO;
import com.djm.inventa.producto.persistence.MarcaDAO;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.inventa.ui.util.BorderUtil;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.ui.component.TextField;
import com.djm.inventa.ui.component.TextArea;
import com.djm.inventa.ui.util.InfoListener;
import com.djm.inventa.util.FechaUtil;
import com.djm.inventa.util.NumberUtils;
import com.djm.inventa.util.MonedaDocument;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.GlobalFrame;
import com.djm.ui.LayoutPanel;
import com.djm.ui.component.Button;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.OptionPane;
import com.djm.ui.component.ToggleButton;
import com.djm.util.NumberConverter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PanelProducto{
    private JPanel panelPrincipal;

    private TextField tCodigo,tCodigoBarra, tNombre,tUnidadMedida, tModelo,
            tSerie, tCosto,tPrecio1,tPrecio2,tPrecio3,tCantMayor, tStockCritico,
            tCantidadDisponible, tUtilidad, tFechaActualizacion, tFechaCreacion;
    private Button bAddMarca, bAddCantidad, bEnter, bBuscar;
    private JButton bGuardar, bCancelar, bEliminar;
    private JLabel lUtilidadAdv, lPrecio1Adv, lPrecio2Adv, lPrecio3Adv,lInfo;
    private ToggleButton bCodigoBarra;
    private TextArea tNota;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Marca> cbMarca;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private DefaultComboBoxModel<Marca> dcbMarca;
    private JCheckBox disponible, noRequiereStock, precioImpuesto, requiereAprob, movimientoNegativo;
    private Color greenButton = new Color(77, 170, 71);
    private LostFocusPrecio lostFocusPrecio = new LostFocusPrecio();
    private ProcesadorProducto procesadorProducto = new ProcesadorProducto();
    private Color color1 = UIManager.getColor("Panel.background");
    private Color color2 = UIManager.getColor("TextField.background");
    private Color color3 = UIManager.getColor("TextField.foreground");
    private JPanel pDetalles, pPrecio, pStock;
    private ImageIcon iDel, iok, icancel;;//
    private JPopupMenu popupMenu;
    private JMenuItem agragrStock, editarStock;

    private final IPanelDataAction iPanelDataAction;

    public PanelProducto(IPanelDataAction iPanelDataAction){
        this.iPanelDataAction = iPanelDataAction;

        panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            public void updateUI(){

                color1 = UIManager.getColor("Panel.background");
                color2 = UIManager.getColor("TextField.background");
                color3 = UIManager.getColor("TextField.foreground");

                if(pDetalles != null)
                    pDetalles.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.detalle")));

                if(pStock != null)
                    pStock.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.stock")));

                if(pPrecio != null)
                    pPrecio.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

                Color colButton = AppContext.getInstance().getColor("Label.colorDarker");

                if(colButton == null || "LIGTH".equals(PropiedadesLookAndFeel.getPropiedad("Apariencia.lookandfeel"))){
                    colButton = color3;
                }


                if(bCodigoBarra != null)
                    bCodigoBarra.setColorFilter(colButton);

                //iDel = IconManager.getIcon(getClass().getResource("/icon/16/delete2.png"));//new ImageIcon(ColorFilter.filterImage( IconManager.get("16/delete2.png") ,colButton,false));
                iDel = new ImageIcon(ColorFilter.filterImage( IconManager.get16("delete2") ,colButton,false));
                if(bEliminar != null){
                    bEliminar.setIcon(iDel);
                    bEliminar.updateUI();
                }

                if(popupMenu != null) {
                    popupMenu.updateUI();
                    agragrStock.updateUI();
                    editarStock.updateUI();
                }

                iok =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("ok16"),colButton,false));
                icancel =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("closed"),colButton,false));

                if(bCancelar != null) {
                    bCancelar.setIcon(icancel);
                    bCancelar.updateUI();
                }

                if(bGuardar != null) {
                    bGuardar.setIcon(iok);
                    bGuardar.updateUI();
                }

                revalidate();
                repaint();

                super.updateUI();
            }
        };

        panelPrincipal.setOpaque(false);

        pDetalles = panelDatelle();
        pPrecio = panelPrecio();
        pStock = panelStock();

        //JPanel panel = new JPanel(new GridBagLayout());
        //panel.setOpaque(false);
        bEliminar = new JButton(CONSTANTS.i18n.getValue("button.eliminar"),iDel);
        bEliminar.setEnabled(false);
        bEliminar.setActionCommand("BUTTON_ELIMINAR");

        panelPrincipal.add(pDetalles, LayoutPanel.constantePane(0, 1, 1, 3, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panelPrincipal.add(pPrecio, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        panelPrincipal.add(pStock, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        panelPrincipal.add(bEliminar, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 10, 5, 0, 1.0f, 0.0f));
        panelPrincipal.add(getPanelButton(), LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 0, 5, 10, 1.0f, 0.0f));
    }

    public JPanel getPanelButton(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        iok =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("ok16"),color3,false));
        icancel =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("closed"),color3,false));

        //iok = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/ok16.png") ,color3,false));
        //icancel = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/closed.png") ,color3,false));

        bGuardar = new JButton(CONSTANTS.i18n.getValue("button.guardar"), iok);//,"F5",null);//,new ImageIcon("com.djm.inventa.icon/ok.png"));
        bCancelar = new JButton(CONSTANTS.i18n.getValue("button.cancelar"), icancel);//,new ImageIcon("com.djm.inventa.icon/close.png"));
        //bGuardar.setForeground(new Color(66, 89, 147));

        bCancelar.setActionCommand("BUTTON_CANCELAR");
        bGuardar.setActionCommand("GUARDAR_PRODUCTO");

        lInfo = new JLabel();
        lInfo.setIcon(IconManager.getIcon(getClass().getResource("/icon/16/info.png")));
        lInfo.setVisible(false);

        guardarF5();

        bGuardar.setFocusable(true);

        panel.add(bCancelar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LAST_LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(bGuardar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LAST_LINE_END, 5, 5, 0, 0, 0.0f, 1.0f));

        return panel;
    }

    private JPanel panelDatelle() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.detalle")));

        JLabel lCodigo = new JLabel(CONSTANTS.i18n.getLabel("label.codigo"));
        JLabel lCodigoBarra = new JLabel(CONSTANTS.i18n.getLabel("producto.label.codigobarra"));
        JLabel lNombre = new JLabel(CONSTANTS.i18n.getLabel("label.nombre"));
        JLabel lUnidad= new JLabel(CONSTANTS.i18n.getLabel("producto.label.unidad"));
        JLabel lModelo= new JLabel(CONSTANTS.i18n.getLabel("producto.label.modelo"));
        JLabel lSerie= new JLabel(CONSTANTS.i18n.getLabel("producto.label.serie"));
        JLabel lMarca = new JLabel(CONSTANTS.i18n.getLabel("producto.label.marca"));
        JLabel lCategoria = new JLabel(CONSTANTS.i18n.getLabel("producto.label.categoria"));
        JLabel lNota= new JLabel(CONSTANTS.i18n.getLabel("producto.label.nota"));
        JLabel lFechaActualizacion = new JLabel(CONSTANTS.i18n.getLabel("producto.label.fechaactualizacion"));
        JLabel lFechaCreacion = new JLabel(CONSTANTS.i18n.getLabel("producto.label.fechacreacion"));

        bEnter = new Button(IconManager.get20("ok"),true);
        bEnter.setPaintBack(false);
        bEnter.setColorFilter(greenButton);
        //bEnter.setActionCommand("BUSCAR_PRODUCTO");
        //bBuscar.addActionListener(this);
        bEnter.setButtonIcon(true);
        bEnter.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar_producto"));
        bEnter.setFocusable(false);
        bEnter.setColorBackIn(color1);
        bEnter.setColorBackSelected(color2);

        bBuscar = new Button(IconManager.get20("buscar"),true);
        bBuscar.setPaintBack(false);
        bBuscar.setButtonIcon(true);
        bBuscar.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar_producto"));
        bBuscar.setFocusable(false);
        bBuscar.setColorBackIn(color1);
        bBuscar.setColorBackSelected(color2);

        bCodigoBarra = new ToggleButton(IconManager.getIcon(getClass().getResource("/icons/barcode.png")));
        bCodigoBarra.setToolTipText(CONSTANTS.i18n.getValue("producto.inf.buscarporcodbarra"));
        bCodigoBarra.setColorIn(color2);
        bCodigoBarra.setColorFilter(color3);
        bCodigoBarra.setFocusable(false);

        bAddMarca = new Button(IconManager.get16("add"),true);
        bAddMarca.setPaintBack(false);
        bAddMarca.setColorFilter(greenButton);
        bAddMarca.setForeground(Color.RED);
        bAddMarca.setActionCommand("ADD_MARCA_PRODUCTO");
        //bAddMarca.addActionListener(this);
        bAddMarca.setButtonIcon(true);
        bAddMarca.setToolTipText(CONSTANTS.i18n.getValue("producto.marca.add"));
        bAddMarca.setFocusable(false);
        bAddMarca.setColorBackIn(color1);
        bAddMarca.setColorBackSelected(color2);

        int dimColumText = 31;//27;

        tCodigo = new TextField(20,20);
        tCodigoBarra = new TextField(dimColumText,40,true);
        tNombre = new TextField(dimColumText,50);
        tUnidadMedida = new TextField(5,10);
        tModelo = new TextField(dimColumText,20);
        tSerie = new TextField(dimColumText,20);
        tFechaActualizacion = new TextField(dimColumText,20);
        tFechaCreacion = new TextField(dimColumText,20);

        tFechaActualizacion.setEditable(false);
        tFechaCreacion.setEditable(false);

        tFechaActualizacion.setText("-");
        tFechaCreacion.setText("-");

         tCodigo.setToolTipText(CONSTANTS.i18n.getValue("producto.inf.buscarcodigo"));
         tCodigo.setPlaceHolder(CONSTANTS.i18n.getValue("producto.inf.buscarcodigo"));

         // Agregar FocusListener para validar cuando pierde el foco
        /* tCodigo.addFocusListener(new FocusListener() {
             @Override
             public void focusGained(FocusEvent e) {
                 // No hacer nada al ganar el foco
             }

             @Override
             public void focusLost(FocusEvent e) {
                 actionCodigo();
             }
         });*/

         tCodigo.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                actionCodigo();

                //True para que cambie el focus
                return true;
            }
        });

         Border border = tCodigo.getBorder();
        Color color = tCodigo.getSelectionColor();
        Color colBack = tCodigo.getBackground();

        tNota = new TextArea(2,dimColumText);
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

        dcbCategoria = new DefaultComboBoxModel<Categoria> ();

        cbCategoria = new JComboBox<>(dcbCategoria);
        cbCategoria.setPreferredSize(CONSTANTS.CDDIM);

        dcbMarca = new DefaultComboBoxModel<Marca> ();

        cbMarca = new JComboBox<>(dcbMarca);
        cbMarca.setPreferredSize(CONSTANTS.CDDIM);

        disponible = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.habilitado"));
        disponible.setOpaque(false);
        disponible.setSelected(true);

        noRequiereStock = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.no_requiere_stock"));
        noRequiereStock.setOpaque(false);
        noRequiereStock.setActionCommand("NO_REQUERE_STOCK");
        noRequiereStock.addActionListener((ae)->{
            bloquearFormStock();
        });

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bEnter, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 4, 0, 0, 0.0f, 0.0f));
        panel.add(bCodigoBarra, LayoutPanel.constantePane(3, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bBuscar, LayoutPanel.constantePane(4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 3, 0, 0, 0.0f, 0.0f));

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
        panel.add(cbMarca, LayoutPanel.constantePane(1, 6, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));
        panel.add(bAddMarca, LayoutPanel.constantePane(4, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 3, 0, 0, 0.0f, 0.0f));

        panel.add(lCategoria, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 7, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));

        panel.add(disponible, LayoutPanel.constantePane(1, 8, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(noRequiereStock, LayoutPanel.constantePane(1, 9, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lNota, LayoutPanel.constantePane(0, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(jsp, LayoutPanel.constantePane(1, 10, 4, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lFechaActualizacion, LayoutPanel.constantePane(0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tFechaActualizacion, LayoutPanel.constantePane(1, 11, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lFechaCreacion, LayoutPanel.constantePane(0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(tFechaCreacion, LayoutPanel.constantePane(1, 12, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 1.0f));

        return panel;
    }


    private JPanel panelPrecio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

        JLabel lPrecioCosto = new JLabel(CONSTANTS.i18n.getLabel("producto.label.preciocompra"));
        JLabel  lUtilidad= new JLabel(CONSTANTS.i18n.getLabel("producto.label.utilidad"));
        JLabel  lPrecio1= new JLabel(CONSTANTS.i18n.getLabel("producto.label.precio1"));
        JLabel  lPrecio2= new JLabel(CONSTANTS.i18n.getLabel("producto.label.precio2"));
        JLabel  lPrecio3= new JLabel(CONSTANTS.i18n.getLabel("producto.label.precio3"));
        JLabel  lCantMayor= new JLabel(CONSTANTS.i18n.getLabel("producto.label.cantmayor"));

        precioImpuesto = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.precioIncluyeImpuesto"));
        precioImpuesto.setSelected(true);
        precioImpuesto.setOpaque(false);
        //precioImpuesto.addActionListener(this);
        //precioImpuesto.setActionCommand("PRECIO_IMPUESTO");

        requiereAprob = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.reqAprobPrecioEspecial"));
        requiereAprob.setSelected(true);
        requiereAprob.setOpaque(false);
        //requiereAprob.addActionListener(this);
        //requiereAprob.setActionCommand("REQUIERE_APROBACION");

        tCosto = new TextField(10,10);
        tUtilidad=new TextField(10,3,true);
        tPrecio1 = new TextField(10,10);
        tPrecio2 = new TextField(10,10);
        tPrecio3 = new TextField(10,10);
        tCantMayor = new TextField(10,10);

        ImageIcon warning = IconManager.get16("warning");

        lUtilidadAdv = new JLabel(warning);
        lPrecio1Adv = new JLabel(warning);
        lPrecio2Adv = new JLabel(warning);
        lPrecio3Adv = new JLabel(warning);

        InfoListener infoListener = new InfoListener();
        lUtilidadAdv.addMouseListener(infoListener);
        lPrecio1Adv.addMouseListener(infoListener);
        lPrecio2Adv.addMouseListener(infoListener);
        lPrecio3Adv.addMouseListener(infoListener);

        lUtilidadAdv .setVisible(false);
        lPrecio1Adv .setVisible(false);
        lPrecio2Adv.setVisible(false);
        lPrecio3Adv.setVisible(false);

        lUtilidadAdv.setToolTipText(CONSTANTS.i18n.getValue("producto.message.warning_utildadexcesiva"));
        lPrecio1Adv.setToolTipText(CONSTANTS.i18n.getValue("producto.message.warning_precio_menor_costo"));
        lPrecio2Adv.setToolTipText(CONSTANTS.i18n.getValue("producto.message.warning_precio_menor_costo"));
        lPrecio3Adv.setToolTipText(CONSTANTS.i18n.getValue("producto.message.warning_precio_menor_costo"));

        tPrecio1.addFocusListener(lostFocusPrecio);
        tPrecio2.addFocusListener(lostFocusPrecio);
        tPrecio3.addFocusListener(lostFocusPrecio);

        new MonedaDocument(tCosto);
        new MonedaDocument(tPrecio1);
        new MonedaDocument(tPrecio2);
        new MonedaDocument(tPrecio3);

        tUtilidad.setActionCommand("UTILIDAD");
        //tUtilidad.addActionListener(this);

        tCosto.setText("0,00");
        tUtilidad.setText("0");
        tCantMayor.setText("0");
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

        panel.add(lPrecio2, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio2, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio2Adv, LayoutPanel.constantePane(2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCantMayor, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantMayor, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lPrecio3, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio3, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio3Adv, LayoutPanel.constantePane(2, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(requiereAprob, LayoutPanel.constantePane(1, 6, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 1.0f, 0.0f));

        panel.add(precioImpuesto, LayoutPanel.constantePane(1, 7, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private JPanel panelStock() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBackground(Color.RED);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.stock")));

        movimientoNegativo = new JCheckBox(CONSTANTS.i18n.getValue("producto.stock.nonegativo"));
        movimientoNegativo.setOpaque(false);
        movimientoNegativo.setSelected(true);

        menuIngreso();

        bAddCantidad = new Button(IconManager.get16("add"),true);
        bAddCantidad.setPaintBack(false);
        bAddCantidad.setColorFilter(greenButton);
        bAddCantidad.setActionCommand("ADD_CANTIDAD_PRODUCTO");
        //bAddCantidad.addActionListener(this);
        bAddCantidad.setButtonIcon(true);
        bAddCantidad.setToolTipText(CONSTANTS.i18n.getValue("producto.stock.add"));
        bAddCantidad.setFocusable(false);
        bAddCantidad.setColorBackIn(color1);
        bAddCantidad.setColorBackSelected(color2);

        bAddCantidad.addActionListener(ae->{
            popupMenu.show(bAddCantidad, 0,bAddCantidad.getHeight());
        });

        JLabel lAdvertencia = new JLabel(CONSTANTS.i18n.getLabel("producto.label.adv_stockcritico"));
        JLabel lDisponible = new JLabel(CONSTANTS.i18n.getLabel("producto.label.cantidad_disponible"));

        tStockCritico = new TextField(7,10,true);
        tStockCritico.setText("0");
        tStockCritico.setName("STOCK_ENTER");

        tCantidadDisponible = new TextField(7);
        tCantidadDisponible.setEditable(false);
        tCantidadDisponible.setText("0");
        //tCantidadDisponible.addActionListener(this);
        tCantidadDisponible.setActionCommand("STOCK_ENTER");

        panel.add(movimientoNegativo, LayoutPanel.constantePane(0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 0, 0, 0.0f, 0.0f));
        panel.add(lAdvertencia, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tStockCritico, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lDisponible, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadDisponible, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddCantidad, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private void menuIngreso(){
        popupMenu = new JPopupMenu();
        agragrStock = new JMenuItem(CONSTANTS.i18n.getValue("menu.producto.agregarstock"));
        editarStock = new JMenuItem(CONSTANTS.i18n.getValue("menu.producto.editarstock"));

        agragrStock.setActionCommand("AGREGAR_STOCK_RAPIDO");
        editarStock.setActionCommand("EDITAR_STOCK_RAPIDO");

        editarStock.setEnabled(false);

        popupMenu.add(agragrStock);
        popupMenu.add(editarStock);
    }
    private void bloquearFormStock() {
        boolean e = !noRequiereStock.isSelected();
        tCantidadDisponible.setEnabled(e);
        bAddCantidad.setEnabled(e);
        movimientoNegativo.setEnabled(e);
        tStockCritico.setEnabled(e);
    }

    private boolean actionCodigo() {
        String codigo = tCodigo.getText().trim();

        if (!codigo.isEmpty()) {
            validarDatos(codigo.trim());
        }

        return true;
    }

    private void validarDatos(String codigo) {

        if(codigo == null || isDataDB(codigo)){
            return;
        }

        if(bCodigoBarra.isSelected()){
            // Solicitar foco de forma diferida solo si está activada la búsqueda por código de barra
            SwingUtilities.invokeLater(() -> tCodigoBarra.requestFocusInWindow());
            if(tCodigoBarra.getText().trim().isEmpty()){
                tCodigoBarra.setText(codigo);
            }
            else {

                if(!codigo.equalsIgnoreCase(tCodigoBarra.getText())){
                    int n0 = OptionPane.questionYesOrKey(CONSTANTS.i18n.getValue("producto.mensaje.difcodigo"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//
                    if (n0 == OptionPane.OK) {
                        tCodigoBarra.setText(codigo);

                    }
                }
            }
        }
    }

    //Comprobar si el producto existe en la BD
    private boolean isDataDB(String codigo) {

        ProductoDAO productoDB = new ProductoDAO();
        Producto producto = null;
        try {
            producto = productoDB.obtenerProducto(codigo);
        }
        catch (ProductoException exc){
            OptionPane.error(exc.getMessage());
        }

        if(producto != null){
            insertData(producto);

            //Cargar los datos
            return true;
        }

        return false;
    }

    /**
     * Inserta un producto en el formulario para edición
     */
    public void insertData(Producto producto) {
        iPanelDataAction.insertData(producto);
        fillerFormProducto(producto, null);
    }

    public void fillerFormProducto(Producto producto, MovimientoStock movimientoStock){
        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));

        eText(false);

        clearForm();

        tCodigo.setText(producto.getCodigo());
        tCodigoBarra.setText(producto.getCodigoBarra());
        tNombre.setText(producto.getNombre());
        tUnidadMedida.setText(producto.getUnidadMedida());
        tModelo.setText(producto.getModelo());
        tSerie.setText(producto.getSerie());

        tCosto.setText(NumberConverter.bigDecimalToString(producto.getPrecioCosto()));

        if(producto.getUtilidad()!= null)
            tUtilidad.setText(String.valueOf(producto.getUtilidad()));


        tUtilidad.setText(producto.getUtilidad() != null
                ? producto.getUtilidad().toString()
                : "");

        tCosto.setText(NumberUtils.format(producto.getPrecioCosto()));
        tPrecio1.setText(NumberUtils.format(producto.getPrecio1()));
        tPrecio2.setText(NumberUtils.format(producto.getPrecio2()));
        tPrecio3.setText(NumberUtils.format(producto.getPrecio3()));

        tCantMayor.setText(producto.getCantMayor() != null
                ? producto.getCantMayor().toString()
                : "");

        tCantidadDisponible.setText(producto.getCantidadDisponible() != null
                ? producto.getCantidadDisponible().toString()
                : "");

        tStockCritico.setText(producto.getStockCritico() != null
                ? producto.getStockCritico().toString()
                : "");

        if(producto.getStockCritico() != null)
            tStockCritico.setText(String.valueOf(producto.getStockCritico()));

        disponible.setSelected(Boolean.TRUE.equals(producto.isDisponible()));
        noRequiereStock.setSelected(Boolean.TRUE.equals(producto.isNoRequiereStock()));
        movimientoNegativo.setSelected(Boolean.TRUE.equals(producto.isMovimientoNegativo()));
        precioImpuesto.setSelected(Boolean.TRUE.equals(producto.isPrecioIncluyeImpuesto()));
        requiereAprob.setSelected(Boolean.TRUE.equals(producto.isReqAprobPrecioEspecial()));

        tNota.setText(producto.getNota());

        /*Marca marca = producto.getMarca();
        Categoria categoria = producto.getCategoria();

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
        }*/

        cbCategoria.setSelectedItem(producto.getCategoria());
        cbMarca.setSelectedItem(producto.getMarca());

        boolean excesivo = producto.getUtilidad() > 100;
        tUtilidad.setForeground(excesivo ? Color.RED : UIManager.getColor("TextField.foreground"));
        lUtilidadAdv.setVisible(excesivo);

        procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);

        if (movimientoStock != null) {
            tCantidadDisponible.setText(String.valueOf(movimientoStock.getCantidad()));
        }
        else {
            tCantidadDisponible.setText(String.valueOf(producto.getCantidadDisponible()));
        }

        bEliminar.setEnabled(true);
        editarStock.setEnabled(true);

        tFechaCreacion.setText(producto.getFechaCreacion() != null
                ? producto.getFechaCreacion().toString()
                : "");

        tFechaActualizacion.setText(producto.getFechaActualizacion() != null
                ? producto.getFechaActualizacion().toString()
                : "");

        eText(true);

        bloquearFormStock();

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    protected void clearForm(){
        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));

        tPrecio1.borderNoError();
        tPrecio2.borderNoError();
        tPrecio3.borderNoError();

        bEliminar.setEnabled(false);

        tCodigo.setText(null);
        tCodigoBarra.setText(null);
        tNombre.setText(null);
        tUnidadMedida.setText(null);
        tModelo.setText(null);
        tSerie.setText(null);

        disponible.setSelected(true);
        noRequiereStock.setSelected(false);
        movimientoNegativo.setSelected(true);

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

        editarStock.setEnabled(false);

        precioImpuesto.setSelected(true);

        if(cbCategoria.getItemCount() > 0)
            cbCategoria.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        tStockCritico.setText("0");
        tCantidadDisponible.setText("0");

        tFechaActualizacion.setText("-");
        tFechaCreacion.setText("-");

        tCodigo.requestFocus();

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public Producto getDataForm(Producto producto){
        String cod = tCodigo.getText();
        String codBarra = tCodigoBarra.getText();
        String nombre = tNombre.getText();
        String unidMed = tUnidadMedida.getText();
        String modelo = tModelo.getText();
        String serie = tSerie.getText();
        boolean disp = disponible.isSelected();
        boolean noReqStock = noRequiereStock.isSelected();
        boolean movNegativo = movimientoNegativo.isSelected();
        String nota = tNota.getText();
        String costo = tCosto.getText();
        String utilidad = tUtilidad.getText();
        String precio1 = tPrecio1.getText();
        String precio2 = tPrecio2.getText();
        String precio3 = tPrecio3.getText();
        boolean reqAprob = requiereAprob.isSelected();
        String cantMayor = tCantMayor.getText();
        boolean isImpuesto = precioImpuesto.isSelected();

        if((cod == null || cod.trim().isEmpty()) && (codBarra != null &&!codBarra.trim().isEmpty())){
            cod = codBarra;
            tCodigo.setText(cod);
        }

        Marca marca = (Marca)dcbMarca.getSelectedItem();
        Categoria categoria = (Categoria) dcbCategoria.getSelectedItem();

        String stockCrititco = tStockCritico.getText();

        BigDecimal c = BigDecimal.ZERO;
        if(costo!=null && !costo.trim().isEmpty()) {
            c = NumberConverter.stringToBigDecimal(costo);
        }

        BigDecimal p1 = BigDecimal.ZERO;
        if(precio1!= null && !precio1.trim().isEmpty()) {
            p1 = NumberConverter.stringToBigDecimal(precio1);
        }

        BigDecimal p2 = BigDecimal.ZERO;
        if(precio2!= null && !precio2.trim().isEmpty()){
            p2 = NumberConverter.stringToBigDecimal(precio2);
        }

        BigDecimal p3 = BigDecimal.ZERO;
        if(precio3!= null && !precio3.trim().isEmpty()) {
            p3 = NumberConverter.stringToBigDecimal(precio3);
        }

        BigDecimal scritico = BigDecimal.ZERO;

        if(stockCrititco !=null && !stockCrititco.trim().isEmpty()) {
            scritico = new BigDecimal(stockCrititco);
        }

        int util = 0;
        if(utilidad != null && !utilidad.trim().isEmpty()) {
            try {
                util = Integer.parseInt(utilidad);
            }catch (NumberFormatException exc){
                LoggerApp.error("Error al tratar de convertir la utilidad en entero: "+exc.getMessage());
            }
        }

        Integer cantMayorInt = 0;
        if(cantMayor != null && !cantMayor.trim().isEmpty()) {
            cantMayorInt = Integer.parseInt(cantMayor);
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
        producto.setMovimientoNegativo(movNegativo);
        producto.setNota(nota);
        producto.setUtilidad(util);
        producto.setPrecioCosto(c);
        producto.setPrecio1(p1);
        producto.setPrecio2(p2);
        producto.setPrecio3(p3);
        producto.setCantMayor(cantMayorInt);
        producto.setReqAprobPrecioEspecial(reqAprob);
        producto.setPrecioIncluyeImpuesto(isImpuesto);
        producto.setStockCritico(scritico);

        producto.setFechaCreacion(FechaUtil.parseFecha(tFechaCreacion.getText()));
        producto.setFechaActualizacion(FechaUtil.parseFecha(tFechaActualizacion.getText()));

        String cantDisponible = tCantidadDisponible.getText();
        producto.setCantidadDisponible(new BigDecimal(cantDisponible));

        return producto;
    }

    public JButton getButtonCancelar() {
        return bCancelar;
    }

    public void eText(boolean enabled){
        tCodigo.setEnabled(enabled);
        bEnter.setEnabled(enabled);

        tCodigoBarra.setEnabled(enabled);
        tNombre.setEnabled(enabled);
        tUnidadMedida.setEnabled(enabled);
        tModelo.setEnabled(enabled);
        tSerie.setEnabled(enabled);

        cbMarca.setEnabled(enabled);
        bAddMarca.setEnabled(enabled);
        cbCategoria.setEnabled(enabled);

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

    public void setCantidadDisponible(BigDecimal cant){//, boolean agregar){
        tCantidadDisponible.setText(String.valueOf(cant));

        /*BigDecimal cantidad = BigDecimal.ZERO;
        if(cant.compareTo(BigDecimal.ZERO) > 0){
            String tcant = tCantidadDisponible.getText();
            cantidad = cant;
            if(agregar && tcant != null && !tcant.isBlank()){
                try {
                    cantidad = cantidad.add(new BigDecimal(tcant));
                }catch (NumberFormatException exc){}
            }
            tCantidadDisponible.setText(String.valueOf(cantidad));
        }

        return cantidad;*/
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }

    public void setActionListener(ActionListener productoListener){

        bGuardar.addActionListener(productoListener);
        bCancelar.addActionListener(productoListener);
        bEliminar.addActionListener(productoListener);
        agragrStock.addActionListener(productoListener);
        editarStock.addActionListener(productoListener);

        // Al presionar Enter en tCodigo, pasar el foco a tNombre
        tCodigo.addActionListener(ae -> {
            actionCodigo2();
        });

        bEnter.addActionListener(ae->{
            actionCodigo2();
        });

        bBuscar.addActionListener(ae -> {
            // Construir listas de marcas y categorias desde los modelos actuales
            List<Marca> marcas = new ArrayList<>();
            for (int i = 0; i < dcbMarca.getSize(); i++) {
                marcas.add(dcbMarca.getElementAt(i));
            }

            List<Categoria> categorias = new ArrayList<>();
            for (int i = 0; i < dcbCategoria.getSize(); i++) {
                categorias.add(dcbCategoria.getElementAt(i));
            }

            DialogBuscarProducto dialog = new DialogBuscarProducto(GlobalFrame.getInstance().getFrame(), marcas, categorias);
            dialog.setLocationRelativeTo(GlobalFrame.getInstance().getFrame());
            dialog.setVisible(true);
            Producto p = dialog.getSelectedProducto();
            if (p != null) {
                insertData(p);
            }
        });

        bCodigoBarra.addActionListener((e)->{
            boolean edo = bCodigoBarra.isSelected();
            String value = edo?"producto.inf.buscarporcodbarra":"producto.inf.buscarcodigo";
            tCodigo.setPlaceHolder(CONSTANTS.i18n.getValue(value));
            tCodigo.requestFocus();
        });
    }

    private void actionCodigo2() {
        if(bCodigoBarra.isSelected()) {
            tNombre.requestFocus();
        }
        else {
            tCodigoBarra.requestFocus();
        }
    }

    /**
     * Verifica si hay datos en el formulario
     */
    public boolean hasFormData() {
        return !tCodigo.getText().trim().isEmpty()
                || !tCodigoBarra.getText().trim().isEmpty()
                || !tNombre.getText().trim().isEmpty();
    }


    public void addButtonEsc() {

        ActionEvent eventoSimulado = new ActionEvent(bCancelar, ActionEvent.ACTION_PERFORMED, "BUTTON_CANCELAR");

        if( bCancelar.getActionListeners().length == 1)
            bCancelar.getActionListeners()[0].actionPerformed(eventoSimulado);
    }


    private class LostFocusPrecio implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            //procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);
        }
    }

    private void guardarF5() {
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_F5,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                //guardarProducto();
                ActionEvent eventoSimulado = new ActionEvent(bGuardar, ActionEvent.ACTION_PERFORMED, "GUARDAR_PRODUCTO");

                if( bGuardar.getActionListeners().length == 1)
                    bGuardar.getActionListeners()[0].actionPerformed(eventoSimulado);
            }
        };
        InputMap inputMap = bGuardar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "GUARDAR_PRODUCTO");
        ActionMap actionMap = bGuardar.getActionMap();
        actionMap.put("GUARDAR_PRODUCTO", action);
    }

    public void loadData(){
        try {

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categoriaList = categoriaDAO.listarCategorias();

            for (Categoria categoria : categoriaList) {
                dcbCategoria.addElement(categoria);
            }

            MarcaDAO marcaDAO = new MarcaDAO();
            List<Marca> marcaList = marcaDAO.listarMarca();

            for (Marca marca : marcaList) {
                dcbMarca.addElement(marca);
            }
        }
        catch (ProductoException exc) {
            OptionPane.error(exc);
        }
    }
}
