package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.util.ObtenerDatosPC;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelInfo extends JPanel {
    private ImageIcon iPC;
    private JLabel lNombrePc;

    public PanelInfo(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        lNombrePc = new JLabel();
        lNombrePc.setToolTipText(CONSTANTS.LANG.getValue("sistema.tooltext.nombrepc"));

        add(lNombrePc, LayoutPanel.constantePane(1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 3, 5, 3, 0, 1.0f, 0.0f));
        Thread t = new Thread(()->{
            lNombrePc.setText(ObtenerDatosPC.getNombrePC());
            lNombrePc.setIcon(iPC);});
        t.start();
    }

    @Override
    public void updateUI(){
        super.updateUI();
        Color colButton = Color.ORANGE;
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = PropiedadesSistema.getColor("Button.color");// UIManager.getColor("TextField.foreground").brighter();;
        }

        iPC = new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/computer.png"), colButton, true));

        if(lNombrePc != null){
            lNombrePc.setIcon(iPC);
        }
    }
}
