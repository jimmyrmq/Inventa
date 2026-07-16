import com.djm.inventa.ui.ipanel.IPluginInfo;

module com.djm.inventa.appshell {
    exports com.djm.inventa.appshell.startup;
    requires com.djm.inventa.ui;

    requires com.djm.inventa.common.model;
    requires com.djm.inventa.core;
    requires java.desktop;
    requires com.djm.api.ui;
    requires com.djm.api.util;
    requires com.formdev.flatlaf;
    requires com.djm.inventa.util;
    requires java.sql;

    uses IPluginInfo;

}