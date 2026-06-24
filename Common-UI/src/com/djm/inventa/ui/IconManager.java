package com.djm.inventa.ui;

import javax.swing.*;
import java.util.Objects;

public class IconManager {

    private IconManager() {
    }

    public static ImageIcon get16(String name) {
        return get("/icon/16/"+name+".png");
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
}
