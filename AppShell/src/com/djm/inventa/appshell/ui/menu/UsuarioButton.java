package com.djm.inventa.appshell.ui.menu;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.PropiedadesSistema;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.djm.inventa.ui.component.Button;
import com.djm.ui.component.ColorFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioButton  extends Button implements ActionListener {
    private JPopupMenu popupMenu;
    private Dimension dim;
    private ImageIcon iUser;
    private ImageIcon imUser;
    private JMenuItem cerrarSessión;
    private JMenuItem lUsuario;

    public UsuarioButton(){
        super(IconManager.get16("user"));
        addActionListener(this);
        setActionCommand("USUARIO");

        menu();
    }

    private void menu(){
        popupMenu = new JPopupMenu();
        dim = new Dimension(160,60);
        popupMenu.setPreferredSize(dim);

        cerrarSessión = new JMenuItem("Cerrar Sessión");
        cerrarSessión.addActionListener(this);
        cerrarSessión.setActionCommand("CERRAR_SESSION");

        lUsuario = new JMenuItem(AppContext.getInstance().getString("Usuario.Nombre"));
        lUsuario.setIcon(imUser);

        popupMenu.add(lUsuario);
        popupMenu.addSeparator();
        popupMenu.add(cerrarSessión);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action.equals("USUARIO")) {
            /*
            width popupMenu = 160/2 = -80
            width button = 22
            result  = (-80)+22 = -58
            */

            popupMenu.show(this, -58, getHeight());
        }
        else if(action.equals("CERRAR_SESSION")){
            System.out.println(popupMenu.getWidth()+" "+popupMenu.getHeight());
            System.out.println("CERRANDO SESSION");
        }
    }

    @Override
    public void updateUI(){
        Color colButton = PropiedadesSistema.getColor("Label.colorDarker");
        if(PropiedadesLookAndFeel.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = PropiedadesSistema.getColor("Button.color");// UIManager.getColor("TextField.foreground").brighter();;
        }

        imUser = new ImageIcon(ColorFilter.filterImage(IconManager.get16("muser"), colButton, true));
        iUser = new ImageIcon(ColorFilter.filterImage(IconManager.get16("user"), colButton, true));

        setIcon(iUser);

        if(popupMenu != null) {
            popupMenu.updateUI();
            cerrarSessión.updateUI();
            lUsuario.updateUI();
            lUsuario.setIcon(imUser);
        }

        super.updateUI();
    }
}
