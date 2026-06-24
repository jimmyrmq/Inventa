package com.djm.inventa.ui.ipanel;

import java.util.List;

public interface IPluginInfo {
    String getId();
    String getNombre();
    String getDescripcion();
    String getVersion();
    String getAutor();
    String getIdDundle();

    void init();    // se llama al cargar el plugin
    void start();   // se llama al activar el plugin
    void stop();    // se llama al desactivar o cerrar el sistema

    List<IMenuContribution> getMenus();

}
