package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.component.Button;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioButton extends Button implements ActionListener {
    private JPopupMenu popupMenu;
    private Dimension dim;
    private ImageIcon iUser;
    private ImageIcon imUser;
    private JMenuItem cerrarSessión;
    private JMenuItem lUsuario;
    public UsuarioButton(){
        super(Image.getIcon("16/user.png"));
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

        lUsuario = new JMenuItem(PropiedadesSistema.getPropiedad("Usuario.Nombre"));
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
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colButton = PropiedadesSistema.getColor("Button.color");// UIManager.getColor("TextField.foreground").brighter();;
        }

        imUser = new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/muser.png"), colButton, true));
        iUser = new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/user.png"), colButton, true));

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
