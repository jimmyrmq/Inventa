module com.djm.inventa.producto {
    requires java.desktop;
    requires com.djm.inventa.util;
    requires com.djm.api.util;
    requires com.djm.api.ui;
    requires java.sql;
    requires com.djm.inventa.core;
    requires com.djm.inventa.ui;
    requires com.djm.inventa.common.model;

    //provides IPluginInfo with InfoPluginOC;

    opens lang_dev;
    exports com.djm.inventa.producto.model;
    exports com.djm.inventa.stock.model; // abre el paquete de resources al module system
}