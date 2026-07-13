import com.djm.inventa.admin.compra.core.InfoPluginOC;
import com.djm.inventa.ui.ipanel.IPluginInfo;

module com.djm.inventa.ordencompra {
    requires java.desktop;
    requires com.djm.inventa.util;
    requires com.djm.inventa.ui;
    requires com.djm.inventa.core;
    requires java.sql;
    requires com.djm.inventa.common.model;
    requires com.djm.api.ui;

    provides IPluginInfo
            with InfoPluginOC;

    opens lang_dev; // abre el paquete de resources al module system

}