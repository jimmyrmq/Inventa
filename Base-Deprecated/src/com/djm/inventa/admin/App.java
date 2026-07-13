package com.djm.inventa.admin;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.core.ApplicationInitializer;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.inventa.admin.core.CONSTANTS;
import com.djm.util.Image;
//import com.djm.inventa.admin.vista.principal.VentanaPrincipal;

import javax.swing.SwingUtilities;
import java.io.File;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            run();
        });
    }

    private static void run(){
        LoggerApp.trace("Iniciando Administracion de Ventas");

        /*if(!CONSTANTS.READER_FILE_SYSTEM.isExistFileDB()){
            File directory = new File(CONSTANTS.READER_FILE_SYSTEM.getPathAppData()+"data");
            boolean wasCreated = directory.mkdirs(); // Usa mkdirs() para crear directorios intermedios
            LoggerApp.info("Carpeta data del db creado: "+wasCreated+".");
        }*/


        Image.init(App.class, "icon/");

        ApplicationInitializer applicationInitializer = new ApplicationInitializer();
        applicationInitializer.init();

        /*VentanaPrincipal frame = new VentanaPrincipal();

        GlobalFrame.getInstance().setFrame(frame);
        //Global.panelDesktop.mostrarPanelListaProducto(applicationInitializer.getMostrarPanel());

        frame.crearGUI();
        frame.setVisible(true);*/
    }
}