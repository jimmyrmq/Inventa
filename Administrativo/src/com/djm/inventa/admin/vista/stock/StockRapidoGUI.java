package com.djm.inventa.admin.vista.stock;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.TextField;
import com.djm.ui.component.OptionPane;
import com.djm.util.LayoutPanel;

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

public class StockRapidoGUI implements ActionListener {
    private JDialog dialog;
    private boolean acept = false;
    private JButton bAceptar, bCancelar;
    private TextField tCantidad;
    private Integer cantidadEntrante;

    public StockRapidoGUI(){
        this(null);
    }
    public StockRapidoGUI(Integer cantidadEntrante){
        if(cantidadEntrante > 0)
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

        bAceptar = new JButton(CONSTANTS.LANG.getValue("button.aceptar"));
        bCancelar = new JButton(CONSTANTS.LANG.getValue("button.cancelar"));

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

        tCantidad = new TextField(7,10,true);

        tCantidad.addActionListener(this);
        tCantidad.setActionCommand("ACEPT");

        if(cantidadEntrante != null && cantidadEntrante > 0) {
            tCantidad.setText(String.valueOf(cantidadEntrante));
            tCantidad.requestFocus();
            tCantidad.selectAll();
        }

        JLabel lCantidad = new JLabel(CONSTANTS.LANG.getValue("label.cantidad"));

        panel.add(lCantidad, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 0, 0, 0, 0.0f, 0.0f));
        panel.add(tCantidad, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 8, 0, 0, 1.0f, 0.0f));

        return panel;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if(action.equals("ACEPT")){

            String cantidadText = tCantidad.getText();
            if(cantidadText != null && !cantidadText.trim().isEmpty() && Integer.parseInt(cantidadText) >= 0) {
                cantidadEntrante = Integer.parseInt(cantidadText);

                acept = true;
                dialog.setVisible(false);
                dialog.dispose();

            }
            else{
                OptionPane.error(CONSTANTS.LANG.getValue("stock.error.cantidad"));
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

    public Integer getCantidadEntrante(){
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
        String titulo = this.cantidadEntrante!= null && this.cantidadEntrante > 0 ?CONSTANTS.LANG.getValue("stock.label.titulorapido_edit"):CONSTANTS.LANG.getValue("stock.label.titulorapido");
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
