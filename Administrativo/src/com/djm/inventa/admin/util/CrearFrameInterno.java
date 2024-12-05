package com.djm.inventa.admin.util;

import com.djm.inventa.admin.vista.principal.Global;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CrearFrameInterno extends JInternalFrame implements InternalFrameListener{
    private String icon;
    private boolean filterIcon;
    private String ID;
    public CrearFrameInterno(JPanel panel,String titulo, String icon, boolean filterIcon, String id) {
        super(titulo, false, true, false, false);
        this.ID = id;

        this.filterIcon = filterIcon;
        this.icon = icon;
        setIcon();

        getContentPane().add(panel);

        pack();

        setDimension(10,10);

        setResizable(false);
        /*setClosable(false);
        setMaximizable(false);
        setIconifiable(false);*/

        /*int width = GlobalFrame.getInstance().getFrame().getWidth();
        int height = GlobalFrame.getInstance().getFrame().getHeight();
        Dimension dim = getSize();
        setLocation((width - dim.width) / 2, ((height - dim.height) / 2) - 70);*/

        limite();

        setDefaultCloseOperation(0);//Deshabilitar el botton  de cerrar
        addInternalFrameListener(this);
        setVisible(true);
    }

    public void setDimension(int witdh, int height){

        Dimension dim = getPreferredSize();
        dim.width += witdh;
        dim.height += height;

        setDimension(dim);
    }
    public void setDimX(int width){
        Dimension dim = getPreferredSize();
        dim.width += width;

        setDimension(dim);
    }
    public void setDimY(int height){
        Dimension dim = getPreferredSize();
        dim.height += height;

        setDimension(dim);
    }

    public void setDimension(Dimension dim){
        setPreferredSize(dim);
        setSize(dim);
        setMinimumSize(dim);
    }

    public void setIcon(){
        if(this.icon != null) {
            ImageIcon iIcon = Image.getIcon(this.icon);
            if(this.filterIcon) {
                Color colButton = Color.ORANGE;
                if (PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")) {
                    colButton = UIManager.getColor("TextField.foreground");
                }

                iIcon = new ImageIcon(ColorFilter.filterImage(Image.getIcon(this.icon), colButton, false));
            }

            setIcon(iIcon);
        }
        else{

            try {
                setIcon(false);
            } catch (java.beans.PropertyVetoException exc){
                System.err.println(exc);
            }
        }
    }
    public void setIcon(ImageIcon iIcon){
        if(this.icon != null && !this.icon.trim().isEmpty()) {
            setFrameIcon(iIcon);
        }else
            setFrameIcon(null);
    }


    private void limite(){
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                // Obtener límites del JDesktopPane
                Rectangle bounds = Global.panelDesktop.getDesktop().getBounds();
                Rectangle internalBounds = getBounds();

                // Limitar la posición del internalFrame dentro del desktopPane
                if (internalBounds.x < bounds.x) {
                    internalBounds.x = bounds.x;
                }
                if (internalBounds.y < bounds.y) {
                    internalBounds.y = bounds.y;
                }
                if (internalBounds.x + internalBounds.width > bounds.x + bounds.width) {
                    internalBounds.x = bounds.x + bounds.width - internalBounds.width;
                }
                if (internalBounds.y + internalBounds.height > bounds.y + bounds.height) {
                    internalBounds.y = bounds.y + bounds.height - internalBounds.height;
                }

                // Aplicar la nueva posición
                setBounds(internalBounds);
            }
        });
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {}

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        Global.panelDesktop.cerrarVentana(ID);
    }
    @Override
    public void internalFrameClosed(InternalFrameEvent e) {}
    @Override
    public void internalFrameIconified(InternalFrameEvent e) {}
    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {}
    @Override
    public void internalFrameActivated(InternalFrameEvent e) {}
    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {}


    @Override
    public void updateUI(){
        super.updateUI();
        setIcon();

    }

    public String getID(){
        return ID;
    }
}
