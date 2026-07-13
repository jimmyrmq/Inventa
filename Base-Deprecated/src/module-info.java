module com.djm.inventa.basic {
    requires java.desktop;
    requires djm.api;
    requires com.formdev.flatlaf;
    requires org.slf4j;
    requires com.djm.inventa.util;
    requires com.djm.inventa.ui;
    requires com.djm.inventa.core;
    requires com.djm.inventa.appshell;

    opens lang2;
}