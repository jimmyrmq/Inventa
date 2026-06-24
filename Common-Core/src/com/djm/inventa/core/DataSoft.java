package com.djm.inventa.core;

public abstract class DataSoft {
    private String compliacion = "1";
    private String version;
    private String idSoft;
    private String nameDB ;

    public DataSoft(String idSoft,  String version,  String nameDB) {
        this.version = version;
        this.idSoft = idSoft;
        this.nameDB = nameDB;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCompliacion() {
        return compliacion;
    }

    public String getIdSoft() {
        return idSoft;
    }

    public String getNameDB() {
        return nameDB;
    }
}
