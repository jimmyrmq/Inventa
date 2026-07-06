module com.djm.inventa.ui {
    requires java.desktop;
    requires djm.api;
    requires com.djm.inventa.core;

    exports com.djm.inventa.ui;
    exports com.djm.inventa.ui.ipanel;
    exports com.djm.inventa.ui.component;
    exports com.djm.inventa.ui.component.renderer;
    exports com.djm.inventa.ui.util;
}
