package com.djm.inventa.admin.util;

public class DataSoftware {
    private String compliacion = "1";
    private String version = "1";
    private final String idSoft = "Inventa";
    private final String nameDB = "inventa";
    private static DataSoftware dataSoftware;
    private DataSoftware(){}

    public static DataSoftware getDataSoftware() {
        if(dataSoftware == null)
            dataSoftware = new DataSoftware();

        return dataSoftware;
    }

    public String getVersion() {
        return version;
    }

    public String getIdSoft() {
        return idSoft;
    }

    public String getCompliacion() {
        return compliacion;
    }

    public String getNameDB() {
        return nameDB;
    }
}
