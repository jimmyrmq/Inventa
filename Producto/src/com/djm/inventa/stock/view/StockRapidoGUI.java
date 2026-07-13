package com.djm.inventa.stock.view;

import com.djm.inventa.producto.core.CONSTANTS;
import com.djm.inventa.ui.component.TextField;
import com.djm.ui.GlobalFrame;
import com.djm.ui.component.OptionPane;
import com.djm.ui.LayoutPanel;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;

public class StockRapidoGUI implements ActionListener {
    private JDialog dialog;
    private boolean acept = false;
    private JButton bAceptar, bCancelar;
    private TextField tNuevaCantidad;
    private TextField tCantidadActual;
    private BigDecimal cantidadEntrante;

    public StockRapidoGUI(){
        this(null);
    }

    public StockRapidoGUI(BigDecimal cantidadEntrante){
        if(cantidadEntrante.compareTo(BigDecimal.ZERO) > 0)
            this.cantidadEntrante = cantidadEntrante;

        createGUI();

        Container content = dialog.getContentPane();
        content.setLayout(new GridBagLayout());

        content.add(createGUIDescrip(), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 10, 10, 5, 10, 1.0f, 1.0f));
        content.add(panelButton(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 10, 0, 5, 10, 1.0f, 0.0f));


        dialog.setVisible(true);
    }

    private JPanel panelButton(){

        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(false);

        bAceptar = new JButton(CONSTANTS.i18n.getValue("button.aceptar"));
        bCancelar = new JButton(CONSTANTS.i18n.getValue("button.cancelar"));

        bAceptar.setActionCommand("ACEPT");
        bCancelar.setActionCommand("CANCEL");

        cerrarEsc();

        bAceptar.addActionListener(this);
        bCancelar.addActionListener(this);

        panel.add(bCancelar);
        panel.add(bAceptar);

        return panel;
    }
    public JPanel createGUIDescrip() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        tCantidadActual = new TextField(7,10,true);
        tNuevaCantidad = new TextField(7,10,true);

        tCantidadActual.setEnabled(false);

        tNuevaCantidad.addActionListener(this);
        tNuevaCantidad.setActionCommand("ACEPT");

        if(cantidadEntrante != null && cantidadEntrante.compareTo(BigDecimal.ZERO) > 0) {
            tNuevaCantidad.setText(String.valueOf(cantidadEntrante));
            tNuevaCantidad.requestFocus();
            tNuevaCantidad.selectAll();
        }

        JLabel lCantidadActual = new JLabel(CONSTANTS.i18n.getValue("movimientoStock.label.cantidadactual"));
        JLabel lCantidadNueva = new JLabel(CONSTANTS.i18n.getValue("movimientoStock.label.cantidadnueva"));

        panel.add(lCantidadActual, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidadActual, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 8, 0, 0, 1.0f, 0.0f));
        panel.add(lCantidadNueva, LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tNuevaCantidad, LayoutPanel.constantePane(1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 8, 0, 0, 1.0f, 0.0f));

        return panel;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if(action.equals("ACEPT")){

            String cantidadText = tNuevaCantidad.getText();
            if(cantidadText != null && !cantidadText.trim().isEmpty() && Integer.parseInt(cantidadText) >= 0) {
                cantidadEntrante = new BigDecimal(cantidadText);

                acept = true;
                dialog.setVisible(false);
                dialog.dispose();

            }
            else{
                OptionPane.error(CONSTANTS.i18n.getValue("stock.error.cantidad"));
            }
        }
        else if(action.equals("CANCEL")) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    public boolean isAcept() {
        return acept;
    }

    public BigDecimal getCantidadEntrante(){
        return cantidadEntrante;
    }

    private void createGUI(){
        dialog = new JDialog(GlobalFrame.getInstance().getFrame(), null , true);

        dialog.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

        dialog.pack();

        Dimension dim = new Dimension(300,130);

        dialog.setSize(dim);
        dialog.setPreferredSize(dim);

        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        String titulo = this.cantidadEntrante!= null && this.cantidadEntrante.compareTo(BigDecimal.ZERO) > 0 ?CONSTANTS.i18n.getValue("stock.label.titulorapido_edit"):CONSTANTS.i18n.getValue("stock.label.titulorapido");
        dialog.setTitle(titulo);
    }


    private void cerrarEsc(){
        KeyStroke SR= KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false);
        Action action =new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
                acept = false;
            }
        };
        InputMap inputMap = bCancelar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(SR, "CERRAR");
        ActionMap actionMap = bCancelar.getActionMap();
        actionMap.put("CERRAR", action);
    }
}
