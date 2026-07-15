package com.djm.inventa.core;

import com.djm.inventa.core.i18n.I18nManager;

import java.util.Map;

// Common-Core: registro global accesible desde cualquier módulo
public class AppContext {

    private static AppContext instance;
    private AppFileSystem fileSystem;
    private I18nManager i18n;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    // FileSystem
    public void setFileSystem(AppFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public AppFileSystem getFileSystem() {
        assertInitialized(fileSystem, "AppFileSystem");
        return fileSystem;
    }

    // I18n
    public void setI18n(I18nManager i18n) {
        this.i18n = i18n;
    }

    public I18nManager getI18n() {
        assertInitialized(i18n, "I18nManager");
        return i18n;
    }

    // Propiedades — delega directamente a PropiedadesSistema
    public void setPropiedad(String clave, Object valor) {
        PropiedadesSistema.set(clave, valor);
    }

    public  Map<String, Object> getPropiedades() {
        return PropiedadesSistema.getPropiedades();
    }

    public String getString(String clave, String defecto) {
        return PropiedadesSistema.getString(clave, defecto);
    }

    public DatabaseService getDatabaseService(String clave) {
        return PropiedadesSistema.getDatabaseService(clave);
    }

    public Object get(String clave) {
        return PropiedadesSistema.getValue(clave);
    }

    public String getString(String clave) {
        return PropiedadesSistema.getString(clave);
    }

    public int getInt(String clave, int defecto) {
        return PropiedadesSistema.getInt(clave, defecto);
    }

    public int getInt(String clave) {
        return PropiedadesSistema.getInt(clave, -1);
    }

    public double getDouble(String clave, double defecto) {
        return PropiedadesSistema.getDouble(clave, defecto);
    }

    public boolean getBoolean(String clave, boolean defecto) {
        return PropiedadesSistema.getBoolean(clave, defecto);
    }

    public java.awt.Color getColor(String clave) {
        return PropiedadesSistema.getColor(clave);
    }

    public boolean existePropiedad(String clave) {
        return PropiedadesSistema.existe(clave);
    }

    // Validación de inicialización
    private void assertInitialized(Object obj, String nombre) {
        if (obj == null) {
            throw new IllegalStateException(
                    "[AppContext] " + nombre + " no ha sido inicializado. " +
                            "Llamalo desde Bootstrap antes de usar."
            );
        }
    }

    @Override
    public String toString() {
        return "AppContext{" +
                "fileSystem=" + fileSystem +
                ", i18n=" + i18n +
                '}';
    }
}