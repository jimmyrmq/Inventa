package com.djm.inventa.admin.vista.promociones;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.modelo.Promocion;
import com.djm.inventa.admin.util.CheckDataException;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.ipanel.IDialog;
import com.djm.util.Fecha;
import com.djm.util.LayoutPanel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearPromocion extends IDialog<Promocion> implements ActionListener {
    private JTextField tCodigo, tNombre, tPrecio;
    private String f1;
    private JFormattedTextField tFechaIni, tFechaFin;
    private JCheckBox existencia;
    private String tipoPromocion;

    public CrearPromocion(String tipoPromocion){
        super("PAQUETE".equalsIgnoreCase(tipoPromocion)?CONSTANTS.LANG.getValue("promocion.label.titulo.crearpaq"):CONSTANTS.LANG.getValue("promocion.label.titulo.creardesc"));

        this.tipoPromocion = tipoPromocion;

        f1= Fecha.getFechaActual();

        addContentPane(createPane(),true);
    }

    public JPanel createPane() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JButton bCerrar = new JButton(CONSTANTS.LANG.getValue("button.cerrar"));
        bCerrar.addActionListener(this);
        bCerrar.setActionCommand("CERRAR");
        cerrarEsc(bCerrar);

        JButton bGuardar = new JButton(CONSTANTS.LANG.getValue("button.guardar"));
        bGuardar.addActionListener(this);
        bGuardar.setActionCommand("GUARDAR");

        panel.add(getPanelData(), LayoutPanel.constantePane(0, 0, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 15, 5, 0, 0, 0.0f, 0.0f));

        if("PAQUETE".equalsIgnoreCase(this.tipoPromocion))
            panel.add(new PanelPaquetes(), LayoutPanel.constantePane(0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(bGuardar, LayoutPanel.constantePane(1, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 1.0f, 0.0f));
        panel.add(bCerrar, LayoutPanel.constantePane(2, 2, 3, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private JPanel getPanelData() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        tCodigo = new JTextField(20);
        tNombre = new JTextField(20);
        tPrecio = new JTextField(10);

        existencia = new JCheckBox(CONSTANTS.LANG.getValue("promocion.label.existencia"));
        existencia.addActionListener(e->fechaActive());

        MaskFormatter fmt1 = null, fmt2 = null;

        try{
            fmt1 = new MaskFormatter("##/##/####");
            fmt1.setValidCharacters("01234596789");

            fmt2 = new MaskFormatter("##/##/####");
            fmt2.setValidCharacters("01234596789");
        }catch(Exception e){}

        tFechaIni= new JFormattedTextField(fmt1);
        tFechaIni.setText(f1);

        tFechaFin= new JFormattedTextField(fmt2);
        tFechaFin.setText(f1);

        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.codigo")), LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));
        panel.add(tCodigo, LayoutPanel.constantePane(1, 0, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 0, 5, 0, 0, 0.0f, 0.0f));

        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.nombre")), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(tNombre, LayoutPanel.constantePane(1, 1, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(new JLabel(CONSTANTS.LANG.getValue("producto.label.precio1")), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(tPrecio, LayoutPanel.constantePane(1, 2, 4, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        panel.add(new JLabel(CONSTANTS.LANG.getValue("promocion.label.vigencia")), LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.desde")), LayoutPanel.constantePane(1, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(tFechaIni, LayoutPanel.constantePane(2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(new JLabel(CONSTANTS.LANG.getValue("label.hasta")), LayoutPanel.constantePane(1, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(tFechaFin, LayoutPanel.constantePane(2, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));
        panel.add(existencia, LayoutPanel.constantePane(3, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 5, 5, 0, 0, 0.0f, 0.0f));

        return panel;
    }

    private void fechaActive(){
        boolean selected = !existencia.isSelected();
        tFechaFin.setEnabled(selected);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("CERRAR".equals(command)){
            close();
        }
        if("GUARDAR".equals(command)){
            Promocion promocion = new Promocion();
            promocion.setCodigo(tCodigo.getText());

            try {
                checkData(promocion);

                trueAcept();

                insertData(promocion);

                close();
            } catch (CheckDataException ex) {
                JOptionPane.showMessageDialog(GlobalFrame.getInstance().getFrame(),ex.getMessage(),
                        CONSTANTS.LANG.getValue("promocion.label.titulo"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkData(Promocion promocion) throws CheckDataException {
        if(promocion == null){}
        else if(promocion.getCodigo() == null || promocion.getCodigo().trim().isEmpty()){
            tCodigo.requestFocus();
            throw new CheckDataException(CONSTANTS.LANG.getValue("mensaje.codigo.vacio","promocion"));

        }
    }

    @Override
    public void actionEsc() {
        close();
    }
}
