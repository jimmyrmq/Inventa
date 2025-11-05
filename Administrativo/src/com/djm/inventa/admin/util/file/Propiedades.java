package com.djm.inventa.admin.util.file;

import com.djm.inventa.admin.util.LoggerApp;
import com.djm.util.exception.PropertiesException;

import java.io.*;
import java.util.Properties;

public class Propiedades {
    private Properties systemProperties =  new Properties();
    private File configFile;
    private boolean fileNew = false;

    public Propiedades(String resourcePath){
        try(InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath)){
            if (in == null) {
                throw new PropertiesException("No se encontró el recurso: " + resourcePath);
            }
            systemProperties.load(in);
        }catch (IOException | PropertiesException exc) {
            LoggerApp.error("Error SystemProperties : " + exc);
        }
    }

    public Propiedades(File configFile){
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                fileNew = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try(InputStream in = new FileInputStream (configFile)){
            if (in == null) {
                throw new PropertiesException("No se encontró el recurso fileInputStream");
            }

            this.configFile = configFile;

            systemProperties.load(in);
        }catch (IOException | PropertiesException exc) {
            LoggerApp.error("Error SystemProperties : " + exc);
        }
    }

    public String getValue(String value){
        String val = null;
        if(value != null && systemProperties != null) {
            val = systemProperties.getProperty(value);
        }
        return val;
    }

    public String getValue(String key, Integer value){
        String val = getValue(key, String.valueOf(value));

        return val;
    }
    public String getValue(String value,String keyReplacement){
        String result = null;
        
        if(keyReplacement != null && systemProperties != null) {
            String replacement = systemProperties.getProperty(keyReplacement);

            if(replacement == null)
                replacement = keyReplacement;

            result = systemProperties.getProperty(value , replacement);
        }

        return result;
    }
    public void setValue(String key,Object value){
        systemProperties.setProperty(key, String.valueOf(value));
        if(configFile != null)
            store();
    }

    private void store(){
        // Escribir valores por defecto
        try (OutputStream out = new FileOutputStream(configFile)) {
            try {
                systemProperties.store(out, null); // No escribe comentario ni fecha
            } catch (IOException e) {
            }
        } catch (FileNotFoundException exc) {
            LoggerApp.error("Error SystemProperties : " + exc);
            throw new RuntimeException(exc);
        } catch (IOException exc) {
            LoggerApp.error("Error SystemProperties : " + exc);
            throw new RuntimeException(exc);
        }
    }

    public boolean isFileNew(){
        return fileNew;
    }
}
