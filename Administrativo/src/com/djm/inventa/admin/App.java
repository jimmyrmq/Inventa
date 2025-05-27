package com.djm.inventa.admin;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.util.LoggerApp;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.principal.AparienciaLookFeel;
import com.djm.inventa.admin.vista.principal.Global;
import com.djm.util.Image;
import com.djm.inventa.admin.vista.principal.VentanaPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Currency;
import java.util.Enumeration;
import java.util.Locale;

public class App {
    private static boolean ver = true;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            run();
        });
    }
    private static void run(){
        if(!CONSTANTS.READER_FILE_SYSTEM.isExistFileDB()){
            File directory = new File(CONSTANTS.READER_FILE_SYSTEM.getPathAppData()+"data");
            boolean wasCreated = directory.mkdirs(); // Usa mkdirs() para crear directorios intermedios
            LoggerApp.info("Carpeta data del db creado: "+wasCreated+".");
        }

        ver = Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellista"));

        propiedadesSistema();

        Image.init(App.class,"icon");

        lookAndFeel();

        VentanaPrincipal frame = new VentanaPrincipal();

        GlobalFrame.getInstance().setFrame(frame);
        Global.panelDesktop.mostrarPanelListaProducto(ver);

        frame.crearGUI();
        frame.setVisible(true);
    }

    private static void propiedadesSistema(){

        //Ver Lista Panel
        PropiedadesSistema.setPropiedad("PanelProducto.mostrar", ver);
        PropiedadesSistema.setPropiedad("PanelProducto.producto", Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellistaproducto")));
        PropiedadesSistema.setPropiedad("PanelProducto.servicio", Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellistaservicio")));

        PropiedadesSistema.setPropiedad("Producto.ID","PRODUCTO");
        PropiedadesSistema.setPropiedad("Stock.ID","STOCK");
        PropiedadesSistema.setPropiedad("ImportarInventarioArchivo.ID","INVENTARIO_ARCHIVO");
        PropiedadesSistema.setPropiedad("Promocion.ID","PROMOCION_PRODUCTO");
        PropiedadesSistema.setPropiedad("ActualizarPrecio.ID","ACTUALIZAR_PRECIO");


        Locale defaultLocale = Locale.getDefault();
        Currency currency = Currency.getInstance(defaultLocale);
        PropiedadesSistema.setPropiedad("Moneda.Simbolo", currency.getSymbol());

    }

    private static void lookAndFeel(){

        try {
            String look = CONSTANTS.CONFIG.getValue("lookandfeel");

            AparienciaLookFeel aparienciaLookFeel = look.equals(AparienciaLookFeel.Dark.getDescripcion())?AparienciaLookFeel.Dark:AparienciaLookFeel.Light;

            PropiedadesSistema.setPropiedad("Apariencia.lookandfeel",aparienciaLookFeel);

            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if(AparienciaLookFeel.Dark.getDescripcion().equalsIgnoreCase(look) )
                UIManager.setLookAndFeel(new FlatDarkLaf());
            else
                UIManager.setLookAndFeel(new FlatLightLaf());

            PropiedadesSistema.setPropiedad("Button.color", new Color(77,77,77));
            PropiedadesSistema.setPropiedad("back.color.dark",  new Color(190, 190, 190));
            PropiedadesSistema.setPropiedad("back.color.light",  new Color(90, 90, 90));
            PropiedadesSistema.setPropiedad("Label.colorDarker", Color.ORANGE);
            PropiedadesSistema.setPropiedad("Panel.backgroundTitle", new Color(81, 99, 124));
            PropiedadesSistema.setPropiedad("TextField.backgroundError", new Color(238, 46, 46));
            PropiedadesSistema.setPropiedad("Usuario.Nombre","Jimmy Morales");

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


    private static String getSerialNumber() {
        String disco = "C";
        String result = "";

        try {
            File file = File.createTempFile("SerialDrive", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" +disco + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();

            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;

            while ((line = input.readLine()) != null) {
                result += line;
            }


            /*BufferedReader input  = new BufferedReader(new InputStreamReader(p.getInputStream())) ;
            StringBuffer sb = new StringBuffer();
            String line;
            for(; (line = input .readLine()) != null; sb.append(line)) {
            }
            result = sb.toString();*/
            input .close();
            file.delete();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result.trim();
    }
}