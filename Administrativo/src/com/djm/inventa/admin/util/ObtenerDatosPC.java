package com.djm.inventa.admin.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ObtenerDatosPC {
    private ObtenerDatosPC(){}
    public static String getNombrePC(){
        String nombrePC = null;

        try {
            // Obtener la dirección IP local
            InetAddress localHost = InetAddress.getLocalHost();
            // Obtener el nombre de la máquina
            nombrePC = localHost.getHostName();

        } catch (UnknownHostException e) {
            LoggerApp.error("No se pudo obtener el nombre de la máquina: " + e.getMessage());
        }

        return nombrePC;
    }
}
