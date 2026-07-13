package com.djm.inventa.admin.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class SerialDSK {

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
