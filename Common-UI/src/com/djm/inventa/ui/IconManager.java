package com.djm.inventa.ui;

import com.djm.inventa.core.PropiedadesSistema;
import com.djm.ui.component.ColorFilter;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.net.URL;
import java.util.Objects;

public class IconManager {

    private IconManager() { }

    public static ImageIcon get16(String name) {
        return get("/icon/16/" +name+".png");
    }

    public static ImageIcon get20(String name) {
        return get("/icon/20/"+name+".png");
    }

    public static ImageIcon get24(String name) {
        return get("/icon/24/"+name+".png");
    }

    public static ImageIcon get32(String name) {
        return get("/icon/32/"+name+".png");
    }

    public static ImageIcon get(String path) {
        if(Objects.isNull(path)) {
            return null;
        }

        return new ImageIcon(Objects.requireNonNull(IconManager.class.getResource(path)));
    }

    public static ImageIcon getIcon(URL path){
        if(path == null)
            return null;

        Color colButton = PropiedadesSistema.getColor("Label.colorDarker");
        if("LIGTH".equals(PropiedadesLookAndFeel.getPropiedad("Apariencia.lookandfeel"))){
            colButton = PropiedadesSistema.getColor("Button.color");// UIManager.getColor("TextField.foreground").brighter();;
        }

        if(colButton == null)
            colButton = Color.BLACK;

        ImageIcon ii = new ImageIcon(ColorFilter.filterImage(new ImageIcon(path), colButton, true));

        return ii;
    }
}
