package com.djm.inventa.admin.vista;

import com.djm.inventa.admin.util.file.Propiedades;
import com.djm.inventa.admin.util.file.ReaderFileSystem;

import java.awt.Dimension;

public class CONSTANTS {
    public static final String TITULO = "Inventa";
    public static final ReaderFileSystem READER_FILE_SYSTEM = new ReaderFileSystem();
    public static final Propiedades LANG = new Propiedades("Administrativo/lang/es.properties");
    public static final Propiedades CONFIG = new Propiedades("Administrativo/config.ini");

    public static final Dimension CDDIM = new Dimension(227,23);
}