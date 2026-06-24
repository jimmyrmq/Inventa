import com.djm.inventa.ui.ipanel.IPluginInfo;

module com.djm.inventa.appshell {
    exports com.djm.inventa.appshell.startup;
    requires com.djm.inventa.ui;

    requires com.djm.inventa.core;
    requires java.desktop;
    requires djm.api;
    requires com.formdev.flatlaf;
    requires com.djm.inventa.util;

    uses IPluginInfo;

    opens lang;
}