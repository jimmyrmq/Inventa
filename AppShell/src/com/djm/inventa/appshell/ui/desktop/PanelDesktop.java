package com.djm.inventa.appshell.ui.desktop;


import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class PanelDesktop extends JPanel {
    private JDesktopPane desktop;
    //private PanelListaProducto pListaProducto;

    public PanelDesktop(){
        desktop=new JDesktopPane();

        setOpaque(false);
        setLayout(new BorderLayout());

        add(desktop, BorderLayout.CENTER);//LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public JDesktopPane getDesktop(){
        return desktop;
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
