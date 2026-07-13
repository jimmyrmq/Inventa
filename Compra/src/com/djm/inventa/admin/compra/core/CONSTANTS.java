package com.djm.inventa.admin.compra.core;

import com.djm.inventa.core.i18n.I18nManager;
import com.djm.inventa.util.Propiedades;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.component.OptionPane;
//import com.djm.util.exception.PropertiesException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class CONSTANTS {
    //public static Propiedades i18n;// = new Propiedades("lang/global_es.properties");

    public static final I18nManager i18n = I18nManager.getInstance();

    /*static {
        propertiesLang();
    }

    private static void propertiesLang(){
        String pathLang = "/lang/compra_es.properties";
        URL url =CONSTANTS.class.getResource(pathLang);

        if(url != null) {
            try {
                LANG = new Propiedades(new File(url.toURI()), false);//"/lang/global_es.properties");
            } catch (URISyntaxException | PropertiesException exc) {
                OptionPane.error(exc.getMessage());
                LoggerApp.error(exc.getMessage());
            }
        }else{
            OptionPane.error("No se encontro el archivo "+pathLang);
            LoggerApp.error("No se encontro el archivo "+pathLang);
            System.exit(-1);
        }
    }*/
}
