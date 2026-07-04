module com.djm.inventa.producto {
    requires java.desktop;
    requires com.djm.inventa.util;
    requires djm.api;
    requires com.djm.inventa.ui;
    requires com.djm.inventa.core;
    requires java.sql;

    //provides IPluginInfo with InfoPluginOC;

    opens lang; // abre el paquete de resources al module system

}