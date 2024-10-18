package com.djm.inventa.admin.util.file;

import com.djm.inventa.admin.util.LoggerApp;
import com.djm.util.exception.PropertiesException;
import com.djm.util.SystemProperties;

public class Propiedades {
    private SystemProperties systemProperties = null;

    public Propiedades(String path){
        try {
            systemProperties = new SystemProperties(path);
        } catch (PropertiesException exc) {
            LoggerApp.error("Error SystemProperties : " + exc);
        }
    }

    public String getValue(String value){
        String val = null;
        if(value != null && systemProperties != null) {
            val = systemProperties.getValue(value);
        }
        return val;
    }

    public String getValue(String key, Integer value){
        String val = getValue(key, String.valueOf(value));

        return val;
    }
    public String getValue(String value,String replacement){
        String result = null;
        
        if(replacement != null && systemProperties != null) {
            result = systemProperties.getValue(value , replacement);
        }

        return result;
    }
    public void setValue(String key,Object value){
        try {
            systemProperties.setValue(key, String.valueOf(value));
        }catch (PropertiesException exc){
            LoggerApp.error(exc.getMessage());
        }
    }
}
