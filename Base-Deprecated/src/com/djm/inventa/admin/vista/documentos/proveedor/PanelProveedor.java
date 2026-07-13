package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.modelo.Proveedor;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.util.LayoutPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelProveedor extends IPanelDataAction<Proveedor> {
    private JPanel panelPrincipal;
    private JTextField tCodigo, tNombre, tContacto, tDireccion, tTelefono1, tTelefono2, tCorreo, tFecha;
    private JTextArea tNota;
    private JButton bEliminar,bGuardar, bCancelar;
    private PanelTableProveedor panelTableProveedor;

    public PanelProveedor() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setOpaque(false);

        panelTableProveedor = new PanelTableProveedor();

        // Registrar listener para que al hacer doble click en la tabla
        // se carguen los datos en este formulario
        panelTableProveedor.addProveedorSelectionListener(new ProveedorSelectionListener() {
            @Override
            public void proveedorSelected(Proveedor p) {
                if (p != null) {
                    insertData(p);
                }
            }
        });

        panelPrincipal.add(pDatos(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 5, 5, 0, 0.0f, 0.0f));
        panelPrincipal.add(panelTableProveedor, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 0, 15, 5, 5, 1.0f, 1.0f));

        actionListener();
    }

    private JPanel pDatos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel lCodigo = new JLabel(CONSTANTS.LANG.getValueLabel("label.codigo","Codigo"));
        JLabel lNombre = new JLabel(CONSTANTS.LANG.getValueLabel("proveedor.label.razon_social","Razón Social"));
        JLabel lContacto = new JLabel(CONSTANTS.LANG.getValueLabel("proveedor.label.contacto", "Nombre de Contacto" ));
        JLabel lDireccion = new JLabel(CONSTANTS.LANG.getValueLabel("label.direccion", "Dirección"));
        JLabel lTelefono1 = new JLabel(CONSTANTS.LANG.getValueLabel("proveedor.label.telefono1","Teléfono 1"));
        JLabel lTelefono2 = new JLabel(CONSTANTS.LANG.getValueLabel("proveedor.label.telefono2", "Teléfono 2") );
        JLabel lCorreo = new JLabel(CONSTANTS.LANG.getValueLabel("label.correo","Correo" ));
        JLabel lFecha = new JLabel(CONSTANTS.LANG.getValueLabel("label.fecha", "Fecha"));
        JLabel lNota = new JLabel(CONSTANTS.LANG.getValueLabel("label.nota", "Nota"));

        tCodigo = new JTextField(18);
        tNombre = new JTextField(25);
        tContacto = new JTextField(25);
        tDireccion = new JTextField(25);
        tTelefono1 = new JTextField(15);
        tTelefono2 = new JTextField(15);
        tCorreo = new JTextField(25);
        tFecha = new JTextField(12);
        tNota = new JTextArea(2, 25);

        JScrollPane spNota = new JScrollPane(tNota);

        // Row 0
        panel.add(lCodigo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        // Row 1
        panel.add(lNombre, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        // Row 2
        panel.add(lContacto, LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tContacto, LayoutPanel.constantePane(1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        // Row 3
        panel.add(lDireccion, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tDireccion, LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        // Row 4 (telefono y telefono2)
        panel.add(lTelefono1, LayoutPanel.constantePane(0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tTelefono1, LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lTelefono2, LayoutPanel.constantePane(0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tTelefono2, LayoutPanel.constantePane(1, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        // Row 6 correo y fecha
        panel.add(lCorreo, LayoutPanel.constantePane(0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCorreo, LayoutPanel.constantePane(1, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(lFecha, LayoutPanel.constantePane(0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tFecha, LayoutPanel.constantePane(1, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        // Row 8 nota
        panel.add(lNota, LayoutPanel.constantePane(0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(spNota, LayoutPanel.constantePane(1, 8, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START, 5, 5, 5, 0, 1.0f, 0.0f));

        panel.add(getPanelButton(), LayoutPanel.constantePane(0, 9, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_END, 10, 0, 0, 0, 1.0f, 0.0f));

        return panel;
    }

    private JPanel getPanelButton() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        //iok = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/ok16.png") ,color3,false));
        //icancel = new ImageIcon(ColorFilter.filterImage( Image.getIcon("16/closed.png") ,color3,false));

        bEliminar = new JButton(CONSTANTS.LANG.getValue("button.eliminar"));//, iok);//,"F5",null);//,new ImageIcon("com.djm.inventa.icon/ok.png"));
        bGuardar = new JButton(CONSTANTS.LANG.getValue("button.guardar"));//, iok);//,"F5",null);//,new ImageIcon("com.djm.inventa.icon/ok.png"));
        bCancelar = new JButton(CONSTANTS.LANG.getValue("button.cancelar"));//, icancel);//,new ImageIcon("com.djm.inventa.icon/close.png"));
        //bGuardar.setForeground(new Color(66, 89, 147));

        bEliminar.setActionCommand("BUTTON_ELIMINAR");
        bCancelar.setActionCommand("BUTTON_CANCELAR");
        bGuardar.setActionCommand("GUARDAR_PROVEEDOR");

        cerrarEsc(bCancelar);

        bGuardar.setFocusable(true);

        panel.add(bEliminar, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 1.0f, 0.0f));
        panel.add(bCancelar, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 0, 0, 0, 0.0f, 0.0f));
        panel.add(bGuardar, LayoutPanel.constantePane(2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 10, 0, 0, .0f, 0.0f));

        return panel;
    }

    @Override
    public Proveedor getDataForm() {
        Proveedor p = new Proveedor();

        if(isData()) {
            p = getValue();
        }

        // ID left null for new entries
        p.setCodigo(tCodigo.getText());
        p.setNombre(tNombre.getText());
        p.setNombreContacto(tContacto.getText());
        p.setDireccion(tDireccion.getText());
        p.setTelefono1(tTelefono1.getText());
        p.setTelefono2(tTelefono2.getText());
        p.setCorreo(tCorreo.getText());
        p.setFechaRegistro(tFecha.getText());
        p.setNota(tNota.getText());

        return p;
    }

    @Override
    public void actionEsc() {

        ActionEvent eventoSimulado = new ActionEvent(bCancelar, ActionEvent.ACTION_PERFORMED, "BUTTON_CANCELAR");

        if( bCancelar.getActionListeners().length == 1)
            bCancelar.getActionListeners()[0].actionPerformed(eventoSimulado);
    }

    @Override
    public String getId() {
        return "proveedor_id";
    }

    @Override
    public String getTitle() {
        return "Proveedor";
    }

    @Override
    public JPanel getPanel() {
        return panelPrincipal;
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }

    @Override
    public void clearForm() {
        tCodigo.setText(null);
        tNombre.setText(null);
        tContacto.setText(null);
        tDireccion.setText(null);
        tTelefono1.setText(null);
        tTelefono2.setText(null);
        tCorreo.setText(null);
        tFecha.setText(null);
        tNota.setText(null);

        tCodigo.requestFocus();

        super.insertData(null);
    }

    /**
     * Carga los datos de un Proveedor en el formulario.
     */
    @Override
    public void insertData(Proveedor p){
        if(p == null) return;

        tCodigo.setText(p.getCodigo());
        tNombre.setText(p.getNombre());
        tContacto.setText(p.getNombreContacto());
        tDireccion.setText(p.getDireccion());
        tTelefono1.setText(p.getTelefono1());
        tTelefono2.setText(p.getTelefono2());
        tCorreo.setText(p.getCorreo());
        tFecha.setText(p.getFechaRegistro());
        tNota.setText(p.getNota());

        super.insertData(p);
    }

    /**
     * Actualiza o agrega un proveedor en la tabla.
     * Delega a PanelTableProveedor.updateOrAddProveedor()
     * @param p el Proveedor a actualizar o agregar
     */
    public void updateOrAddProveedorInTable(Proveedor p) {
        if (panelTableProveedor != null) {
            panelTableProveedor.updateOrAddProveedor(p);
        }
    }

    private void actionListener() {
        ActionListener al = new ProveedorListener(this);
        bGuardar.addActionListener(al);
        bCancelar.addActionListener(al);
    }


}