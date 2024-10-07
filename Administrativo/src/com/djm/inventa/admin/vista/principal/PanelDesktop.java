package com.djm.inventa.admin.vista.principal;

import com.djm.util.LayoutPanel;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

public class PanelDesktop extends JPanel {
    private JDesktopPane desktop;
    private List<IPanelDesktop> listIDPane;
    public PanelDesktop(){
        listIDPane = new ArrayList<>();

        setOpaque(false);
        setLayout(new GridBagLayout());

        pEscritorio();

        add(desktop, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0, 1.0f, 1.0f));
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

        if(add) {
            destopPanel = panelDesktop.getDesktopPane();
            listIDPane.add(panelDesktop);
            desktop.add(destopPanel);
        }

        destopPanel.moveToFront();

        try {
            destopPanel.setSelected(true);
        } catch (java.beans.PropertyVetoException exc){
            System.err.println(exc);
        }
    }

    public void delVentana(String idPanelDesktop){
        cont:for(IPanelDesktop id: listIDPane){
            if(id.getID().equalsIgnoreCase(idPanelDesktop)){
                listIDPane.remove(id);
                break cont;
            }
        }
    }
    private void pEscritorio(){
        //Color colFondo= UIManager.getColor("");//new Color(254,253,255);//new Color(233,240,246);//
        desktop=new JDesktopPane();/*{
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
