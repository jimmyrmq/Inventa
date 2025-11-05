package com.djm.inventa.admin.vista;

import com.djm.inventa.admin.util.file.Propiedades;
import com.djm.inventa.admin.util.file.ReaderFileSystem;

import java.awt.Dimension;
import java.io.*;

public class CONSTANTS {
    public static final String TITULO = "Inventa";
    public static final ReaderFileSystem READER_FILE_SYSTEM = new ReaderFileSystem();
    public static final Propiedades LANG = new Propiedades("lang/es.properties");
    public static final Propiedades CONFIG;

    static {
        //Comprobamos si la carpeta AppData Existe
        File dirInventa = new File(System.getProperty("app.localdata"));
        boolean wasCreated = dirInventa.exists();
        if(!wasCreated)
            wasCreated = dirInventa.mkdirs(); // Usa mkdirs() para crear directorios intermedios

            //
        String pathConfig = System.getProperty("app.localdata") + "/config.ini";

        CONFIG = new Propiedades(new File(pathConfig));

        if (CONFIG.isFileNew()) {
            CONFIG.setValue("lookandfeel", "DARK");
            CONFIG.setValue("panellista", false);
            CONFIG.setValue("panellistaproducto", true);
            CONFIG.setValue("panellistaservicio", false);
        }
    }

    public static final Dimension CDDIM = new Dimension(227,23);
}