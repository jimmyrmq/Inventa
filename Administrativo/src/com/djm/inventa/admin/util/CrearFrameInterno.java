package com.djm.inventa.admin.util;

import com.djm.common.GlobalFrame;
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

public class CrearFrameInterno extends JInternalFrame{
    private String icon;
    private boolean filterIcon;
    private String id;
    public CrearFrameInterno(JPanel panel,String titulo, String icon, boolean filterIcon, String id) {
        super(titulo, false, true, false, false);
        this.id = id;

        this.filterIcon = filterIcon;
        this.icon = icon;
        setIcon();

        getContentPane().add(panel);

        pack();

        /*
        setResizable(false);
        setClosable(false);
        setMaximizable(false);
        setIconifiable(false);*/

        int width = GlobalFrame.getInstance().getFrame().getWidth();
        int height = GlobalFrame.getInstance().getFrame().getHeight();
        Dimension dim = getSize();
        setLocation((width - dim.width) / 2, ((height - dim.height) / 2) - 70);

        setVisible(true);
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
    @Override
    public void updateUI(){
        super.updateUI();
        setIcon();

    }

    public String getID(){
        return id;
    }
}
