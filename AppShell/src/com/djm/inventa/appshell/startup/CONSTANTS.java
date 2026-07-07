package com.djm.inventa.appshell.startup;

import com.djm.inventa.core.i18n.I18nManager;
import com.djm.inventa.util.Propiedades;
import com.djm.inventa.util.LoggerApp;
import com.djm.ui.component.OptionPane;
import com.djm.util.exception.PropertiesException;
import com.djm.inventa.ui.component.renderer.EtiquetaValue;

import java.io.File;

public class CONSTANTS {
    public static final I18nManager I8N = I18nManager.getInstance();
    public static final Propiedades CONFIG;
    public static EtiquetaValue etiquetaValue = EtiquetaValue.getInstance();
    //public static final Dimension CDDIM = new Dimension(227,23);

    static {
        //Comprobamos si la carpeta AppData Existe
        File dirInventa = new File(System.getProperty("app.localdata"));

        boolean wasCreated = dirInventa.exists();
        if(!wasCreated)
            wasCreated = dirInventa.mkdirs(); // Usa mkdirs() para crear directorios intermedios

        String pathConfig = System.getProperty("app.localdata") + "/config.ini";

        Propiedades cAux;
        try {
            cAux = new Propiedades(new File(pathConfig));
        } catch (PropertiesException exc) {
            OptionPane.error(exc.getMessage());
            LoggerApp.error(exc.getMessage());
            cAux = null;
        }

        CONFIG = cAux;
        if (CONFIG != null && CONFIG.isFileNew()) {
            CONFIG.setValue("lookandfeel", "DARK");
            CONFIG.setValue("panellista", false);
            CONFIG.setValue("panellistaproducto", true);
            CONFIG.setValue("panellistaservicio", false);
        }

        //Iniciando varaibles
        CONSTANTS.CONFIG.setValue("database.type","lite"); // Cambiar a "mysql" si se desea usar MySQL
    }

    /*private static void propertiesLang(){
        System.out.println("Loading properties file");
        String pathLang = "/lang/global_es.properties";

        URL url = CONSTANTS.class.getResource(pathLang);

        if(url != null) {
            try {
                LANG = new Propiedades(new File(url.toURI()), false);
            } catch (URISyntaxException | PropertiesException exc) {
                OptionPane.error(exc.getMessage());
                LoggerApp.error(exc.getMessage());
                System.exit(1);
            }
        }
        else{
            OptionPane.error("No se encontro el archivo "+pathLang);
            LoggerApp.error("No se encontro el archivo "+pathLang);
            //System.exit(1);
        }
    }*/
}