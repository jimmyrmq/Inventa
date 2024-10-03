package com.djm.inventa.admin.util.file;

import com.djm.inventa.admin.util.DataSoftware;

import java.io.File;

public class ReaderFileSystem {
    static {
        String appData = System.getenv("LOCALAPPDATA");
        if (appData != null) {
            // Establece la variable de entorno 'app.home' para Logback
            System.setProperty("app.localdata", appData + "\\"+ DataSoftware.getDataSoftware().getIdSoft());
        }
    }
    private final String pathSeparator = System.getProperty("file.separator");

    private final String pathAppData = System.getProperty("app.localdata")+pathSeparator ;
    private final String pathDataBase = pathAppData+"data"+pathSeparator+DataSoftware.getDataSoftware().getNameDB()+".db";

    public ReaderFileSystem() { }

    public boolean isExistFileDB(){

        File file = new File(pathDataBase);
        //System.out.println(pathDataBase+": "+file.exists());
        boolean isExistFileDB = file.exists();

        return isExistFileDB;
    }

    public String getPathAppData() {
        return pathAppData;
    }

    public String getPathDataBase() {
        return pathDataBase;
    }

    public String getPathSeparator() {
        return pathSeparator;
    }

    @Override
    public String toString() {
        return "ReaderFileSystem{" +
                "pathSeparator='" + pathSeparator + '\'' +
                ", pathAppData='" + pathAppData + '\'' +
                ", pathDataBase='" + pathDataBase + '\'' +
                '}';
    }
}
