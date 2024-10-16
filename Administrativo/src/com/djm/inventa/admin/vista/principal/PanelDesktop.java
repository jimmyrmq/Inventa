package com.djm.inventa.admin.vista.principal;

import com.djm.util.LayoutPanel;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

public class PanelDesktop extends JPanel {
    private JDesktopPane desktop;
    //private JPanel desktop;
    private List<IPanelDesktop> listIDPane;
    public PanelDesktop(){
        listIDPane = new ArrayList<>();

        setOpaque(false);
        setLayout(new GridBagLayout());

        pEscritorio();

        add(Global.panelTitulo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
        add(desktop, LayoutPanel.constantePane(0, 3, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
    }

    public void addVentana(IPanelDesktop panelDesktop){
        boolean add = true;

        JInternalFrame destopPanel = null;

        cont:for(IPanelDesktop id: listIDPane){
            if(id.getID().equalsIgnoreCase(panelDesktop.getID())){
                destopPanel = id.getDesktopPane();
                add = false;
                break cont;
            }
        }

        if(add){
            /*if(panelDesktop.getToolBar() != null) {
                add(panelDesktop.getToolBar(), LayoutPanel.constantePane(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
                add(new JSeparator(), LayoutPanel.constantePane(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 0.0f));
            }*/
            destopPanel = panelDesktop.getDesktopPane();
            desktop.add(destopPanel);//panelDesktop.getPanel());//
            listIDPane.add(panelDesktop);
            Global.panelTitulo.setTitulo(panelDesktop.getTitulo());
            Global.panelTitulo.setVisible(true);
        }

        destopPanel.moveToFront();

        try {
            destopPanel.setSelected(true);
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
                desktop.removeAll();
                Global.panelTitulo.setVisible(false);

                remove(panelDesktop.getToolBar());
                break cont;
            }
        }

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

}
