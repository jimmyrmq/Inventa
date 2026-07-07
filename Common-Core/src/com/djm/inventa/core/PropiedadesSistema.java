package com.djm.inventa.core;

import java.awt.Color;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PropiedadesSistema {
    /*
    ConcurrentHashMap en lugar de Hashtable

    Tipo            Sincronización	Claves nulas	Orden	Rendimiento (Promedio)	Uso Ideal
    HashMap         No	            Sí	            No  	Rápido	                General, sin orden requerido
    Hashtable       Sí	            No	            No	    Más lento	            Necesidad de sincronización
    TreeMap         No              No	            Sí	    Logarítmico	            Necesidad de orden
    LinkedHashMap   No              Sí	            Sí	    Rápido	                Mantener orden de inserción
    */


    private static final Map<String, Object> propiedades = new ConcurrentHashMap<>();

    private PropiedadesSistema(){}

    public static void set(String clave, Object valor){
        validarClave(clave);
        propiedades.put(clave,valor);
    }

    /*public static String get(String clave){
        String value = null;
        Object obj = propiedades.get(clave);

        *//*if(obj instanceof Enum){
            if(AparienciaLookFeel.class.isAssignableFrom(((AparienciaLookFeel) obj).getClass())) {
                AparienciaLookFeel aparienciaLookFeel = (AparienciaLookFeel) obj;
                value = aparienciaLookFeel.getDescripcion();
            }
        }
        else{*//*
            value = String.valueOf(obj);
        //}

        return  value;
    }*/

    // Devuelve Optional para que el caller decida qué hacer si no existe
    public static Optional<Object> get(String clave) {
        return Optional.ofNullable(propiedades.get(clave));
    }

    // Devuelve Optional para que el caller decida qué hacer si no existe
    public static Object getValue(String clave) {
        return propiedades.get(clave);
    }


    /*public static AparienciaLookFeel getAparienciaLookFeel(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof AparienciaLookFeel) ? (AparienciaLookFeel)value : null;
    }*/

    public static String getString(String clave, String valorDefecto) {
        Object value = propiedades.get(clave);
        return (value instanceof String s) ? s : valorDefecto;
    }

    public static DatabaseService getDatabaseService(String clave) {
        DatabaseService value = (DatabaseService) propiedades.get(clave);
        return (value instanceof DatabaseService s) ? s : null;
    }

    public static String getString(String clave) {
        return getString(clave, null);
    }

    public static int getInt(String clave, int valorDefecto) {
        Object value = propiedades.get(clave);
        if (value instanceof Integer i)   return i;
        if (value instanceof String s)    {
            try { return Integer.parseInt(s); }
            catch (NumberFormatException ignored) {}
        }
        return valorDefecto;
    }

    public static int getInt(String clave) {
        return getInt(clave, 0);
    }


    public static double getDouble(String clave, double valorDefecto) {
        Object value = propiedades.get(clave);
        if (value instanceof Double d)  return d;
        if (value instanceof Integer i) return i.doubleValue();
        if (value instanceof String s)  {
            try { return Double.parseDouble(s); }
            catch (NumberFormatException ignored) {}
        }
        return valorDefecto;
    }

    public static double getDouble(String clave) {
        return getDouble(clave, 0.0);
    }

    public static boolean getBoolean(String clave, boolean valorDefecto) {
        Object value = propiedades.get(clave);
        if (value instanceof Boolean b) return b;
        if (value instanceof String s)  return Boolean.parseBoolean(s);
        return valorDefecto;
    }

    public static boolean getBoolean(String clave) {
        return getBoolean(clave, false);
    }

    public static Color getColor(String clave, Color valorDefecto) {
        Object value = propiedades.get(clave);
        return (value instanceof Color c) ? c : valorDefecto;
    }

    public static Color getColor(String clave) {
        return getColor(clave, null);
    }

    private static void validarClave(String clave){

        if (clave == null || clave.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede ser null ni vacía");
        }
    }

    public static void eliminar(String clave){
        validarClave(clave);
        propiedades.remove(clave);
    }

    public static boolean existe(String clave) {
        return propiedades.containsKey(clave);
    }

    /*public static Map<String, Object> getPropiedades() {
        return propiedades;
    }*/

    public static Map<String, Object> getPropiedades() {
        return Collections.unmodifiableMap(propiedades); // ✅ nadie puede modificarlo
    }

    @Override
    public String toString() {
        return "PropiedadesSistema" + propiedades;
    }

    public static void limpiar() {
        propiedades.clear();
    }
}
