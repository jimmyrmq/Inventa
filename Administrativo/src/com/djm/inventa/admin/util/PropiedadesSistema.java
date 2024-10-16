package com.djm.inventa.admin.util;


import com.djm.inventa.admin.vista.principal.AparienciaLookFeel;

import java.awt.Color;
import java.util.Map;
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

    static {
        propiedades = new ConcurrentHashMap<>();
    }
    private static Map<String, Object> propiedades;

    private PropiedadesSistema(){}

    public static void setPropiedad(String clave, Object valor){
        check(clave);
        propiedades.put(clave,valor);
    }

    public static String getPropiedad(String clave){
        String value = null;
        Object obj = propiedades.get(clave);

        if(obj instanceof Enum){
            if(AparienciaLookFeel.class.isAssignableFrom(((AparienciaLookFeel) obj).getClass())) {
                AparienciaLookFeel aparienciaLookFeel = (AparienciaLookFeel) obj;
                value = aparienciaLookFeel.getDescripcion();
            }
        }
        else{
            value = String.valueOf(obj);
        }

        return  value;
    }
    public static Color getColor(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof Color) ? (Color)value : null;
    }
    public static AparienciaLookFeel getAparienciaLookFeel(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof AparienciaLookFeel) ? (AparienciaLookFeel)value : null;
    }
    public static String getString(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof String) ? (String)value : null;
    }
    public static int getInt(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof Integer) ? (Integer)value : null;
    }
    public double getDouble(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof Double) ? (Double) value : null;
    }

    private static void check(String clave){

        if (clave == null) {
            throw new NullPointerException("No se permite valores nulos");
        }
        else if (clave.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede ser vacio");
        }
    }

    public static void eliminar(String clave){
        propiedades.remove(clave);
    }

}
