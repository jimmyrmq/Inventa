package com.djm.inventa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReaderFileSQL {
    public ReaderFileSQL(){}
    public synchronized String readTable(File archivo){
        String sql = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {

            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            StringBuffer sb = new StringBuffer();
            while((linea=br.readLine())!=null) {
                sb.append(linea);
                sb.append("\n");
            }

            if(sb.length() > 0) {
                sql = sb.toString();
            }
        }
        catch(Exception exc){
            LoggerApp.error(exc.getMessage());
        }
        finally{
            try{
                if( fr != null ){
                    fr.close();
                }

                if( br != null ){
                    br.close();
                }
            }catch (Exception exc){
                LoggerApp.error(exc.getMessage());
            }
        }

        return sql;
    }
}
