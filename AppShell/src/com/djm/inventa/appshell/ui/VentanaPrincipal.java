package com.djm.inventa.appshell.ui;

import com.djm.inventa.appshell.Global;
import com.djm.inventa.appshell.ui.desktop.PanelDesktop;
import com.djm.inventa.appshell.ui.menu.Salir;
import com.djm.inventa.ui.IconManager;
import com.djm.ui.LayoutPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class VentanaPrincipal extends JFrame  implements WindowListener {
    private JMenuBar menuBar;

    public VentanaPrincipal(JMenuBar menuBar) {
        this.menuBar = menuBar;

        init();

        Container content= getContentPane();
        content.setLayout(new GridBagLayout());

        content.add(Global.panelDesktop, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));

    }

    public void mostrar(){
        setVisible(true);
    }

    private void init(){

        Dimension dimension =new Dimension(1490,830);

        setTitle("Inventa");
        setResizable(true);
        setIconImage (IconManager.get32("ico").getImage());
        //frame.setSize(dim);
        setMinimumSize(dimension);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//Deshabilitar el botton  de cerrar
        setDefaultLookAndFeelDecorated(true);
        //frame.setBackground(new Color(158,162,144));
        setLocationRelativeTo(null);
        addWindowListener(this);
        setUndecorated(false);
        setExtendedState(6);//Expandir la ventana

        setJMenuBar(this.menuBar);

        Global.panelDesktop = new PanelDesktop();
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
