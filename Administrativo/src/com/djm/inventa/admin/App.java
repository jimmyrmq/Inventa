package com.djm.inventa.admin;

import com.djm.common.GlobalFrame;
import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.principal.AparienciaLookFeel;
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
import java.util.Enumeration;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            run();
        });
    }
    private static void run(){
        Image.init(App.class,"icon");

        lookAndFeel();

        VentanaPrincipal frame = new VentanaPrincipal();

        GlobalFrame.getInstance().setFrame(frame);

        frame.crearGUI();
        frame.setVisible(true);
    }

    private static void lookAndFeel(){

        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.setLookAndFeel(new FlatLightLaf());

            PropiedadesSistema.setPropiedad("Apariencia.lookandfeel", AparienciaLookFeel.Light);

            //Cambiamos la letra
            Font font = UIManager.getFont("Label.font");
            Font font2 = font.deriveFont(11f);
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            // Enumerar las propiedades
            Enumeration<Object> keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                //Object value = defaults.get(key);
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