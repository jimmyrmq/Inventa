package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Button;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ToolBarProducto extends JPanel {
    private final ProductoListener productoListener = new ProductoListener();
    private JTextField tBuscar;
    private JButton bEliminar, bBuscar, bCambiarPrecio, bImportar, bClose;
    private final String carp = "16/";
    private ImageIcon eliminarIcon, buscarIcon,cambiarPrecioIcon, importarIcon, closeIcon;
    private JToggleButton bCodigo, bProducto;
    private ImageIcon iProd, iCod,lupaText;
    private static final String[] DATA = {
            "Manzana", "Banana", "Naranja", "Fresa", "Uva",
            "Mango", "Papaya", "Piña", "Kiwi", "Cereza"
    };
    public ToolBarProducto(){
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 3));
        init();
    }

    private void init() {
        tBuscar = new JTextField(30);

        bProducto = new JToggleButton(iProd);
        bProducto.setSelected(true);
        bProducto.setFocusable(false);
        bProducto.setBackground(UIManager.getColor("TextField.background"));

        bProducto.addActionListener((ae)->{
            this.tBuscar.putClientProperty("JTextField.placeholderText", CONSTANTS.LANG.getValue("producto.label.buscar_cod_producto"));
            tBuscar.requestFocus();
        });

        bCodigo = new JToggleButton(iCod);
        bCodigo.setFocusable(false);

        bCodigo.addActionListener((ae)->{
            this.tBuscar.putClientProperty("JTextField.placeholderText", CONSTANTS.LANG.getValue("producto.label.buscar_cod_barra"));
            tBuscar.requestFocus();
        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(bProducto);
        bg.add(bCodigo);

        JToolBar searchToolbar = new JToolBar();
        searchToolbar.add(bProducto);
        searchToolbar.add(bCodigo);

        this.tBuscar.putClientProperty("JTextField.placeholderText", CONSTANTS.LANG.getValue("producto.label.buscar_cod_producto"));
        this.tBuscar.putClientProperty("JTextField.trailingComponent", searchToolbar);
        this.tBuscar.putClientProperty("JTextField.leadingIcon", lupaText);
        Dimension dim = new Dimension(280, 23);

        this.tBuscar.setMinimumSize(dim);
        this.tBuscar.setPreferredSize(dim);

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setFocusable(false);
        Dimension dim0 = new Dimension(dim.width + 32, 1000);
        popupMenu.setMinimumSize(dim0);
        this.tBuscar.getDocument().addDocumentListener(new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) {
               updatePopup();
           }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePopup();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePopup();
            }

            private void updatePopup() {
                String input = tBuscar.getText().toLowerCase();
                popupMenu.removeAll();

                if (input.isEmpty()) {
                    popupMenu.setVisible(false);
                } else {
                    List<String> suggestions = new ArrayList<>();
                    for (String item : DATA) {
                        if (item.toLowerCase().indexOf(input)!=-1) {
                            suggestions.add(item);
                        }
                    }

                    if (!suggestions.isEmpty()) {
                        for (String suggestion : suggestions) {
                            JMenuItem item = new JMenuItem(suggestion);
                            item.addActionListener(e -> {
                                tBuscar.setText(suggestion);
                                popupMenu.setVisible(false);
                            });
                            popupMenu.add(item);
                        }

                        popupMenu.show(tBuscar, 0, tBuscar.getHeight());
                        popupMenu.updateUI();
                        popupMenu.revalidate();
                        popupMenu.repaint();
                    } else {
                        popupMenu.setVisible(false);
                    }
                }
            }
        });

        // Cerrar el popup si se hace clic fuera
        tBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //updatePopup();
            }
        });

        bEliminar = new Button(eliminarIcon);
        bEliminar.setToolTipText("");
        bEliminar.setEnabled(false);

        bClose = new Button(closeIcon);
        bClose.setToolTipText(CONSTANTS.LANG.getValue("producto.label.buscar_producto"));
        bClose.setActionCommand("BUTTON_CERRAR");
        bClose.addActionListener(productoListener);

        bBuscar = new Button(buscarIcon);
        bBuscar.setToolTipText(CONSTANTS.LANG.getValue("producto.label.buscar_producto"));

        bCambiarPrecio = new Button(cambiarPrecioIcon);
        bCambiarPrecio.setToolTipText("");

        bImportar = new Button(importarIcon);
        bImportar.setToolTipText("");

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(tBuscar);

        add(tBuscar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        add(bBuscar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 1.0f, 0.0f));
        /*add(new JSeparator(), LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 0, 5, 0, 0, 0.0f, 1.0f));
        add(bCambiarPrecio, LayoutPanel.constantePane(3, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        add(bImportar, LayoutPanel.constantePane(4, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        add(new JSeparator(), LayoutPanel.constantePane(5, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.CENTER, 0, 5, 0, 0, 0.0f, 1.0f));
        add(bEliminar, LayoutPanel.constantePane(6, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        add(bClose, LayoutPanel.constantePane(7, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 0, 0, 0, 0, 1.0f, 0.0f));
    */}

    @Override
    public void updateUI(){
        Color colButton = Color.ORANGE;
        Color bcol = new Color(190, 190, 190);
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = PropiedadesSistema.getColor("Button.color");// UIManager.getColor("TextField.foreground").brighter();;
            bcol = new Color(90, 90, 90);
        }

        iProd = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"tproduct.png"),bcol, true));
        iCod = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"barcode.png"),bcol, true));
        lupaText = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"buscar.png"),bcol, true));

        if(bCodigo != null)
            bCodigo.setIcon(iCod);

        if (bProducto != null)
            bProducto.setIcon(iProd);

        if(this.tBuscar != null)
            this.tBuscar.putClientProperty("JTextField.leadingIcon", lupaText);

        closeIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"close.png"), colButton, true));
        if(bClose != null)
                bClose.setIcon(closeIcon);

        eliminarIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"delete2.png"), colButton, true));
        if(bEliminar != null)
            bEliminar.setIcon(eliminarIcon);

        buscarIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"buscar.png"), colButton, true));

        if(bBuscar != null)
            bBuscar.setIcon(buscarIcon);

        cambiarPrecioIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"change.png"), colButton, true));

        if(bCambiarPrecio != null)
            bCambiarPrecio.setIcon(cambiarPrecioIcon);

        importarIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(carp+"import.png"), colButton, true));

        if(bImportar != null)
            bImportar.setIcon(importarIcon);

        super.updateUI();
    }
}
