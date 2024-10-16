package com.djm.inventa.admin.vista.producto;

import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.inventa.admin.vista.component.TextField;
import com.djm.ui.themes.global.GlobalUI;
import com.djm.util.FormatNumber;

import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;

public class ProcesadorProducto {
    public ProcesadorProducto(){}

    public boolean calculoUtilidadPrecio(TextField tUtilidad, JLabel lUtilidadAdv, TextField tCosto, TextField tPrecio1){
        boolean calculo = false;
        String strUtil = tUtilidad.getText().trim();
        if(strUtil != null && !strUtil.isEmpty()){
            double util=Double.parseDouble(strUtil);

            String val = tCosto.getText().trim();

            double costo = FormatNumber.stringToDouble(val);

            boolean excesivo = util > 100;

            double ut = (util / 100) + 1;

            double p = ut * costo;

            tUtilidad.setForeground(excesivo ? Color.RED : UIManager.getColor("Textfield.foreground"));
            lUtilidadAdv.setVisible(excesivo);

            tPrecio1.setText(FormatNumber.doubleToString(p));
            calculo = true;
        }

        return calculo;
    }

    public void calculoPrecioUtilidad(TextField tCosto,TextField tUtilidad,JLabel lUtilidadAdv, TextField tPrecio){
        String str_costo=tCosto.getText().trim();
        String str_precio = tPrecio.getText().trim();
        if((str_precio != null && !str_precio.isEmpty()) && (str_costo != null && !str_costo.isEmpty())){
            String util = tUtilidad.getText();

            double utilidad = -1;
            if((util != null && !util.isEmpty())){
                utilidad = FormatNumber.stringToDouble(util);
                if(utilidad == 0)
                    utilidad = -1;
            }

            double costo = FormatNumber.stringToDouble(str_costo);
            double precio = FormatNumber.stringToDouble(str_precio);

            if(utilidad == 0 && precio > 0 && costo > 0) {
                calculoUtilidadPrecio(tUtilidad, lUtilidadAdv, tCosto, tPrecio);
            }
            else{
                double doubleValue = ((precio - costo) / costo) * 100;
                int intValue = (int) Math.round(doubleValue);
                boolean excesivo = intValue > 100;
                if (intValue < 0) {
                    intValue = 0;
                }

                tUtilidad.setForeground(excesivo ? Color.RED : UIManager.getColor("Textfield.foreground"));
                lUtilidadAdv.setVisible(excesivo);

                tUtilidad.setText(String.valueOf(intValue));
            }
        }
    }


   /* public Producto buscarProducto(boolean codBarra,String codigo){

        return producto;
    }*/
    public void validadPrecio(TextField tCosto, TextField tPrecio1, JLabel lPrecio1Adv, TextField tPrecio2,JLabel lPrecio2Adv, TextField tPrecio3,JLabel lPrecio3Adv){
        String pcosto = tCosto.getText();
        Double p1 = 0.0;
        Double p2 = 0.0;
        Double p3 = 0.0;

        Double costo = 0.0;

        String precio1 =  tPrecio1.getText();
        String precio2 = tPrecio2.getText();
        String precio3 =  tPrecio3.getText();

        /*String muestra1 = precio1;
        String muestra2 = precio2;
        String muestra3 = precio3;*/

        boolean pc = costo!=null && !pcosto.trim().isEmpty();

        if(pc){
            costo = FormatNumber.stringToDouble(pcosto);
            pc = costo > 0;
        }

        if(precio1!=null && !precio1.trim().isEmpty()) {
            p1 = FormatNumber.stringToDouble(precio1);
        }

        if(precio2!=null && !precio2.trim().isEmpty()) {
            p2 = FormatNumber.stringToDouble(precio2);
        }

        if(precio3!=null && !precio3.trim().isEmpty()) {
            p3 = FormatNumber.stringToDouble(precio3);
        }

        boolean epc1 = pc && p1 <= costo ;
        boolean epc2 = pc && p2 > 0 && p2 < costo ;//
        boolean epc3 = pc && p3 > 0 && p3 < costo ;//

        lPrecio1Adv.setVisible(epc1);
        lPrecio2Adv.setVisible(epc2);
        lPrecio3Adv.setVisible(epc3);

        tPrecio1.setToolTipText(null);
        tPrecio2.setToolTipText(null);
        tPrecio3.setToolTipText(null);

        /*tPrecio1.setForeground(colFont);
        tPrecio2.setForeground(colFont);
        tPrecio3.setForeground(colFont);*/

        /*tPrecio1.offError();
        tPrecio2.offError();
        tPrecio3.offError();*/

        if(epc1){
            //tPrecio1.setForeground(Color.red);
            //lPrecio1Adv.setText("");//CONSTANTS.LANG.getValue("productos.message.warning_precio_menor_costo"));
            tPrecio1.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));
            //tPrecio1.onError();
        }

        if(epc2){
            //tPrecio2.setForeground(Color.red);
            //lPrecio2Adv.setText("");//CONSTANTS.LANG.getValue("productos.message.warning_precio_menor_costo"));
            tPrecio2.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));
            //tPrecio2.onError();
        }

        if(epc3){
            //tPrecio3.setForeground(Color.red);
            //lPrecio3Adv.setText("");//CONSTANTS.LANG.getValue("productos.message.warning_precio_menor_costo"));
            tPrecio3.setToolTipText(CONSTANTS.LANG.getValue("producto.message.warning_precio_menor_costo"));
            //tPrecio3.onError();
        }
    }
}
