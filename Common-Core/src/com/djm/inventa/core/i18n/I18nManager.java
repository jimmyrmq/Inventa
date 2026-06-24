package com.djm.inventa.core.i18n;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class I18nManager {

    private static volatile I18nManager INSTANCE;
    private Locale locale = new Locale("es");

    // Claves de módulos específicos (ventas, compra, etc.)
    private final Map<String, String> messages = new ConcurrentHashMap<>();

    // Claves globales — fallback para todos los módulos
    private final Map<String, String> globalMessages = new ConcurrentHashMap<>();


    private I18nManager() {}

    public static I18nManager getInstance() {
        if (INSTANCE == null) {
            synchronized (I18nManager.class) {
                if (INSTANCE == null) INSTANCE = new I18nManager();
            }
        }
        return INSTANCE;
    }

    // registerModule carga en messages
    public void registerModule(String moduleId, String idBundle, ClassLoader classLoader) {
        // ✅ Sin paquete completo — busca lang/ocompra_es.properties en la raíz del JAR
        System.out.println("Registering module: " + moduleId);
        tryLoadBundle(moduleId, "lang." + idBundle, classLoader, messages);
    }

    // registerModule carga en messages
    public void registerModuleDev(String moduleId, String idBundle, ClassLoader classLoader) {
        // ✅ Sin paquete completo — busca lang/ocompra_es.properties en la raíz del JAR
        System.out.println("Registering module: " + moduleId);
        tryLoadBundle(moduleId, "lang_dev." + idBundle, classLoader, messages);
    }


    public void registerGlobal(ClassLoader classLoader) {
        tryLoadBundle("global", "lang.global", classLoader, globalMessages);
    }

    private void tryLoadBundle(String moduleId, String baseName,
                               ClassLoader cl, Map<String, String> target) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, cl);
            for (String key : bundle.keySet()) {
                target.putIfAbsent(key, bundle.getString(key));
            }
            System.out.printf("[I18n] ✅ '%s' → %d claves%n",
                    baseName, bundle.keySet().size());
        } catch (MissingResourceException e) {
            System.err.printf("[I18n] ⚠ No encontrado: '%s' en '%s'%n", baseName, moduleId);
        }
    }

    // Busca en módulo → si no está, busca en global
    public String get(String key) {
        String value = messages.get(key);
        if (value != null) return value;

        value = globalMessages.get(key);
        if (value != null) return value;

        System.err.printf("[I18n] ⚠ Clave no encontrada: '%s'%n", key);
        return "[ null: " + key + "]";
    }

    public String get(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }

    // En I18nManager.java
    public static void initForDev(String moduleId, String bundleId) {
        try {
            ClassLoader cl = I18nManager.class.getClassLoader();

            // Global
            URL globalRes = cl.getResource("lang/global_es.properties");
            if (globalRes != null) {
                URLClassLoader globalLoader = new URLClassLoader(
                        new URL[]{ resolveJarUrl(globalRes, "lang/global_es.properties") }, cl
                );
                getInstance().registerGlobal(globalLoader);
            }

            // Módulo actual
            URL moduleRes = cl.getResource("lang_dev/" + bundleId + "_es.properties");
            if (moduleRes != null) {
                URLClassLoader moduleLoader = new URLClassLoader(
                        new URL[]{ resolveJarUrl(moduleRes, "lang_dev/" + bundleId + "_es.properties") }, cl
                );
                getInstance().registerModuleDev(moduleId, bundleId, moduleLoader);
            }

            System.out.println("[I18n-DEV] Inicializado para módulo: " + moduleId);

        } catch (Exception e) {
            System.err.println("[I18n-DEV] Error: " + e.getMessage());
        }
    }

    private static URL resolveJarUrl(URL resource, String path) throws Exception {
        String full = resource.toString();
        if (full.startsWith("jar:")) {
            return new URL(full.replace("!/" + path, "!").replace("jar:", ""));
        }
        // En dev (carpeta de clases, no JAR)
        String base = full.replace(path.replace("/", "/"), "");
        return new URL(base);
    }
}