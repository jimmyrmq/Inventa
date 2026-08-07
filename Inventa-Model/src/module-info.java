module com.djm.inventa.common.model {
    requires java.desktop;
    requires java.sql;
    requires com.djm.inventa.core;
    requires com.djm.inventa.util;

    exports com.djm.inventa.modelo;
    exports com.djm.inventa.persistence;
}