package com.djm.inventa.ui;

import java.util.Map;

public class PropiedadesLookAndFeel {

    private static Map<String, Object> propiedades;

    private PropiedadesLookAndFeel( ){

    }

    public static void setPropiedades(Map<String, Object> propiedades) {
        PropiedadesLookAndFeel.propiedades = propiedades;
    }

    public static String getPropiedad(String clave){
        String value = null;
        if(propiedades != null) {
            Object obj = propiedades.get(clave);

            if (obj instanceof Enum) {
                if (AparienciaLookFeel.class.isAssignableFrom(((AparienciaLookFeel) obj).getClass())) {
                    AparienciaLookFeel aparienciaLookFeel = (AparienciaLookFeel) obj;
                    value = aparienciaLookFeel.getDescripcion();
                }
            } else {
                value = String.valueOf(obj);
            }
        }

        return  value;
    }

    public static AparienciaLookFeel getAparienciaLookFeel(Object key) {
        Object value = propiedades.get(key);
        return (value instanceof AparienciaLookFeel) ? (AparienciaLookFeel)value : null;
    }
}
