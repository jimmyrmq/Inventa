package com.djm.inventa.ui.ipanel;

import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.djm.ui.component.ColorFilter;
import com.djm.ui.themes.global.GlobalUI;
import com.djm.util.Image;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CrearFrameInterno extends JInternalFrame implements InternalFrameListener{
    private boolean filterIcon;
    private Rectangle bounds ;
    private ImageIcon icon;

    public CrearFrameInterno(IPanelDataAction view, boolean filterIcon, Rectangle bounds) {
        super(view.getTitle(), false, true, false, false);
        this.bounds = bounds;

        ImageIcon icon = view.getIcon();

        if(icon != null) {
            this.icon = icon;
            this.filterIcon = filterIcon;
            setIcon();
        }

        JPanel panel = view.getPanel();
        if(panel != null) {
            getContentPane().add(panel);
        }

        setDimension(10,10);

        //Reajustar el tamaño del panel para que se muestre correctamente
        pack();

        //setResizable(false);
        /*setClosable(false);
        setMaximizable(false);
        setIconifiable(false);*/

        if(bounds != null) {
            center();
            limite();
        }

        setDefaultCloseOperation(0);//Deshabilitar el botton  de cerrar
        addInternalFrameListener(this);
        setVisible(true);
    }

    public void center(){
        int width = this.bounds.width;
        int height = this.bounds.height;
        Dimension dim = getSize();
        setLocation((width - dim.width) / 2, ((height - dim.height) / 2) - 70);
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
            ImageIcon iIcon = this.icon;
            if(this.filterIcon) {
                Color colButton = Color.ORANGE;
                if (PropiedadesLookAndFeel.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")) {
                    colButton = UIManager.getColor("TextField.foreground");
                }

                iIcon = new ImageIcon(ColorFilter.filterImage(this.icon, colButton, false));
            }

            setFrameIcon(iIcon);
        }
        else{

            setFrameIcon(null);
            try {
                setIcon(false);
            } catch (java.beans.PropertyVetoException exc){
                System.err.println(exc);
            }
        }
    }


    private void limite(){
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                // Obtener límites del JDesktopPane
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
}
