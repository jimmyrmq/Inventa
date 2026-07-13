package com.djm.inventa.admin.core;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.ui.AparienciaLookFeel;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Currency;
import java.util.Enumeration;
import java.util.Locale;

public class ApplicationInitializer {
    private boolean ver;

    public ApplicationInitializer() {
        this.ver = Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellista"));
        PropiedadesLookAndFeel.setPropiedades(AppContext.getInstance().getPropiedades());
    }

    public void init() {

        //Propiedas principales de sistema
        propiedadesSistema();

        //Se cargan el Look and Feel del sistema
        lookAndFeel();
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

    private void propiedadesSistema(){

        //Ver Lista Panel
        AppContext.getInstance().setPropiedad("PanelProducto.mostrar", ver);
        AppContext.getInstance().setPropiedad("PanelProducto.producto", Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellistaproducto")));
        AppContext.getInstance().setPropiedad("PanelProducto.servicio", Boolean.parseBoolean(CONSTANTS.CONFIG.getValue("panellistaservicio")));

        AppContext.getInstance().setPropiedad("Producto.ID","PRODUCTO");
        AppContext.getInstance().setPropiedad("OrdenCompra.ID","ORDEN_COMPRA");
        AppContext.getInstance().setPropiedad("Proveedor.ID","REGISTRO_PROVEEDOR");
        AppContext.getInstance().setPropiedad("Stock.ID","STOCK");
        AppContext.getInstance().setPropiedad("ImportarInventarioArchivo.ID","INVENTARIO_ARCHIVO");
        AppContext.getInstance().setPropiedad("Promocion.ID","PROMOCION_PRODUCTO");
        AppContext.getInstance().setPropiedad("Defectuoso.ID","DEFECTUOSO_PRODUCTO");
        AppContext.getInstance().setPropiedad("ActualizarPrecio.ID","ACTUALIZAR_PRECIO");

        Locale defaultLocale = Locale.getDefault();
        Currency currency = Currency.getInstance(defaultLocale);
        AppContext.getInstance().setPropiedad("Moneda.Simbolo", currency.getSymbol());
    }

    private void setConfig(String value){
        CONSTANTS.etiquetaValue.set(value, CONSTANTS.LANG.getValue(value));
    }

    public boolean getMostrarPanel(){
        return ver;
    }
}
