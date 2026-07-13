package com.djm.inventa.util;

import com.djm.inventa.exception.PropertiesException;

import java.io.*;
import java.util.Properties;

public class Propiedades {
    private Properties systemProperties =  new Properties();
    private File configFile;
    private boolean fileNew = false;

    /*public Propiedades(String resourcePath){
        if(resourcePath == null || resourcePath.trim().isEmpty()){
            return;
        }

        resourcePath = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;

        try(InputStream in = getClass().getResourceAsStream(resourcePath)
             //getClass().getClassLoader().getResourceAsStream(resourcePath)
            ){
            if (in == null) {
                throw new PropertiesException("No se encontró el recurso: " + resourcePath);
            }
            systemProperties.load(in);
        }catch (IOException | PropertiesException exc) {
            OptionPane.error("Error SystemProperties : " + exc);
            LoggerApp.error("Error SystemProperties : " + exc);
        }
    }*/

    public Propiedades(File configFile) throws PropertiesException {
        this(configFile, true);
    }

    public Propiedades(File configFile, boolean createFile) throws PropertiesException{
        if (!configFile.exists()) {
            if(createFile) {
                try {
                    configFile.createNewFile();
                    fileNew = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                throw new PropertiesException("El archivo de configuración no existe: " + configFile.getAbsolutePath());
            }
        }

        try(InputStream in = new FileInputStream (configFile)){

            this.configFile = configFile;

            systemProperties.load(in);
        }
        catch (IOException exc) {
            LoggerApp.error("Error SystemProperties ["+configFile.getAbsolutePath()+"] : " + exc);
            throw new PropertiesException("Error SystemProperties ["+configFile.getAbsolutePath()+"] : " + exc);
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

    public String getValue(String value,String defaultValue){
        String result = null;
        
        if(systemProperties != null) {
            result = systemProperties.getProperty(value, defaultValue);
        }

        return result;
    }

    public String getValueLabel(String value){
        return getValueLabel(value, null);
    }

    public String getValueLabel(String value,String defaultValue){
        String result = null;

        if(systemProperties != null) {
            result = systemProperties.getProperty(value, defaultValue);
        }

        if(result != null){
            result = result+":";
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
