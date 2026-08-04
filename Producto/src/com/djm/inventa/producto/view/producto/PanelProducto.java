package com.djm.inventa.producto.view.producto;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.modelo.Atributo;
import com.djm.inventa.modelo.AtributoValor;
import com.djm.inventa.modelo.Moneda;
import com.djm.inventa.modelo.PrecioProducto;
import com.djm.inventa.modelo.ProductoVariante;
import com.djm.inventa.modelo.TipoPrecio;
import com.djm.inventa.modelo.UnidadMedida;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.producto.exception.ProductoException;
import com.djm.inventa.producto.persistence.CategoriaDAO;
import com.djm.inventa.producto.persistence.MarcaDAO;
import com.djm.inventa.producto.persistence.ProductoDAO;
import com.djm.inventa.stock.model.MovimientoStock;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.component.Table;
import com.djm.inventa.ui.component.renderer.ThreeLabelRenderer;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.inventa.ui.component.renderer.TwoColumnRenderer;
import com.djm.inventa.ui.util.BorderUtil;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.djm.inventa.modelo.Categoria;
import com.djm.inventa.modelo.Marca;
import com.djm.inventa.producto.model.Producto;
import com.djm.inventa.ui.component.TextField;
import com.djm.inventa.ui.component.TextArea;
import com.djm.inventa.util.DecimalField;
import com.djm.inventa.util.FechaUtil;
import com.djm.inventa.util.LoggerApp;
import com.djm.inventa.util.FormatDecimalDocument;
import com.djm.inventa.util.NumberUtils;
import com.djm.ui.GlobalFrame;
import com.djm.ui.LayoutPanel;
import com.djm.ui.component.Button;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.OptionPane;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.NumberConverter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PanelProducto{
    private JPanel panelPrincipal;

    private ModelTableVarianteCustom mtc;
    private Table<Producto> tabla;

    //Producto
    private TextField tCodigo, tNombre, tModelo, tFechaActualizacion, tFechaCreacion;
    //Variante
    private TextField tSKU,tCodigoBarra, tCantidadStock, tCantidadMinima, tCantidadMaxima,
                        tPrecio;
    private DefaultListModel<PrecioProducto> modelPrecioProducto;

    private Button bAddMarca, bEnter, bBuscar, bDelPrecio, bEditPrecio;
    private JButton bGuardar, bCancelar, bEliminar, bAddPrecio;
    private JLabel lInfo;
    //private ToggleButton bCodigoBarra;
    private TextArea tNota;
    private JComboBox<Atributo> cbAtributo;
    private JComboBox<AtributoValor> cbAtributoValor;
    private JComboBox<TipoPrecio> cbTipoPrecio;
    private JComboBox<Moneda> cbMoneda;
    private JComboBox<UnidadMedida> cbUnidadMedida;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Marca> cbMarca;
    private DefaultComboBoxModel<Atributo> dcbAtributo;
    private DefaultComboBoxModel<AtributoValor> dcbAtributoValor;
    private DefaultComboBoxModel<TipoPrecio> dcbTipoPrecio;
    private DefaultComboBoxModel<Moneda> dcbMoneda;
    private DefaultComboBoxModel<UnidadMedida> dcbUnidadMedida;
    private DefaultComboBoxModel<Categoria> dcbCategoria;
    private DefaultComboBoxModel<Marca> dcbMarca;
    private JCheckBox disponibleProd, disponibleVar, noRequiereStock, precioImpuesto, movimientoNegativo, reqAutPrec;
    private Color greenButton = new Color(77, 170, 71);
    private LostFocusPrecio lostFocusPrecio = new LostFocusPrecio();
    private ProcesadorProducto procesadorProducto = new ProcesadorProducto();
    private Color color1 = UIManager.getColor("Panel.background");
    private Color color2 = UIManager.getColor("TextField.background");
    private Color color3 = UIManager.getColor("TextField.foreground");

    private JPanel pDetalles, pVariante;
    private ImageIcon iDel, iok, ibuscar, icancel;;//

    private Producto producto = null;
    private boolean skipInputVerifier = true;

    private final int dimColumText = 30;//27;
    private Dimension dimLabel = null;

    private final IPanelDataAction iPanelDataAction;

    public PanelProducto(IPanelDataAction iPanelDataAction){
        this.iPanelDataAction = iPanelDataAction;

        panelPrincipal = new JPanel(new GridBagLayout()) {
            @Override
            public void updateUI(){
                color1 = UIManager.getColor("Panel.background");
                color2 = UIManager.getColor("TextField.background");
                color3 = UIManager.getColor("TextField.foreground");

                /*if(pDetalles != null)
                    pDetalles.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.detalle")));

                if(pVariante != null)
                    pVariante.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.variante")));
                if(pStock != null)
                    pStock.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.stock")));*/


                Color colButton = AppContext.getInstance().getColor("Label.colorDarker");

                if(colButton == null || "LIGTH".equals(PropiedadesLookAndFeel.getPropiedad("Apariencia.lookandfeel"))){
                    colButton = color3;
                }

                /*if(bCodigoBarra != null)
                    bCodigoBarra.setColorFilter(colButton);*/


                if(bBuscar != null){
                    bBuscar.setColorFilter(colButton);
                }

                //iDel = IconManager.getIcon(getClass().getResource("/icon/16/delete2.png"));//new ImageIcon(ColorFilter.filterImage( IconManager.get("16/delete2.png") ,colButton,false));
                iDel = new ImageIcon(ColorFilter.filterImage( IconManager.get16("delete2") ,colButton,false));
                if(bEliminar != null){
                    bEliminar.setIcon(iDel);
                    bEliminar.updateUI();
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
        pVariante = panelVariante();

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(CONSTANTS.i18n.getValue("producto.border.title.detalle"), pDetalles);
        tabbedPane.addTab(CONSTANTS.i18n.getValue("producto.border.title.variante"), pVariante);

        //JPanel panel = new JPanel(new GridBagLayout());
        //panel.setOpaque(false);
        bEliminar = new JButton(CONSTANTS.i18n.getValue("button.eliminar"),iDel);
        bEliminar.setEnabled(false);
        bEliminar.setActionCommand("BUTTON_ELIMINAR");
        bEliminar.setToolTipText(CONSTANTS.i18n.getValue("button.eliminar.producto.tooltip"));

        panelPrincipal.add(tabbedPane, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        //panelPrincipal.add(pDetalles, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 0, 0.0f, 0.0f));
        //panelPrincipal.add(pVariante, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        //panelPrincipal.add(pStock, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 10, 0, 10, 1.0f, 0.0f));
        panelPrincipal.add(bEliminar, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 10, 5, 0, 1.0f, 0.0f));
        panelPrincipal.add(getPanelButton(), LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 0, 5, 10, 1.0f, 0.0f));
        panelPrincipal.add(panelFecha(), LayoutPanel.constantePane(0, 5, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 10, 5, 0, 0.0f, 0.0f));
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
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.detalle")));

        JLabel lCodigo = new JLabel(CONSTANTS.i18n.getLabel("label.codigo"));
        JLabel lNombre = new JLabel(CONSTANTS.i18n.getLabel("label.nombre"));
        JLabel lUnidad= new JLabel(CONSTANTS.i18n.getLabel("producto.label.unidad"));
        JLabel lModelo= new JLabel(CONSTANTS.i18n.getLabel("producto.label.modelo"));
        JLabel lMarca = new JLabel(CONSTANTS.i18n.getLabel("producto.label.marca"));
        JLabel lCategoria = new JLabel(CONSTANTS.i18n.getLabel("producto.label.categoria"));
        JLabel lNota= new JLabel(CONSTANTS.i18n.getLabel("producto.label.nota"));

        bEnter = new Button(IconManager.get20("ok"),true);
        bEnter.setPaintBack(false);
        bEnter.setColorFilter(greenButton);
        //bEnter.setActionCommand("BUSCAR_PRODUCTO");
        //bBuscar.addActionListener(this);
        bEnter.setButtonIcon(true);
        bEnter.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar"));
        bEnter.setFocusable(false);
        bEnter.setColorBackIn(color1);
        bEnter.setColorBackSelected(color2);

        ibuscar =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("buscar"),color3,false));
        bBuscar = new Button(ibuscar,true);
        bBuscar.setPaintBack(false);
        bBuscar.setButtonIcon(true);
        bBuscar.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar"));
        bBuscar.setFocusable(false);
        bBuscar.setColorBackIn(color1);
        bBuscar.setColorBackSelected(color2);
        bBuscar.setColorFilter(color3);

        /*bCodigoBarra = new ToggleButton(IconManager.getIcon(getClass().getResource("/icons/barcode.png")));
        bCodigoBarra.setToolTipText(CONSTANTS.i18n.getValue("producto.inf.buscarporcodbarra"));
        bCodigoBarra.setColorIn(color2);
        bCodigoBarra.setColorFilter(color3);
        bCodigoBarra.setFocusable(false);*/

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

        tCodigo = new TextField(20,20);
        tNombre = new TextField(dimColumText,50);
        tModelo = new TextField(dimColumText,20);

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
                if(skipInputVerifier)
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

        tNota.setFont(UIManager.getFont("TextField.font"));

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

        dcbUnidadMedida = new DefaultComboBoxModel<UnidadMedida> ();
        dcbCategoria = new DefaultComboBoxModel<Categoria> ();

        cbCategoria = new JComboBox<>(dcbCategoria);
        cbCategoria.setPreferredSize(CONSTANTS.CDDIM_227);

        cbUnidadMedida = new JComboBox<>(dcbUnidadMedida);
        cbUnidadMedida.setPreferredSize(CONSTANTS.CDDIM_227);

        cbUnidadMedida.setRenderer(
                new TwoColumnRenderer<>(
                        UnidadMedida::getNombre,
                        UnidadMedida::getAbreviacion
                )
        );

        UnidadMedida unidadMedida = new UnidadMedida();
        unidadMedida.setNombre(CONSTANTS.i18n.getValue("label.ninguno"));
        dcbUnidadMedida.addElement(unidadMedida);

        UnidadMedida um2 = new UnidadMedida();
        um2.setNombre("Litro");
        um2.setAbreviacion("Lt");
        dcbUnidadMedida.addElement(um2);

        UnidadMedida um3 = new UnidadMedida();
        um3.setNombre("Kilogramo");
        um3.setAbreviacion("Kg");
        dcbUnidadMedida.addElement(um3);

        dcbMarca = new DefaultComboBoxModel<Marca> ();

        Marca marca = new Marca();
        marca.setNombre(CONSTANTS.i18n.getValue("label.ninguno"));
        dcbMarca.addElement(marca);

        cbMarca = new JComboBox<>(dcbMarca);
        cbMarca.setPreferredSize(CONSTANTS.CDDIM_227);

        disponibleProd = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.habilitado"));
        disponibleProd.setOpaque(false);
        disponibleProd.setSelected(true);

        disponibleProd.addActionListener((ae)->{
            if(producto != null && !producto.isEliminado()) {
                bEliminar.setEnabled( !disponibleProd.isSelected());
            }
        });

        noRequiereStock = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.no_requiere_stock"));
        noRequiereStock.setOpaque(false);
        noRequiereStock.setActionCommand("NO_REQUERE_STOCK");
        noRequiereStock.addActionListener((ae)->{
            if (producto == null || !producto.isEliminado()) {
                bloquearFormStock(!noRequiereStock.isSelected());
            }
        });

        precioImpuesto = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.precioIncluyeImpuesto"));
        precioImpuesto.setSelected(true);
        precioImpuesto.setOpaque(false);

        movimientoNegativo = new JCheckBox(CONSTANTS.i18n.getValue("producto.stock.nonegativo"));
        movimientoNegativo.setOpaque(false);
        movimientoNegativo.setSelected(false);

        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bEnter, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 4, 0, 0, 0.0f, 0.0f));
        //panel.add(bCodigoBarra, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 3, 0, 0, 0.0f, 0.0f));
        panel.add(bBuscar, LayoutPanel.constantePane(4, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 3, 0, 0, 0.0f, 0.0f));

        panel.add(lNombre, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 2, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lModelo, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tModelo, LayoutPanel.constantePane(1, 3, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lUnidad, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbUnidadMedida, LayoutPanel.constantePane(1, 4, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lMarca, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbMarca, LayoutPanel.constantePane(1, 5, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddMarca, LayoutPanel.constantePane(4, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 3, 0, 0, 0.0f, 0.0f));

        panel.add(lCategoria, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbCategoria, LayoutPanel.constantePane(1, 6, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(disponibleProd, LayoutPanel.constantePane(1, 7, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(noRequiereStock, LayoutPanel.constantePane(1, 8, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(precioImpuesto, LayoutPanel.constantePane(1, 9, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(movimientoNegativo, LayoutPanel.constantePane(1, 10, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lNota, LayoutPanel.constantePane(0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(jsp, LayoutPanel.constantePane(1, 11, 4, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 1.0f, 1.0f));


        return panel;
    }

    private JPanel panelFecha() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lFechaActualizacion = new JLabel(CONSTANTS.i18n.getLabel("producto.label.fechaactualizacion"));
        JLabel lFechaCreacion = new JLabel(CONSTANTS.i18n.getLabel("producto.label.fechacreacion"));

        tFechaActualizacion = new TextField(10,20);
        tFechaCreacion = new TextField(10,20);

        tFechaActualizacion.setEditable(false);
        tFechaCreacion.setEditable(false);

        tFechaActualizacion.setText("-");
        tFechaCreacion.setText("-");

        panel.add(lFechaCreacion, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(tFechaCreacion, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 1.0f));

        panel.add(lFechaActualizacion, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tFechaActualizacion, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel panelVariante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.variante")));

        panel.add(panelAtributos(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));
        panel.add(panelCantidad(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));

        panel.add(panelPrecio(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));

        panel.add(pTable(), LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 0, 0.0f, 0.0f));

        return panel;
    }


    private JPanel panelAtributos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

        JLabel lCodigoBarra = new JLabel(CONSTANTS.i18n.getLabel("producto.label.codigobarra"));
        JLabel lSKU = new JLabel(CONSTANTS.i18n.getLabel("producto.label.sku"));
        JLabel lAtributo = new JLabel(CONSTANTS.i18n.getLabel("producto.label.atributo"));
        //JLabel lAtributoValor = new JLabel(CONSTANTS.i18n.getLabel("producto.label.atributovalor"));

        dimLabel = lCodigoBarra.getPreferredSize();//new Dimension(90,23);

        tCodigoBarra = new TextField(dimColumText,20,true);
        tSKU = new TextField(dimColumText,20);

        disponibleVar = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.habilitado"));
        disponibleVar.setOpaque(false);
        disponibleVar.setSelected(true);

        reqAutPrec = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.reqAprobPrecio"));
        reqAutPrec.setOpaque(false);

        dcbAtributo = new DefaultComboBoxModel<Atributo> ();
        dcbAtributoValor = new DefaultComboBoxModel<AtributoValor>();

        cbAtributo = new JComboBox<>(dcbAtributo);
        cbAtributo.setPreferredSize(CONSTANTS.CDDIM_120);

        cbAtributoValor = new JComboBox<>(dcbAtributoValor);
        cbAtributoValor.setPreferredSize(CONSTANTS.CDDIM_120);
        cbAtributoValor.setEditable(false);

        Atributo atributo = new Atributo();
        atributo.setNombre(CONSTANTS.i18n.getValue("label.ninguno"));
        dcbAtributo.addElement(atributo);

        AtributoValor atributoValor = new AtributoValor();
        atributoValor.setAtributo(atributo);
        atributoValor.setValor("");

        dcbAtributoValor.addElement(atributoValor);

        cbAtributoValor.setEnabled(false);

        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(1, 1, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lSKU, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tSKU, LayoutPanel.constantePane(1, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lAtributo, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbAtributo, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        //panel.add(lAtributoValor, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbAtributoValor, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(disponibleVar, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));


        return panel;
    }

    private JPanel panelPrecio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

        JLabel lTipoPrecio = new JLabel(CONSTANTS.i18n.getLabel("producto.label.tipo_precio"));
        JLabel lMoneda = new JLabel(CONSTANTS.i18n.getLabel("producto.label.moneda"));
        JLabel lPrecio = new JLabel(CONSTANTS.i18n.getLabel("producto.label.precio"));

        //lTipoPrecio.setPreferredSize(dimLabel);

        bAddPrecio = new JButton(CONSTANTS.i18n.getValueMsgFormat("producto.button.addprecio"));

        tPrecio = new TextField(10,10);

        tPrecio.addFocusListener(lostFocusPrecio);
        DecimalField.configurar(tPrecio, true);

        tPrecio.setText("0,00");

        dcbTipoPrecio = new DefaultComboBoxModel<TipoPrecio>();
        dcbMoneda = new DefaultComboBoxModel<Moneda>();
        dcbCategoria = new DefaultComboBoxModel<Categoria>();

        cbTipoPrecio = new JComboBox<>(dcbTipoPrecio);
        cbTipoPrecio.setPreferredSize(CONSTANTS.CDDIM_120);

        cbMoneda = new JComboBox<>(dcbMoneda);
        cbMoneda.setPreferredSize(CONSTANTS.CDDIM_120);

        cbMoneda.setRenderer(
                new TwoColumnRenderer<>(
                        Moneda::getNombre,
                        Moneda::getSimbolo
                )
        );

        Moneda m1 = new Moneda();
        Moneda m2 = new Moneda();
        Moneda m3 = new Moneda();
        Moneda m4 = new Moneda();

        m1.setNombre("Pesos");
        m1.setSimbolo("$");
        m2.setNombre("Bolivar");
        m2.setSimbolo("Bs");
        m3.setNombre("Dólar");
        m3.setSimbolo("USD");
        m4.setNombre("Euro");
        m4.setSimbolo("EUR");

        dcbMoneda.addElement(m1);
        dcbMoneda.addElement(m2);
        dcbMoneda.addElement(m3);
        dcbMoneda.addElement(m4);

        panel.add(lTipoPrecio, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbTipoPrecio, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lMoneda, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(cbMoneda, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio, LayoutPanel.constantePane(4, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio, LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(reqAutPrec, LayoutPanel.constantePane(0, 1, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bAddPrecio, LayoutPanel.constantePane(5, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 0, 0, 5, 0.0f, 0.0f));

        panel.add(pTablePrecio(), LayoutPanel.constantePane(0, 2, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 5, 0.0f, 0.0f));

        return panel;
    }


    private JPanel panelCantidad() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.stock")));

        JLabel lCantidadMinima = new JLabel(CONSTANTS.i18n.getLabel("producto.label.stockcritico"));
        JLabel lCantidadMaxima = new JLabel(CONSTANTS.i18n.getLabel("producto.label.stock_maximo"));
        JLabel lCantidadStock = new JLabel(CONSTANTS.i18n.getLabel("producto.label.cantidad_disponible"));

        //lCantidadMinima.setPreferredSize(dimLabel);

        tCantidadStock = new TextField(10,10);
        tCantidadMinima = new TextField(10,10);
        tCantidadMaxima = new TextField(10,10);

        tCantidadStock.setText("0");
        tCantidadMinima.setText("0");
        tCantidadMaxima.setText("0");

        DecimalField.configurar(tCantidadStock);
        DecimalField.configurar(tCantidadMinima);
        DecimalField.configurar(tCantidadMaxima);


        panel.add(lCantidadMinima, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadMinima, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCantidadMaxima, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadMaxima, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCantidadStock, LayoutPanel.constantePane(4, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadStock, LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel pTablePrecio(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        bDelPrecio = new Button("X");
        bEditPrecio = new Button("E");

        modelPrecioProducto = new DefaultListModel<>();
        JList<PrecioProducto> lista = new JList<>(modelPrecioProducto);

        //lista.setPreferredSize(new Dimension(400, 50));

        JScrollPane jsp = new JScrollPane(lista,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);

        jsp.setPreferredSize(new Dimension(350, 70));

        lista.setCellRenderer(
                new ThreeLabelRenderer<>(
                        precio -> precio.getTipoPrecio() != null
                                ? precio.getTipoPrecio().getNombre()
                                : "",

                        precio -> NumberUtils.format(precio.getValor())
                                + " "
                                + precio.getMoneda().getSimbolo(),

                        precio -> Boolean.TRUE.equals(precio.getRequiereAutorizacion()),

                        precio -> precio.getTipoPrecio() != null
                                ? precio.getMoneda().getNombre()
                                : ""
                )
        );

        cargarDatosFictisios();

        panel.add(jsp, LayoutPanel.constantePane(0, 0, 1, 2, GridBagConstraints.VERTICAL, GridBagConstraints.LINE_START, 5, 10, 0, 0, 0.0f, 1.0f));
        panel.add(bEditPrecio, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 2, 0, 10, 1.0f, 0.0f));
        panel.add(bDelPrecio, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 2, 2, 0, 10, 1.0f, 0.0f));

        return panel;
    }

    private JScrollPane pTable(){

        mtc = new ModelTableVarianteCustom();

        ModeloTabla<ProductoVariante> modelo = new ModeloTabla(mtc);

        tabla = new Table(modelo, 70);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    //Seleccionar Variante
                }
            }
        });

        tabla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = tabla.getSelectedRow();
                    if(row != -1){
                        //Seleccionar Variante
                    }
                }
            }
        });

        return tabla.getPanel();
    }

    private void cargarDatosFictisios(){

        Moneda pesos = new Moneda();
        pesos.setId("ARS");
        pesos.setNombre("Peso Argentino");
        pesos.setSimbolo("$");

        Moneda dolar = new Moneda();
        dolar.setId("USD");
        dolar.setNombre("Dólar");
        dolar.setSimbolo("U$S");


        TipoPrecio lista = new TipoPrecio();
        lista.setId(1);
        lista.setNombre("Precio Lista");

        TipoPrecio oferta = new TipoPrecio();
        oferta.setId(2);
        oferta.setNombre("Precio Oferta");

        TipoPrecio mayorista = new TipoPrecio();
        mayorista.setId(3);
        mayorista.setNombre("Precio Mayorista");


        PrecioProducto p1 = new PrecioProducto();
        p1.setId(1);
        p1.setTipoPrecio(lista);
        p1.setMoneda(pesos);
        p1.setValor(new BigDecimal("12500.50"));
        p1.setRequiereAutorizacion(false);

        PrecioProducto p2 = new PrecioProducto();
        p2.setId(2);
        p2.setTipoPrecio(oferta);
        p2.setMoneda(pesos);
        p2.setValor(new BigDecimal("9999.99"));
        p2.setRequiereAutorizacion(true);

        PrecioProducto p3 = new PrecioProducto();
        p3.setId(3);
        p3.setTipoPrecio(mayorista);
        p3.setMoneda(dolar);
        p3.setValor(new BigDecimal("15.75"));
        p3.setRequiereAutorizacion(false);


        modelPrecioProducto.addElement(p1);
        modelPrecioProducto.addElement(p2);
        modelPrecioProducto.addElement(p3);
    }

    private void bloquearFormStock(boolean enabled) {

        movimientoNegativo.setEnabled(enabled);
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

        /*if(bCodigoBarra.isSelected()){
            // Solicitar foco de forma diferida solo si está activada la búsqueda por código de barra
            SwingUtilities.invokeLater(() -> tCodigoBarra.requestFocusInWindow());
            if(tCodigoBarra.getText().isBlank()){
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
        }*/
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
            if(producto.isEliminado()){
                OptionPane.information(CONSTANTS.i18n.getValue("producto.mensaje.eliminado"));//JOptionPane.showConfirmDialog(GlobalFrame.getInstance().getFrame(), CONSTANT.LANG.getValue("sistema.mensaje.salir"), CONSTANT.TITULO,JOptionPane.YES_NO_OPTION);//

            }
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

        this.producto = producto;

        tCodigo.setText(producto.getCodigo());
        tCodigoBarra.setText(producto.getCodigoBarra());
        tNombre.setText(producto.getNombre());
        tModelo.setText(producto.getModelo());


        boolean dispon = Boolean.TRUE.equals(producto.isDisponible());

        disponibleProd.setSelected(dispon);
        noRequiereStock.setSelected(Boolean.TRUE.equals(producto.isNoRequiereStock()));
        movimientoNegativo.setSelected(Boolean.TRUE.equals(producto.isMovimientoNegativo()));
        precioImpuesto.setSelected(Boolean.TRUE.equals(producto.isPrecioIncluyeImpuesto()));
        //requiereAprob.setSelected(Boolean.TRUE.equals(producto.isReqAprobPrecioEspecial()));

        tNota.setText(producto.getNota());

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
            //cbCategoria.setSelectedItem(producto.getCategoria());
        }
/*
        Marca marca = producto.getMarca();
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

        cbMarca.setSelectedItem(producto.getMarca());


        cbUnidadMedida.setSelectedItem(producto.getUnidadMedida());

        boolean excesivo = producto.getUtilidad() > 100;


        //FALSE SI NO ESTA ELIMINADO
        boolean elim = Boolean.FALSE.equals(producto.isEliminado());

        bEliminar.setEnabled(elim && !dispon);

        tFechaCreacion.setText(producto.getFechaCreacion() != null
                ? FechaUtil.parseFecha(producto.getFechaCreacion())
                : "");

        tFechaActualizacion.setText(producto.getFechaActualizacion() != null
                ? FechaUtil.parseFecha(producto.getFechaActualizacion())
                : "");

        eText(true);

        bloquearFormStock(elim);

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    protected void clearForm(){
        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));



        bEliminar.setEnabled(false);

        tCodigo.setText(null);
        tCodigoBarra.setText(null);
        tNombre.setText(null);
        cbUnidadMedida.setSelectedIndex(-1);
        tModelo.setText(null);

        disponibleProd.setSelected(true);
        noRequiereStock.setSelected(false);


        tNota.setText(null);

        lInfo.setText(null);
        lInfo.setVisible(false);


        precioImpuesto.setSelected(true);

        if(cbCategoria.getItemCount() > 0)
            cbCategoria.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        if(cbMarca.getItemCount() > 0)
            cbMarca.setSelectedIndex(0);

        movimientoNegativo.setSelected(true);
        movimientoNegativo.setEnabled(true);


        tFechaActualizacion.setText("-");
        tFechaCreacion.setText("-");

        tCodigo.requestFocus();

        this.producto = null;

        GlobalFrame.getInstance().getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    public Producto getDataForm(){

        if(producto == null)
            producto = new Producto();

        String cod = tCodigo.getText();
        String codBarra = tCodigoBarra.getText();
        String nombre = tNombre.getText();
        UnidadMedida unidadMedida = (UnidadMedida)cbUnidadMedida.getSelectedItem();
        String modelo = tModelo.getText();
        boolean disp = disponibleProd.isSelected();
        boolean noReqStock = noRequiereStock.isSelected();
        boolean movNegativo = movimientoNegativo.isSelected();
        String nota = tNota.getText();
        String costo = null;//tCosto.getText();
        String utilidad = null;//tUtilidad.getText();
        String precio1 = null;//tPrecio1.getText();
        String precio2 = null;//tPrecio2.getText();
        String precio3 = null;//tPrecio3.getText();
        boolean reqAprob = false;//requiereAprob.isSelected();
        String cantMayor = null;//tCantMayor.getText();
        boolean isImpuesto = precioImpuesto.isSelected();

        if((cod == null || cod.isBlank()) && (codBarra != null &&!codBarra.isBlank())){
            cod = codBarra;
            tCodigo.setText(cod);
        }

        if(cod.isBlank()){
            OptionPane.error(CONSTANTS.i18n.getValue("producto.mensaje.codigo.null"));
            tCodigo.requestFocus();
            return null;
        }

        Marca marca = (Marca)dcbMarca.getSelectedItem();
        Categoria categoria = (Categoria) dcbCategoria.getSelectedItem();

        String stockCrititco = null;//tStockCritico.getText();
        String stockMaximo = null;//tStockMaximo.getText();

        BigDecimal c = BigDecimal.ZERO;
        if(costo!=null && !costo.isBlank()) {
            c = NumberConverter.stringToBigDecimal(costo);
        }

        BigDecimal p1 = BigDecimal.ZERO;
        if(precio1!= null && !precio1.isBlank()) {
            p1 = NumberConverter.stringToBigDecimal(precio1);
        }

        BigDecimal p2 = BigDecimal.ZERO;
        if(precio2!= null && !precio2.isBlank()){
            p2 = NumberConverter.stringToBigDecimal(precio2);
        }

        BigDecimal p3 = BigDecimal.ZERO;
        if(precio3!= null && !precio3.isBlank()) {
            p3 = NumberConverter.stringToBigDecimal(precio3);
        }

        BigDecimal scritico = BigDecimal.ZERO;

        if(stockCrititco !=null && !stockCrititco.isBlank()) {
            scritico = new BigDecimal(stockCrititco);
        }

        BigDecimal smaximo = BigDecimal.ZERO;

        if(stockMaximo != null && !stockMaximo.isBlank()) {
            smaximo = new BigDecimal(stockMaximo);
        }

        int util = 0;
        if(utilidad != null && !utilidad.isBlank()) {
            try {
                util = Integer.parseInt(utilidad);
            }catch (NumberFormatException exc){
                LoggerApp.error("Error al tratar de convertir la utilidad en entero: "+exc.getMessage());
            }
        }

        int cantMayorInt = 0;
        if(cantMayor != null && !cantMayor.isBlank()) {
            cantMayorInt = Integer.parseInt(cantMayor);
        }

        boolean elim = false;

        if(producto.getID() != null){
            elim = !disp;
        }

        producto.setCodigo(cod);
        producto.setCodigoBarra(codBarra);
        producto.setNombre(nombre);
        producto.setUnidadMedida(unidadMedida);
        producto.setModelo(modelo);
        producto.setMarca(marca);
        producto.setCategoria(categoria);
        producto.setDisponible(disp);
        producto.setEliminado(elim);
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
        producto.setStockMinimo(scritico);
        producto.setStockMaximo(smaximo);

        if(producto.getID() == null)
            producto.setFechaCreacion(FechaUtil.parseFecha(tFechaCreacion.getText()));

        producto.setFechaActualizacion(LocalDateTime.now());//FechaUtil.parseFecha(tFechaActualizacion.getText()));

        String cantDisponible = null;//tCantidadDisponible.getText();
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
        cbUnidadMedida.setEnabled(enabled);
        tModelo.setEnabled(enabled);

        cbMarca.setEnabled(enabled);
        bAddMarca.setEnabled(enabled);
        cbCategoria.setEnabled(enabled);

        disponibleProd.setEnabled(enabled);
        noRequiereStock.setEnabled(enabled);
        movimientoNegativo.setEnabled(enabled);

        tNota.setEnabled(enabled);


        precioImpuesto.setEnabled(enabled);
    }

    public void setCantidadDisponible(BigDecimal cant){//, boolean agregar){
        //tCantidadDisponible.setText(String.valueOf(cant));

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


        // Al presionar Enter en tCodigo, pasar el foco a tNombre
        tCodigo.addActionListener(ae -> {
            actionCodigo2();
        });

        bEnter.addActionListener(ae->{
            actionCodigo2();
        });

        bBuscar.addActionListener(ae -> {

            List<Categoria> categorias = new ArrayList<>();
            for (int i = 0; i < dcbCategoria.getSize(); i++) {
                categorias.add(dcbCategoria.getElementAt(i));
            }

            DialogBuscarProducto dialog = new DialogBuscarProducto(GlobalFrame.getInstance().getFrame(), categorias);

            Producto p = dialog.getSelectedProducto();

            if (p != null) {
                skipInputVerifier = false;
                insertData(p);
                tNombre.requestFocus();
                skipInputVerifier = true;
            }
        });

        /*bCodigoBarra.addActionListener((e)->{
            boolean edo = bCodigoBarra.isSelected();
            String value = edo?"producto.inf.buscarporcodbarra":"producto.inf.buscarcodigo";
            tCodigo.setPlaceHolder(CONSTANTS.i18n.getValue(value));
            tCodigo.requestFocus();
        });*/
    }

    private void actionCodigo2() {
        /*if(bCodigoBarra.isSelected()) {
            tNombre.requestFocus();
        }
        else {
            tCodigoBarra.requestFocus();
        }*/
    }

    /**
     * Verifica si hay datos en el formulario
     */
    public boolean hasFormData() {
        return !tCodigo.getText().isBlank()
                || !tCodigoBarra.getText().isBlank()
                || !tNombre.getText().isBlank();
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

    public void loadData() {
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
        } catch (ProductoException exc) {
            OptionPane.error(exc);
        }
    }
}
