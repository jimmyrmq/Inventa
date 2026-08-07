package com.djm.inventa.producto.view.producto;

import com.djm.inventa.modelo.Atributo;
import com.djm.inventa.modelo.AtributoValor;
import com.djm.inventa.modelo.Moneda;
import com.djm.inventa.modelo.PrecioProducto;
import com.djm.inventa.modelo.ProductoVariante;
import com.djm.inventa.modelo.TipoPrecio;
import com.djm.inventa.persistence.MonedaDAO;
import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.core.exception.ProductoException;
import com.djm.inventa.persistence.AtributoDAO;
import com.djm.inventa.persistence.AtributosValorDAO;
import com.djm.inventa.persistence.TipoPrecioDAO;
import com.djm.inventa.producto.view.producto.renderer.ThreeLabelRendererAtrib;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.component.Table;
import com.djm.inventa.ui.component.TextField;
import com.djm.inventa.producto.view.producto.renderer.ThreeLabelRendererPrecio;
import com.djm.inventa.ui.component.renderer.TwoColumnRenderer;
import com.djm.inventa.ui.util.BorderUtil;
import com.djm.inventa.util.DecimalField;
import com.djm.inventa.util.NumberUtils;
import com.djm.ui.GlobalFrame;
import com.djm.ui.LayoutPanel;
import com.djm.ui.component.Button;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.OptionPane;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PanelVariante extends JDialog {
    private Dimension dimLabel;

    //Variante
    private TextField tSKU,tCodigoBarra, tCantidadStock, tCantidadMinima, tCantidadMaxima,
            tPrecio, tUnidad;

    private JList<AtributoValor> listAtributosValor;
    private JList<PrecioProducto> listaPrecioProducto;
    private DefaultListModel<PrecioProducto> modelPrecioProducto;
    private DefaultListModel<AtributoValor> modelAtributo;

    private JComboBox<Atributo> cbAtributo;
    private JComboBox<AtributoValor> cbAtributoValor;
    private JComboBox<TipoPrecio> cbTipoPrecio;
    private JComboBox<Moneda> cbMoneda;
    private DefaultComboBoxModel<Atributo> dcbAtributo;
    private DefaultComboBoxModel<AtributoValor> dcbAtributoValor;
    private DefaultComboBoxModel<TipoPrecio> dcbTipoPrecio;
    private DefaultComboBoxModel<Moneda> dcbMoneda;

    private JCheckBox disponible, mantenerPrecio;//, reqAutPrec;

    private Button bDelPrecio, bEditPrecio, bDelAtrib;
    private JButton bBuscar, bAddPrecio, bAddAtributo, bAgregar, bCerrar;

    private LostFocusPrecio lostFocusPrecio = new LostFocusPrecio();
    //private final UnidadMedida unidad;

    private JTextField editorAtributo;
    private JTextField editorAtributoValor;

    private final AtributoDAO atributoDAO = new AtributoDAO();
    private final AtributosValorDAO atributosValorDAO = new AtributosValorDAO();

    private ImageIcon iDelete;
    private ImageIcon iEdit;

    private Table mtc;
    private final List<ProductoVariante> listProducto;
    private ProductoVariante productoVariante = null;

    public PanelVariante(Table<ProductoVariante> mtc, ImageIcon iProduct){//UnidadMedida unidad){
        super(GlobalFrame.getInstance().getFrame(), CONSTANTS.i18n.getValue("producto.border.title.variante"), true);

        this.mtc = mtc;
        listProducto = new ArrayList<>();

        //this.unidad = unidad;

        iDelete =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("delete2"),ContanstLocal.COLOR_TEXT_FOREGROUND,false));
        iEdit =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("edit"),ContanstLocal.COLOR_TEXT_FOREGROUND,false));
        ImageIcon iBuscar =  new ImageIcon(ColorFilter.filterImage( IconManager.get16("buscar"),ContanstLocal.COLOR_TEXT_FOREGROUND,false));

        bAgregar = new JButton(CONSTANTS.i18n.getValue("button.agregar"));
        bCerrar = new JButton(CONSTANTS.i18n.getValue("button.cerrar"));
        bBuscar = new JButton(CONSTANTS.i18n.getValue("producto.button.buscar_variante"), iBuscar);
        bBuscar.setEnabled(false);

        /*// Texto debajo del icono
        bBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        // Texto centrado respecto al icono
        bBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        // Centrar todo el contenido del botón
        bBuscar.setHorizontalAlignment(SwingConstants.CENTER);*/

        bAgregar.addActionListener((ae)->{
            agregarProducto();
        });

        bCerrar.addActionListener(ae->{
            setVisible(true);
            dispose();
        });

        setLayout(new GridBagLayout());

        add(panelVariante(), LayoutPanel.constantePane(0, 0, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 10, 0.0f, 0.0f));
        add(bBuscar, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 5, 10, 1.0f, 0.0f));
        add(bCerrar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 0, 5, 10, 1.0f, 0.0f));
        add(bAgregar, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 5, 10, 0.0f, 0.0f));

        loadData();

        pack();
        Dimension screenSize = getPreferredSize();
        setMinimumSize(screenSize);

        //Agregamos una imagen en frame
        if(iProduct != null)
            setIconImage(iProduct.getImage());

        setResizable(true);

        setLocationRelativeTo(GlobalFrame.getInstance().getFrame());
        setVisible(true);

    }


    private JPanel panelVariante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.variante")));



        disponible = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.habilitado"));
        disponible.setOpaque(false);
        disponible.setSelected(true);

        JLabel lCodigoBarra = new JLabel(CONSTANTS.i18n.getLabel("producto.label.codigobarra"));
        JLabel lSKU = new JLabel(CONSTANTS.i18n.getLabel("producto.label.sku"));
        dimLabel = lCodigoBarra.getPreferredSize();//new Dimension(90,23);

        tCodigoBarra = new TextField(ContanstLocal.DIM_COLUM_TEXT,20,true);
        tSKU = new TextField(ContanstLocal.DIM_COLUM_TEXT,20);

        panel.add(lSKU, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tSKU, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lCodigoBarra, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigoBarra, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(disponible, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 5, 0, 0, 1.0f, 0.0f));

        panel.add(panelAtributos(), LayoutPanel.constantePane(0, 2, 0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        panel.add(panelCantidad(), LayoutPanel.constantePane(0, 3, 0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 10, 0, 0, 0, 1.0f, 0.0f));
        panel.add(panelPrecio(), LayoutPanel.constantePane(0, 4, 0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 10, 0, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private JPanel panelAtributos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        //panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

        bAddAtributo =  new JButton(CONSTANTS.i18n.getValue("producto.button.add.atributos"));
        bAddAtributo.setEnabled(false);

        bDelAtrib = new Button(iDelete,true);
        bDelAtrib.setPaintBack(false);
        bDelAtrib.setButtonIcon(true);
        bDelAtrib.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar"));
        bDelAtrib.setFocusable(false);
        bDelAtrib.setColorBackIn(ContanstLocal.COLOR_PANEL_BACKGROUND);
        bDelAtrib.setColorBackSelected(ContanstLocal.COLOR_TEXT_BACKGROUND);
        bDelAtrib.setColorFilter(ContanstLocal.COLOR_TEXT_FOREGROUND);

        bDelAtrib.setEnabled(false);

        bAddAtributo.addActionListener((ae)->{
            agregarTributoList();
        });

        bDelAtrib.addActionListener(ae->{
            eliminarTributoList();
        });

        JLabel lAtributo = new JLabel(CONSTANTS.i18n.getLabel("producto.label.atributo"));
        //JLabel lUnidad = new JLabel(CONSTANTS.i18n.getLabel("producto.label.presentacion"));

        lAtributo.setPreferredSize(dimLabel);

        tUnidad = new TextField(10);
        tUnidad.setEditable(false);
        //tUnidad.setText(this.unidad.getNombre()+" ("+this.unidad.getSimbolo()+")");

        dcbAtributo = new DefaultComboBoxModel<Atributo> ();
        dcbAtributoValor = new DefaultComboBoxModel<AtributoValor>();

        cbAtributo = new JComboBox<>(dcbAtributo);
        cbAtributo.setPreferredSize(CONSTANTS.CDDIM_120);

        cbAtributoValor = new JComboBox<>(dcbAtributoValor);
        cbAtributoValor.setPreferredSize(CONSTANTS.CDDIM_120);
        cbAtributoValor.setEditable(false);

        modelAtributo = new DefaultListModel<>();
        listAtributosValor = new JList<>(modelAtributo);

        //listAtributosValor.setPreferredSize(new Dimension(400, 50));
        listAtributosValor.setCellRenderer(new ThreeLabelRendererAtrib());

        JScrollPane jsp = new JScrollPane(listAtributosValor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);
        jsp.setPreferredSize(new Dimension(250, 50));

        Atributo atributo = new Atributo();
        atributo.setNombre(CONSTANTS.i18n.getValue("label.ninguno"));
        dcbAtributo.addElement(atributo);

        AtributoValor atributoValor = new AtributoValor();
        atributoValor.setAtributo(atributo);
        atributoValor.setValor("");

        dcbAtributoValor.addElement(atributoValor);

        cbAtributoValor.setEnabled(false);

        eventoAtributos();

        panel.add(lAtributo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbAtributo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(cbAtributoValor, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(bAddAtributo, LayoutPanel.constantePane(2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        //panel.add(lUnidad, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        //panel.add(tUnidad, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(jsp, LayoutPanel.constantePane(1, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bDelAtrib, LayoutPanel.constantePane(4, 2, 0, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private JPanel panelPrecio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.precio")));

        JLabel lTipoPrecio = new JLabel(CONSTANTS.i18n.getLabel("producto.label.tipo_precio"));
        JLabel lMoneda = new JLabel(CONSTANTS.i18n.getLabel("producto.label.moneda"));
        JLabel lPrecio = new JLabel(CONSTANTS.i18n.getLabel("producto.label.precio"));

        //reqAutPrec = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.reqAprobPrecio"));
        //reqAutPrec.setOpaque(false);

        //lTipoPrecio.setPreferredSize(ContanstLocal.dimLabel);

        mantenerPrecio = new JCheckBox(CONSTANTS.i18n.getValue("producto.label.mantener_precio"));
        mantenerPrecio.setSelected(true);

        bAddPrecio = new JButton(CONSTANTS.i18n.getValueMsgFormat("producto.button.addprecio"));
        bAddPrecio.addActionListener(ae->{
            agregarPrecioList();
        });

        tPrecio = new TextField(10,10);
        tPrecio.setSize(CONSTANTS.CDDIM_120);
        tPrecio.setPreferredSize(CONSTANTS.CDDIM_120);
        tPrecio.addFocusListener(lostFocusPrecio);
        DecimalField.configurar(tPrecio, true);

        tPrecio.setText("0,00");
        tPrecio.addActionListener(ae->{
            agregarPrecioList();
        });

        dcbTipoPrecio = new DefaultComboBoxModel<TipoPrecio>();
        dcbMoneda = new DefaultComboBoxModel<Moneda>();

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

        tPrecio.setColumns(cbMoneda.getPreferredSize().width / tPrecio.getFontMetrics(tPrecio.getFont()).charWidth('m')-1);

        panel.add(lTipoPrecio, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbTipoPrecio, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lMoneda, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(cbMoneda, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(lPrecio, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bAddPrecio, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 5, 0, 0, 5, 0.0f, 0.0f));

        panel.add(mantenerPrecio, LayoutPanel.constantePane(0, 4, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        //panel.add(reqAutPrec, LayoutPanel.constantePane(0, 1, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));

        panel.add(pTablePrecio(), LayoutPanel.constantePane(2, 0, 0, 4, GridBagConstraints.VERTICAL, GridBagConstraints.LINE_START, 0, 10, 0, 5, 0.0f, 1.0f));

        return panel;
    }

    private JPanel pTablePrecio(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        bDelPrecio = new Button(iDelete,true);
        bDelPrecio.setPaintBack(false);
        bDelPrecio.setButtonIcon(true);
        bDelPrecio.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar"));
        bDelPrecio.setFocusable(false);
        bDelPrecio.setColorBackIn(ContanstLocal.COLOR_PANEL_BACKGROUND);
        bDelPrecio.setColorBackSelected(ContanstLocal.COLOR_TEXT_BACKGROUND);
        bDelPrecio.setColorFilter(ContanstLocal.COLOR_TEXT_FOREGROUND);

        bEditPrecio = new Button(iEdit,true);
        bEditPrecio.setPaintBack(false);
        bEditPrecio.setButtonIcon(true);
        bEditPrecio.setToolTipText(CONSTANTS.i18n.getValue("producto.label.buscar"));
        bEditPrecio.setFocusable(false);
        bEditPrecio.setColorBackIn(ContanstLocal.COLOR_PANEL_BACKGROUND);
        bEditPrecio.setColorBackSelected(ContanstLocal.COLOR_TEXT_BACKGROUND);
        bEditPrecio.setColorFilter(ContanstLocal.COLOR_TEXT_FOREGROUND);

        bEditPrecio.addActionListener(ae->{
            editarPrecio();
        });

        bDelPrecio.addActionListener(ae->{
            eliminarPrecio();
        });

        bDelPrecio.setEnabled(false);
        bEditPrecio.setEnabled(false);

        modelPrecioProducto = new DefaultListModel<>();
        listaPrecioProducto = new JList<>(modelPrecioProducto);

        //lista.setPreferredSize(new Dimension(400, 50));

        JScrollPane jsp = new JScrollPane(listaPrecioProducto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setViewportBorder(null);//BorderFactory.createLineBorder(GlobalUI.getInstance().getTheme().getColorBorderField()));
        jsp.getViewport().setOpaque(true);
        jsp.setOpaque(false);

        jsp.setPreferredSize(new Dimension(250, 70));

        listaPrecioProducto.setCellRenderer(
                new ThreeLabelRendererPrecio<>(
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

        panel.add(jsp, LayoutPanel.constantePane(0, 0, 1, 2, GridBagConstraints.VERTICAL, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 1.0f));
        panel.add(bEditPrecio, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 2, 0, 10, 1.0f, 0.0f));
        panel.add(bDelPrecio, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 2, 2, 0, 10, 1.0f, 0.0f));

        return panel;
    }

    private JPanel panelCantidad() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new BorderUtil(CONSTANTS.i18n.getValue("producto.border.title.stock")));

        JLabel lCantidadMinima = new JLabel(CONSTANTS.i18n.getLabel("producto.label.stockcritico"));
        JLabel lCantidadMaxima = new JLabel(CONSTANTS.i18n.getLabel("producto.label.stock_maximo"));
        JLabel lCantidadStock = new JLabel(CONSTANTS.i18n.getLabel("producto.label.cantidad_disponible"));

        //lCantidadMinima.setPreferredSize(ContanstLocal.dimLabel);

        tCantidadStock = new TextField(7,10);
        tCantidadMinima = new TextField(7,10);
        tCantidadMaxima = new TextField(7,10);

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
        panel.add(tCantidadStock, LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private void agregarTributoList() {
        try{
            Atributo atributo = getValue(Atributo.class, cbAtributo);
            if(atributo.getID() != null) {

                AtributoValor atributoValor = getValue(AtributoValor.class, cbAtributoValor);
                atributoValor.setAtributo(atributo);

                //boolean existe = false;

                for (int i = 0; i < modelAtributo.getSize(); i++) {
                    AtributoValor item = modelAtributo.getElementAt(i);
                    if(//item.getID() == atributoValor.getID() &&
                         item.getAtributo().getID() ==  atributoValor.getAtributo().getID()) {
                        OptionPane.information(CONSTANTS.i18n.getValue("producto.atributos.add.error"));
                        return;
                    }
                }

                modelAtributo.addElement(atributoValor);

                if(!bDelAtrib.isEnabled()){
                    bDelAtrib.setEnabled(true);
                }
            }
        }
        catch (Exception exc) {}

    }

    private void eliminarTributoList() {
        try{
            int index = listAtributosValor.getSelectedIndex();

            if (index != -1) {
                //listAtributosValor.remove(index);
                modelAtributo.removeElementAt(index);

                if(modelAtributo.getSize() == 0){
                    bDelAtrib.setEnabled(false);
                }
            }else{
                OptionPane.information(CONSTANTS.i18n.getValue("producto.atributos.del.seleccion"));
            }
        }
        catch (Exception e) {
            System.err.println("Error al eliminar lista de atributos: "+e.getMessage());
        }
    }

    private void eventoAtributos() {
        cbAtributo.addActionListener(e -> {

            /*Object sel = dcbAtributo.getSelectedItem();

            if (!(sel instanceof Atributo))
                return;

            Atributo atributo = (Atributo) sel;*/

            try {
                Atributo atributo = getValue(Atributo.class, cbAtributo);

                cbAtributoValor.setEnabled(atributo.getID() != null);
                bAddAtributo.setEnabled(atributo.getID() != null);

                if (atributo.getID() != null && atributo.getID() == -1) {

                    cbAtributo.setEditable(true);

                    editorAtributo = (JTextField) cbAtributo.getEditor().getEditorComponent();

                    editorAtributo.addActionListener(ev -> agregarTributo());

                    SwingUtilities.invokeLater(() -> {
                        editorAtributo.setText("");
                        editorAtributo.requestFocus();

                    });
                } else {
                    cbAtributo.setEditable(false);
                    cargarAtributosValor(atributo.getID());
                }
            }catch (Exception ex) {}

        });

        cbAtributoValor.addActionListener(e -> {

            try {
                AtributoValor atributoValor = getValue(AtributoValor.class, cbAtributoValor);

                if (atributoValor.getID() == -1) {

                    cbAtributoValor.setEditable(true);

                    editorAtributoValor = (JTextField) cbAtributoValor.getEditor().getEditorComponent();

                    editorAtributoValor.addActionListener(ev -> agregarTributoValor());

                    SwingUtilities.invokeLater(() -> {
                        editorAtributoValor.setText("");
                        editorAtributoValor.requestFocus();
                    });
                }
                else {
                    cbAtributoValor.setEditable(false);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        });
    }

    private void cargarAtributosValor(Integer idAtributos) {
        dcbAtributoValor.removeAllElements();

        try {
            if(idAtributos != null) {
                List<AtributoValor> list = atributosValorDAO.listarAtributoValor(idAtributos);

                for (AtributoValor atributoValor : list) {
                    dcbAtributoValor.addElement(atributoValor);
                }
            }

            AtributoValor atributoValor = new AtributoValor();
            atributoValor.setID(-1);
            atributoValor.setValor(CONSTANTS.i18n.getValue("label.nuevo"));

            dcbAtributoValor.addElement(atributoValor);

        } catch (ProductoException e) {
            throw new RuntimeException(e);
        }
    }

    private void agregarTributo(){
        editorAtributo = (JTextField) cbAtributo.getEditor().getEditorComponent();

        String nombre = editorAtributo.getText().trim();

        if(nombre.isEmpty()) {
            return;
        }

        // Buscar existente
        for (int i = 0; i < cbAtributo.getItemCount(); i++) {
            Atributo atributo = cbAtributo.getItemAt(i);
            if (atributo.getNombre().equals(nombre)) {
                cbAtributo.setSelectedItem(atributo);
                cargarAtributosValor(atributo.getID());
                return;
            }
        }

        Atributo atributo = atributoDAO.guardarAtributo(nombre);
        if(atributo != null) {
            dcbAtributo.insertElementAt(atributo, dcbAtributo.getSize() - 1);

            cbAtributo.setSelectedItem(atributo);
            cargarAtributosValor(atributo.getID());
        }
        else{
            OptionPane.error(CONSTANTS.i18n.getValue("producto.error.atributo.guardar"));
        }

        cbAtributo.setEditable(false);
    }

    private void agregarTributoValor(){
        editorAtributoValor = (JTextField) cbAtributoValor.getEditor().getEditorComponent();

        String nombre = editorAtributoValor.getText().trim();

        if(nombre.isEmpty()) {
            return;
        }

        // Buscar existente
        for (int i = 0; i < cbAtributoValor.getItemCount(); i++) {
            AtributoValor atributoValor = cbAtributoValor.getItemAt(i);
            if (atributoValor.getValor().equals(nombre)) {
                cbAtributoValor.setSelectedItem(atributoValor);
                return;
            }
        }

        Atributo atributo = (Atributo)dcbAtributo.getSelectedItem();

        AtributoValor atributoValor = atributosValorDAO.guardarAtributoValor(atributo.getID(), nombre);

        if(atributoValor != null) {
            atributoValor.setAtributo(atributo);
            dcbAtributoValor.insertElementAt(atributoValor, dcbAtributoValor.getSize() - 1);

            cbAtributoValor.setSelectedItem(atributoValor);
        }
        else{
            OptionPane.error(CONSTANTS.i18n.getValue("producto.error.atributo.guardar"));
        }

        cbAtributoValor.setEditable(false);
    }

    private void agregarPrecioList() {
        try {
            TipoPrecio tipoPrecio = getValue(TipoPrecio.class, cbTipoPrecio);
            Moneda moneda = getValue(Moneda.class, cbMoneda);
            String precio = tPrecio.getText();

            if(precio.isBlank()){
                OptionPane.error(CONSTANTS.i18n.getValue("producto.error.precio.vacio"));
                tPrecio.requestFocus();
                return;
            }

            PrecioProducto precioProducto = null;
            int index = -1;
            // Buscar existente
            for (int i = 0; i < modelPrecioProducto.getSize(); i++) {
                PrecioProducto item = modelPrecioProducto.getElementAt(i);
                if (item.getTipoPrecio().getID() == tipoPrecio.getID()
                    && item.getMoneda().getID() == moneda.getID()) {
                    precioProducto = item;
                    index = i;
                    break;
                }
            }

            BigDecimal precioObj = NumberUtils.parseBigDecimal(precio);

            if(precioProducto != null && BigDecimal.ZERO.compareTo(precioProducto.getValor()) != 0){
                //Si el precio es el mismo no lo cambia
                if(precioObj.compareTo(precioProducto.getValor()) == 0){
                    clearFormPrecio();

                    return;
                }

                String viejo = NumberUtils.format(precioProducto.getValor());
                String nuevo = NumberUtils.format(precioObj);

                int n = OptionPane.questionYesOrKey(CONSTANTS.i18n.getValueMsgFormat("producto.question.precio.existente", viejo, nuevo));

                if(n != JOptionPane.YES_OPTION) {
                    return;
                }
            }else
                precioProducto = new PrecioProducto();

            precioProducto.setTipoPrecio(tipoPrecio);
            precioProducto.setMoneda(moneda);
            precioProducto.setValor(precioObj);

            if(index != -1){
                modelPrecioProducto.setElementAt(precioProducto, index);
            }
            else {
                modelPrecioProducto.addElement(precioProducto);
                if(!bDelPrecio.isEnabled()){
                    bDelPrecio.setEnabled(true);
                    bEditPrecio.setEnabled(true);
                }

            }

            clearFormPrecio();

        }
        catch (Exception e){}
    }

    private void agregarProducto() {

        if( this.productoVariante == null )
            this.productoVariante = new ProductoVariante();

        productoVariante.setSKU(tSKU.getText());
        productoVariante.setCodigoBarra(tCodigoBarra.getText());
        productoVariante.setDisponible(disponible.isSelected());

        productoVariante.setCantidadMaxima(NumberUtils.parseBigDecimal(tCantidadMaxima.getText()));
        productoVariante.setCantidadMinina(NumberUtils.parseBigDecimal(tCantidadMinima.getText()));
        productoVariante.setCantidadStock(NumberUtils.parseBigDecimal(tCantidadStock.getText()));

        ArrayList<PrecioProducto> listaPrecio = Collections.list(modelPrecioProducto.elements());
        productoVariante.setPrecioProductos(listaPrecio);

        ArrayList<AtributoValor> listaAtribuso = Collections.list(modelAtributo.elements());
        productoVariante.setListAtributos(listaAtribuso);

        mtc.addRow(productoVariante);

        listProducto.add(productoVariante);

        bBuscar.setEnabled(true);

        clearForm();
    }

    private void clearFormPrecio(){

        tPrecio.setText("0,00");
        tPrecio.requestFocus();

        if(dcbMoneda.getSize() > 0)
            cbMoneda.setSelectedIndex(0);

        if(dcbTipoPrecio.getSize() > 0)
            cbTipoPrecio.setSelectedIndex(0);
    }

    private void editarPrecio() {

        int index = listaPrecioProducto.getSelectedIndex();
        if(index != -1){
            PrecioProducto item = modelPrecioProducto.getElementAt(index);
            TipoPrecio tipoPrecio = item.getTipoPrecio();
            Moneda moneda = item.getMoneda();

            cbMoneda.setSelectedItem(moneda);
            cbTipoPrecio.setSelectedItem(tipoPrecio);
            tPrecio.setText(NumberUtils.format(item.getValor()));
            tPrecio.requestFocus();
            tPrecio.selectAll();
        }
    }

    private void eliminarPrecio(){

        int index = listaPrecioProducto.getSelectedIndex();
        if(index != -1){
            PrecioProducto item = modelPrecioProducto.getElementAt(index);
            //TipoPrecio tipoPrecio = item.getTipoPrecio();
            //Moneda moneda = item.getMoneda();
            boolean eliminar = true;

            if(item.getValor().compareTo(BigDecimal.ZERO) > 0) {
                String precio = NumberUtils.format(item.getValor());

                int n = OptionPane.questionYesOrKey(CONSTANTS.i18n.getValueMsgFormat("producto.question.precio.eliminar",
                        item.getTipoPrecio().getNombre(), precio, item.getMoneda().getSimbolo()));

                eliminar = n == JOptionPane.YES_OPTION;
            }

            if (eliminar) {
                modelPrecioProducto.removeElement(item);
                if (modelPrecioProducto.getSize() == 0) {
                    bDelPrecio.setEnabled(false);
                    bEditPrecio.setEnabled(false);
                }
            }
        }

    }

    private void loadData(){
        try{

            editorAtributo = (JTextField) cbAtributo.getEditor().getEditorComponent();

            List<Atributo> atributoList = atributoDAO.listarAtributos();

            for (Atributo atributo : atributoList) {
                dcbAtributo.addElement(atributo);
            }

            Atributo atributo =  new Atributo();
            atributo.setID(-1);
            atributo.setNombre(CONSTANTS.i18n.getValue("label.nuevo"));

            dcbAtributo.addElement(atributo);

            //Cargamos la lista de precios
            TipoPrecioDAO tipoPrecioDAO = new TipoPrecioDAO();
            List<TipoPrecio> listTipoPrecio = tipoPrecioDAO.listaTipoPrecio();
            dcbTipoPrecio.addAll(listTipoPrecio);
            cbTipoPrecio.setSelectedIndex(0);

            //Cargamos Moneda
            MonedaDAO monedaDAO = new MonedaDAO();
            List<Moneda> listMoneda = monedaDAO.listarMoneda();
            dcbMoneda.addAll(listMoneda);
            cbMoneda.setSelectedIndex(0);

        } catch (ProductoException exc) {
            OptionPane.error(exc);
        }
    }

    private class LostFocusPrecio implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            //procesadorProducto.validadPrecio(tCosto, tPrecio1, lPrecio1Adv, tPrecio2, lPrecio2Adv, tPrecio3, lPrecio3Adv);
        }
    }

    private <T> T getValue(Class<T> type, JComboBox<?> dcm)throws Exception {
        Object sel = dcm.getSelectedItem();

        if (!type.isInstance(sel)) {
            throw new Exception("El valor de seleccionado no es valido");
        }

        //T value = (T) sel;

        return type.cast(sel);//value;
    }

    private void clearForm() {
        productoVariante = null;

        tSKU.setText(null);
        tCodigoBarra.setText(null);
        disponible.setSelected(true);

        tCantidadMaxima.setText("0");
        tCantidadMinima.setText("0");
        tCantidadStock.setText("0");

        modelAtributo.removeAllElements();
        bDelAtrib.setEnabled(false);

        if(!mantenerPrecio.isSelected()) {
            modelPrecioProducto.removeAllElements();
            bEditPrecio.setEnabled(false);
            bDelPrecio.setEnabled(false);
        }

        tSKU.requestFocus();
    }

    public void insertData(ProductoVariante productoVariante){
        this.productoVariante   = productoVariante;

        tSKU.setText(productoVariante.getSKU());
        tCodigoBarra.setText(productoVariante.getCodigoBarra());
        disponible.setSelected(productoVariante.getDisponible());

        tCantidadMaxima.setText(NumberUtils.format(productoVariante.getCantidadMaxima()));
        tCantidadMinima.setText(NumberUtils.format(productoVariante.getCantidadMinina()));
        tCantidadStock.setText(NumberUtils.format(productoVariante.getCantidadStock()));

        modelAtributo.addAll(productoVariante.getListAtributos());
        if(modelAtributo.getSize() > 0)
            bDelAtrib.setEnabled(true);

        modelPrecioProducto.addAll(productoVariante.getPrecioProductos());

        if(modelPrecioProducto.getSize() > 0) {
            bEditPrecio.setEnabled(true);
            bDelPrecio.setEnabled(true);
        }

        tSKU.requestFocus();

    }

    public List<ProductoVariante> getListVariantes(){
        return listProducto;
    }
}
