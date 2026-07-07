package com.djm.inventa.ui.ipanel;

import javax.swing.ImageIcon;
import java.util.List;

public interface IPluginInfo {
    String getId();
    String getNombre();
    String getDescripcion();
    String getVersion();
    String getAutor();
    String getIdDundle();//El prefijo que tiene el archivo lang
    Integer getMenuOrden();//Orden en lo que aparece el menu

    default ImageIcon getIcono() {
        return null;
    }

    void start();    // se llama al cargar el plugin
    void stop();    // se llama al desactivar o cerrar el sistema

    List<IMenuContribution> getMenus();

}
