package com.djm.inventa.appshell;

import com.djm.inventa.appshell.startup.ApplicationInitializer;

import javax.swing.*;

/**
 * Aplicacion principal donde se gestionara todo lo relacionado del sistema
 *  -Las extensiones disponible.
 *  -Creacion de menu.
 *  -Los metadatos del  sistema.
 *  -Arranque principal
 *
 *  @Autor Jimmy Morales
 *  @Version 1.0
 *  @since 2026
*/

public class ApplicationBootstrap {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationInitializer initializer = new ApplicationInitializer();
            initializer.init();
        });
    }
}