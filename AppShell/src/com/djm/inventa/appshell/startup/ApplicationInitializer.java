package com.djm.inventa.appshell.startup;

import com.djm.common.GlobalFrame;
import com.djm.inventa.appshell.ui.menu.MenuBuilder;
import com.djm.inventa.appshell.ui.VentanaPrincipal;
import com.djm.inventa.core.AppContext;
import com.djm.inventa.core.AppFileSystem;
import com.djm.inventa.core.i18n.I18nManager;
import com.djm.inventa.ui.AparienciaLookFeel;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class ApplicationInitializer {

    private AppFileSystem fileSystem;

    public ApplicationInitializer(){}

    public void init(){
        // 1. Global del AppShell (este sí puede ir primero)
        I18nManager.getInstance().registerGlobal(ApplicationInitializer.class.getClassLoader());

        //Registro conexion a l DB
        AppContext.getInstance().setPropiedad("db.service", new DatabaseServiceImpl());

        // 2. Carpetas y contexto
        fileSystem = new AppFileSystem(new DataSoftware());
        AppContext.getInstance().setFileSystem(fileSystem);
        if (!fileSystem.existsDatabase()) {
            initializeDatabase();
        }

        // 3. Propiedades de UI (colores, fuentes) — SIN etiquetas todavía
        loadProperties();

        // 4. Look and Feel
        lookAndFeel();

        // 5. Menú y ventana principal
        MenuBuilder menuBuilder = new MenuBuilder();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(menuBuilder.getMenuBar());
        GlobalFrame.getInstance().setFrame(ventanaPrincipal);

        // 6. Cargar módulos — aquí se registran los bundles de cada JAR
        System.out.println("[Bootstrap] Loading extensions...");
        try {
            ModuleLoader.cargarJars(menuBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 7. Ahora sí — todos los bundles están disponibles
        initValueEtiquetas();

        menuBuilder.addUsuario();
        ventanaPrincipal.mostrar();
    }

    private void loadProperties() {

        AppContext.getInstance().setPropiedad("Button.color", new Color(77,77,77));
        AppContext.getInstance().setPropiedad("back.color.dark",  new Color(190, 190, 190));
        AppContext.getInstance().setPropiedad("back.color.light",  new Color(90, 90, 90));
        AppContext.getInstance().setPropiedad("Label.colorDarker", Color.ORANGE);
        AppContext.getInstance().setPropiedad("Panel.backgroundTitle", new Color(81, 99, 124));
        AppContext.getInstance().setPropiedad("TextField.backgroundError", new Color(238, 46, 46));
        AppContext.getInstance().setPropiedad("Usuario.Nombre","Jimmy Morales");

        PropiedadesLookAndFeel.setPropiedades(AppContext.getInstance().getPropiedades());

    }

    private static void lookAndFeel(){

        try {
            String look = CONSTANTS.CONFIG.getValue("lookandfeel");

            AparienciaLookFeel aparienciaLookFeel = look.equals(AparienciaLookFeel.Dark.getDescripcion())?AparienciaLookFeel.Dark:AparienciaLookFeel.Light;

            AppContext.getInstance().setPropiedad("Apariencia.lookandfeel",aparienciaLookFeel);

            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if(AparienciaLookFeel.Dark.getDescripcion().equalsIgnoreCase(look) )
                UIManager.setLookAndFeel(new FlatDarkLaf());
            else
                UIManager.setLookAndFeel(new FlatLightLaf());


            //Cambiamos la letra
            Font font = UIManager.getFont("Label.font");
            Font font2 = font.deriveFont(11f);
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            // Enumerar las propiedades
            Enumeration<Object> keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();

                /*Object value = defaults.get(key);
                if(String.valueOf(key).indexOf("Table")!=-1 )//|| String.valueOf(key).indexOf("TextField")!=-1)
                    System.out.println(key+": "+value);*/

                if(String.valueOf(key).indexOf("font")!=-1) {
                    UIManager.put(key, font2);
                }
            }

        } catch (Exception exc) {
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exc2) {
                exc.printStackTrace();
            }
        }
    }

    private void initValueEtiquetas(){

        setConfig("etiquetacomponent.disponible");
        setConfig("etiquetacomponent.no_disponible");
        setConfig("etiquetacomponent.stock_critico");
        setConfig("etiquetacomponent.sin_stock");
        setConfig("etiquetacomponent.servicio");
        setConfig("documento.ordencompra");
        setConfig("documento.inventario");
        setConfig("documento.recibido");
        setConfig("documento.solicitado");
        setConfig("documento.abierto");
        setConfig("documento.deuda");
        setConfig("documento.pagado");
        setConfig("documento.cerrado");
    }

    private void setConfig(String value){
        CONSTANTS.etiquetaValue.set(value, CONSTANTS.LANG.getValue(value));
        //CONSTANTS.i18n.t(value);
    }


    private void initializeDatabase() {
        System.out.println("[Bootstrap] Primera ejecución, inicializando BD...");
        // lógica de creación de BD
    }
}