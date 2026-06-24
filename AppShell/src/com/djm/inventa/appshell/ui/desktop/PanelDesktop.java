package com.djm.inventa.appshell.ui.desktop;

import com.djm.inventa.ui.ipanel.IPanelDesktop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PanelDesktop extends JPanel {
    private JDesktopPane desktop;
    //private JPanel desktop;
    private List<IPanelDesktop> listIDPane;
    //private PanelListaProducto pListaProducto;

    public PanelDesktop(){
        listIDPane = new ArrayList<>();
        desktop=new JDesktopPane();

        setOpaque(false);
        setLayout(new BorderLayout());

        add(desktop, BorderLayout.CENTER);//LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public void addVentana(IPanelDesktop panelDesktop, Object data){
        if(panelDesktop == null){
            return;
        }

        boolean add = true;

        //Buscamos si la ventana ya esta abierta
        IPanelDesktop iPanelDesktop = getIPanelDesktop(panelDesktop.getID());

        if(iPanelDesktop != null){
            add = false;
            panelDesktop = iPanelDesktop;
        }

        JInternalFrame desktopPanel = panelDesktop.getDesktopPane();
        if(add){
            addVentana(desktopPanel, iPanelDesktop);
        }
        if(data != null){
            panelDesktop.insertData(data);
        }
    }

    public void addVentana(JInternalFrame desktopPanel, IPanelDesktop iPanelDesktop){

        //Mostrar en el centro del DesktopPane
        int x = (desktop.getWidth() - desktopPanel.getWidth()) / 2;
        int y = ((desktop.getHeight() - desktopPanel.getHeight()) / 2)-50;

        desktopPanel.setLocation(x, y);

        desktop.add(desktopPanel);

        if(iPanelDesktop != null) {
            listIDPane.add(iPanelDesktop);
        }

        desktopPanel.moveToFront();

        try {
            desktopPanel.setSelected(true);
        } catch (java.beans.PropertyVetoException exc){
            System.err.println(exc);
        }

    }

    public void cerrarVentana(String idPanelDesktop){

        cont:for(IPanelDesktop panelDesktop: listIDPane) {
            if (panelDesktop.getID().equalsIgnoreCase(idPanelDesktop)) {
                panelDesktop.getDesktopPane().setVisible(false);
                panelDesktop.getDesktopPane().dispose();
                listIDPane.remove(panelDesktop);

                break cont;
            }
        }
    }

    public JDesktopPane getDesktop(){
        return desktop;
    }

    public IPanelDesktop getIPanelDesktop(String id){
        //Buscamos si la ventana ya esta abierta a traves del ID
        for(IPanelDesktop iPanelDesktop: listIDPane){
            if(iPanelDesktop.getID().equalsIgnoreCase(id)){
                 return iPanelDesktop;
            }
        }

        return null;
    }

    /*
    public void mostrarPanelListaProducto(boolean ver){
        if(ver) {
            pListaProducto = new PanelListaProducto();
            add(pListaProducto, BorderLayout.EAST);
        }
        else if(pListaProducto != null) {

            remove(pListaProducto);
            pListaProducto = null;
        }

        revalidate(); // Actualizar el contenedor
        repaint();    // Redibujar el contenedor
    }

    public void setProductoList(Producto producto){
        //Si el panel dela lista producto esta abierta
        if(pListaProducto != null)
            pListaProducto.setProductoList(producto);

        //Si el StockEsta Abierto
        IPanelDesktop panelDesktop = getIPanelDesktop(AppContext.getInstance().getString("Stock.ID"));

        if(panelDesktop!= null)
            panelDesktop.insertData(producto);


    }

    public void delProductoList(Producto producto){
        if(pListaProducto != null)
            pListaProducto.delProductoList(producto);
    }*/
}
