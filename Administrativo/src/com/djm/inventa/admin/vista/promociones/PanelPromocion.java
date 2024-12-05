package com.djm.inventa.admin.vista.promociones;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.ipanel.IPanelAction;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.Table;
import com.djm.inventa.admin.vista.component.renderer.StatusIconProductRenderer;
import com.djm.inventa.admin.vista.component.renderer.TipoEtiqueta;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.ui.component.EtiquetaComponent;
import com.djm.ui.component.table.ModeloTabla;
import com.djm.util.LayoutPanel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPromocion extends IPanelAction implements ActionListener{
    private JPanel panelPrincipal;
    private ModelTablePromocionCustom mpc;
    private Table<Producto> tabla;
    private JButton bCrear, bCerrar;
    public PanelPromocion(){
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5)); // top, left, bottom, right

        bCerrar = new JButton(CONSTANTS.LANG.getValue("button.cerrar"));
        bCerrar.setActionCommand("CERRAR");
        bCerrar.addActionListener(this);

        bCrear = new JButton(CONSTANTS.LANG.getValue("promocion.button.crear"));
        bCrear.setActionCommand("CREAR");
        bCrear.addActionListener(this);

        cerrarEsc(bCerrar);

        pTable();

        panelPrincipal.add(bCrear, LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panelPrincipal.add(tabla.getPanel(), LayoutPanel.constantePane(0, 1, 3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 10, 0, 0, 0, 0.0f, 0.0f));
        panelPrincipal.add(new JButton(CONSTANTS.LANG.getValue("button.eliminar")), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 0.0f, 0.0f));
        panelPrincipal.add(new JButton(CONSTANTS.LANG.getValue("button.detener")), LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 5, 0, 0, 1.0f, 0.0f));
        panelPrincipal.add(bCerrar, LayoutPanel.constantePane(2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 10, 0, 0, 5, 0.0f, 0.0f));

    }

    private void pTable(){

        mpc = new ModelTablePromocionCustom();

        ModeloTabla<Producto> modelo = new ModeloTabla(mpc);

        tabla = new Table(modelo, 230);
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

    @Override
    public JPanel getPanel() {
        return panelPrincipal;
    }


    @Override
    public void clearForm() {

    }

    @Override
    public void actionEsc() {
        Global.panelDesktop.cerrarVentana(PropiedadesSistema.getPropiedad("Promocion.ID"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if("CERRAR".equals(action)){
            Global.panelDesktop.cerrarVentana(PropiedadesSistema.getPropiedad("Promocion.ID"));
        }
        else if("CREAR".equals(action)){
            CrearPromocion crearPromocion = new CrearPromocion();
            if (crearPromocion.isAcept()){
                System.out.println(crearPromocion.getData());
            }
        }
    }
}
