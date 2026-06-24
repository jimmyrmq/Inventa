package com.djm.inventa.ui.component.renderer;

import java.util.HashMap;
import java.util.Map;

public class EtiquetaValue {

    private Map<String, String> data = new HashMap<>();
    private static EtiquetaValue etiquetaValue;

    private EtiquetaValue(){}

    public static EtiquetaValue getInstance(){
        if(etiquetaValue == null){
            etiquetaValue = new EtiquetaValue();
        }
        return etiquetaValue;
    }

    // guardar valor
    public void set(String key, String value) {
        data.put(key, value);
    }

    // recuperar valor
    public String get(String key) {
        return data.get(key);
    }

    // opcional: verificar si existe
    public boolean contains(String key) {
        return data.containsKey(key);
    }
}