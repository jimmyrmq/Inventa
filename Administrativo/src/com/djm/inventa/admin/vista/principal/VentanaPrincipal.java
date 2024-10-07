package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class VentanaPrincipal extends JFrame  implements WindowListener {

    public VentanaPrincipal(){
        init();
    }
    public void crearGUI(){
        Container content= getContentPane();
        content.setLayout(new GridBagLayout());

        content.add(Global.panelDesktop, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
        content.add(new JSeparator(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        content.add(new PanelInfo(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));

    }
    private void init(){

        Dimension dimension =new Dimension(1410,830);

        setTitle(CONSTANTS.TITULO);
        setResizable(true);
        setIconImage (Image.getIcon("ico.png").getImage());
        //frame.setSize(dim);
        setMinimumSize(dimension);
        setDefaultCloseOperation(0);//Deshabilitar el botton  de cerrar
        setDefaultLookAndFeelDecorated(true);
        //frame.setBackground(new Color(158,162,144));
        setLocationRelativeTo(null);
        addWindowListener(this);
        setUndecorated(false);
        setExtendedState(6);//Expandir la ventana

        setJMenuBar(new Menu());

    }
    public void cerrar() {
        setVisible(false);
        dispose();
        System.gc();
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Salir.getInstance().exitSystem();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
