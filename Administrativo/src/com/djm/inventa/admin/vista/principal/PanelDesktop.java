package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.modelo.Producto;
import com.djm.inventa.admin.vista.producto.PanelListaProducto;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

public class PanelDesktop extends JPanel {
    private JDesktopPane desktop;
    //private JPanel desktop;
    private List<IPanelDesktop> listIDPane;
    private PanelListaProducto pListaProducto;
    public PanelDesktop(){
        listIDPane = new ArrayList<>();

        setOpaque(false);
        setLayout(new BorderLayout());

        pEscritorio();

        //add(Global.panelTitulo, BorderLayout.NORTH);//LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        add(desktop, BorderLayout.CENTER);//LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public void addVentana(IPanelDesktop panelDesktop, Object data){
        boolean add = true;

        //Buscamos si la ventana ya esta abierta
        IPanelDesktop iPanelDesktop = getIPanelDesktop(panelDesktop.getID());

        if(iPanelDesktop != null){
            add = false;
            panelDesktop = iPanelDesktop;
        }

        JInternalFrame desktopPanel = panelDesktop.getDesktopPane();

        if(add){
            //Mostrar en el centro del DesktopPane
            int x = (desktop.getWidth() - desktopPanel.getWidth()) / 2;
            int y = ((desktop.getHeight() - desktopPanel.getHeight()) / 2)-50;

            desktopPanel.setLocation(x, y);

            desktop.add(desktopPanel);
            listIDPane.add(panelDesktop);
        }

        desktopPanel.moveToFront();

        if(data != null){
            panelDesktop.insertData(data);
        }

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

    private void pEscritorio(){
        /*desktop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        desktop.setOpaque(false);*/

        desktop=new JDesktopPane();
        /*{
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g;
                g2.setColor(colFondo);
                g2.fillRect(0,0,getWidth(),getHeight());

                /*if (image == null) {
                    try {
                        image = ImageIO.read(getClass().getResource("Imagen/logo_emp.png"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                g2.drawImage(image,frame.getWidth()-(image.getWidth()+60),frame.getHeight()-(image.getHeight()+150),null);//frame.getWidth()-(image.getWidth()),frame.getHeight()-(image.getHeight()), null);
*
            }
        };*/

        //desktopManager =desktop.getDesktopManager();

        //new runImageDescktop();

        //desktop.setBackground(colFondo);
    }

    public JDesktopPane getDesktop(){
        return desktop;
    }

    public IPanelDesktop getIPanelDesktop(String id){
        IPanelDesktop panelDesktop = null;
        //Buscamos si la ventana ya esta abierta
        cont:for(IPanelDesktop iPanelDesktop: listIDPane){
            if(iPanelDesktop.getID().equalsIgnoreCase(id)){
                panelDesktop = iPanelDesktop;
                break cont;
            }
        }

        return panelDesktop;
    }

    public void setProductoList(Producto producto){
        if(pListaProducto != null)
            pListaProducto.setProductoList(producto);
    }

    public void delProductoList(Producto producto){
        if(pListaProducto != null)
            pListaProducto.delProductoList(producto);
    }

    /*public IPanel getIPanel(String id){
        IPanelDesktop panelDesktop = getIPanelDesktop(id);
        IPanel iPanel = null;

        if(panelDesktop != null){
            iPanel = panelDesktop.getIPanel();
        }

        return iPanel;
    }*/
}
