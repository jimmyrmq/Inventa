import com.djm.inventa.admin.compra.core.InfoPluginOC;
import com.djm.inventa.ui.ipanel.IPluginInfo;

module com.djm.inventa.ordencompra {
    requires java.desktop;
    requires com.djm.inventa.util;
    requires djm.api;
    requires com.djm.inventa.ui;
    requires com.djm.inventa.core;

    provides IPluginInfo
            with InfoPluginOC;

    opens lang; // abre el paquete de resources al module system

}