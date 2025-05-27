package com.djm.inventa.admin.vista.promociones;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Button;
import com.djm.inventa.admin.vista.component.Table;
import com.djm.inventa.admin.vista.component.TextField;
import com.djm.inventa.admin.vista.component.renderer.StatusIconProductRenderer;
import com.djm.inventa.admin.vista.component.renderer.TipoEtiqueta;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.ToggleButton;
import com.djm.ui.component.border.CustomBorder;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPaquetes extends JPanel {
    private ModelTableProdPaqueteCustom mpc;
    private Button bEliminar;
    private Table<Producto> tabla;
    private TextField tCodigo;
    private JTextField tNombre, tCosto,tPrecioVenta, tCantidad;//, tCategoria
    private ToggleButton bCodigoBarra;
    private Color color2 = UIManager.getColor("TextField.background");
    private Color color3 = UIManager.getColor("TextField.foreground");
    private Color cborder = UIManager.getColor("TextField.shadow");//"TableHeader.separatorColor");
    public PanelPaquetes() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        Color colButton = PropiedadesSistema.getColor("Label.colorDarker");
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = color3;
        }

        bEliminar = new Button(new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/delete.png"), colButton, true)));
        bEliminar.setToolTipText(CONSTANTS.LANG.getValue("promocion.producto.tooltiptext.borrar_producto"));

        pTable();

        Border border = BorderFactory.createMatteBorder(1, 1, 0, 1, cborder);//new CustomBorder(1,Color.BLUE,2,Color.BLUE,3,Color.BLUE,4,Color.BLUE);

        add(pDetallesProducto(), LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        add(tabla.getPanel(border), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        add(bEliminar, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 8, 2, 0, 0, 0.0f, 0.0f));
        add(panelTotal(), LayoutPanel.constantePane(0, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));


    }
    private JPanel pDetallesProducto(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JButton bAgregar = new JButton(CONSTANTS.LANG.getValue("button.agregar"));

        tCodigo = new TextField(20,20);
        tNombre = new TextField(27,50);
        //tCategoria = new JTextField(10);
        tCosto = new JTextField(10);
        tPrecioVenta = new JTextField(10);
        tCantidad = new JTextField(10);

        tNombre.setEditable(false);
        //tCategoria.setEditable(false);
        tCosto.setEditable(false);
        tPrecioVenta.setEditable(false);

        tCodigo.setToolTipText(CONSTANTS.LANG.getValue("producto.inf.buscarcodigo"));
        tCodigo.setPlaceHolder(CONSTANTS.LANG.getValue("producto.inf.buscarcodigo"));

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

        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.codigoproducto")), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(bCodigoBarra, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.nombre")), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        /*panel.add(new JLabel(CONSTANTS.LANG.getValue("producto.label.categoria")), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCategoria, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        */panel.add(new JLabel(CONSTANTS.LANG.getValue("producto.label.preciocompra")), LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCosto, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(new JLabel(CONSTANTS.LANG.getValue("producto.label.precio1")), LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecioVenta, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.cantidad")), LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidad, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));

        panel.add(bAgregar, LayoutPanel.constantePane(2, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private void pTable(){

        mpc = new ModelTableProdPaqueteCustom();

        ModeloTabla<Producto> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 70);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //loadTable();

        tabla.setDefaultRenderer(EtiquetaComponent.class, new StatusIconProductRenderer(TipoEtiqueta.NONE, 5));

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tabla.isEnabled() && e.getClickCount() == 2) {
                    Producto prod = tabla.getSelectedItem();

                    if(prod!=null) {
                        //fillDataProduct(prod);
                    }
                }
            }
        });
    }
    private JPanel panelTotal(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);

        Color colLine = UIManager.getColor("TableHeader.bottomSeparatorColor");//""TextField.shadow");
        panel.setBorder(new CustomBorder(1, colLine, 1, cborder, 1, cborder, 1, cborder));

        panel.setBackground(UIManager.getColor("Table.background"));

        JLabel lTotal = new JLabel(CONSTANTS.LANG.getValue("label.total"));
/*        lTotal.setOpaque(true);
        lTotal.setBackground(Color.pink);*/

        Font font1 = lTotal.getFont().deriveFont(Font.BOLD);;//new Font(nombre,Font.BOLD,tam);

        lTotal.setFont(font1);

        JLabel lCosto = new JLabel("0,00");
        JLabel lVenta = new JLabel("0,00");
        JLabel lCantidad = new JLabel("0");
        lCosto.setPreferredSize(new Dimension(100,16));
        lVenta.setPreferredSize(new Dimension(100,16));
        lCantidad.setPreferredSize(new Dimension(90,16));
        /*lVenta.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lCosto.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lCantidad.setAlignmentX(Component.RIGHT_ALIGNMENT);*/

        lCosto.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
        lVenta.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
        lCantidad.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha

        lCosto.setBorder(new EmptyBorder(0, 0, 0, 3));
        lVenta.setBorder(new EmptyBorder(0, 0, 0, 3));
        lCantidad.setBorder(new EmptyBorder(0, 0, 0, 3));
        /*
        lCosto.setOpaque(true);
        lCosto.setBackground(Color.blue);
        lVenta.setOpaque(true);
        lVenta.setBackground(Color.red);
        lCantidad.setOpaque(true);
        lCantidad.setBackground(Color.magenta);*/

        panel.add(lTotal, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 1.0f, 0.0f));
        panel.add(lCosto, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 1, 0, 0, 0.0f, 0.0f));
        panel.add(lVenta, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 1, 0, 0, 0.0f, 0.0f));
        panel.add(lCantidad, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 1, 0, 0, 0.0f, 0.0f));

        Dimension dim = panel.getSize();
        dim.height = 30;
        dim.width = mpc.getDimensionX() +2;

        panel.setPreferredSize(dim);

        return panel;
    }

}
