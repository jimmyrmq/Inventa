package com.djm.inventa.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppFileSystem {
    private DataSoft dataSoft;

    private final Path pathAppData;
    private final Path pathDataBase;

    //private final String pathSeparator = System.getProperty("file.separator");
    //private final String pathAppData = System.getProperty("app.localdata") + pathSeparator;
    //private final String pathDataBase = pathAppData + "data" + pathSeparator + dataSoft.getNameDB() + ".db";

    public AppFileSystem(DataSoft dataSoft) {
        if (dataSoft == null) {
            throw new IllegalArgumentException("dataSoft no puede ser null");
        }

        this.dataSoft =  dataSoft;

        // Se resuelve en el constructor, cuando dataSoft ya está disponible
        this.pathAppData  = resolveAppDataPath();
        this.pathDataBase = resolveDataBasePath();

        String appData = System.getenv("LOCALAPPDATA");

        System.setProperty("app.localdata", appData + "\\" + dataSoft.getIdSoft());

        ensureDirectoriesExist();
    }

    private Path resolveAppDataPath() {
        String localAppData = System.getenv("LOCALAPPDATA");

        if (localAppData != null) {
            // Windows: C:\Users\Usuario\AppData\Local\MiApp
            return Paths.get(localAppData, dataSoft.getIdSoft());
        }

        // Fallback multiplataforma: Linux / macOS
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, "." + dataSoft.getIdSoft());
    }

    private Path resolveDataBasePath() {
        return pathAppData
                .resolve("data")
                .resolve(dataSoft.getNameDB() + ".db");
    }

    public boolean existsDatabase() {
        return Files.exists(pathDataBase);
    }

    // Crea la carpeta AppData y la subcarpeta data si no existen
    public void ensureDirectoriesExist(){
        if (!Files.exists(pathAppData)) {
            try {
                Files.createDirectories(pathAppData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[FileSystem] Carpeta AppData creada: " + pathAppData);
        }

        Path pathData = pathAppData.resolve("data");
        if (!Files.exists(pathData)) {
            try {
                Files.createDirectories(pathData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[FileSystem] Carpeta data creada: " + pathData);
        }
    }

    public Path getPathAppData() {
        return pathAppData;
    }

    public Path getPathDataBase() {
        return pathDataBase;
    }

    // Devuelve String por si algún componente lo necesita
    public String getPathAppDataAsString() {
        return pathAppData.toString();
    }

    public String getPathDataBaseAsString() {
        return pathDataBase.toString();
    }

    @Override
    public String toString() {
        return "AppFileSystem{" +
                "pathAppData=" + pathAppData +
                ", pathDataBase=" + pathDataBase +
                '}';
    }

    /*public boolean isExistFileDB(){
        File file = new File(pathDataBase);
        return file.exists();
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
    }*/
}