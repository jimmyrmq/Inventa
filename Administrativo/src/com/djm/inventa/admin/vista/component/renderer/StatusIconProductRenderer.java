package com.djm.inventa.admin.vista.component.renderer;

import com.djm.inventa.admin.vista.component.EtiquetaComponent;
import com.djm.ui.component.ITipoEtiqueta;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class StatusIconProductRenderer extends EtiquetaComponent implements TableCellRenderer {
    private int COLUMN = 2;
    private int columnSelect = -1;
    public StatusIconProductRenderer(ITipoEtiqueta iTipoEtiqueta, int column) {
        super(iTipoEtiqueta);
        this.COLUMN = column;
    }
    private String sdisp,snorstock,sstock,sstockcrit;//
    private String valueSplit[];
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {

        if(col == COLUMN) {
            //System.out.println("focus: "+table.hasFocus() + " select: "+isSelected+"  r:"+row+" c:"+col);//+" "+hasFocus
            //System.out.println(row+" "+col+" "+value);
            //return aux.isDisponible()+"@"+aux.isNoRequiereStock()+"@"+aux.getStock()+"@"+aux.getStockCritico();
            valueSplit = String.valueOf(value).split("@");
            sdisp = valueSplit[0];//Disponible
            snorstock = valueSplit[1];//No requiere stock
            sstock = valueSplit[2];//Stock
            sstockcrit = valueSplit[3];//Stock Critico

            if(sstock==null||sstock.equals("null"))
                sstock = "0";

            if(sstockcrit==null||sstockcrit.equals("null"))
                sstockcrit="0";

            if(columnSelect != col) {
                columnSelect = col;
            }

            setSelected(isSelected);
            setHasFocus(table.hasFocus());

            TipoEtiqueta te = TipoEtiqueta.NoDisponible;

            //System.out.println(sdisp+" "+snorstock+" "+sstock+" "+sstockcrit+" "+(Integer.parseInt(sstock) <= Integer.parseInt(sstockcrit)));
            if(Boolean.valueOf(sdisp)){
                if(Boolean.valueOf(snorstock)){
                    te = TipoEtiqueta.Servicio;
                }else{
                    te = TipoEtiqueta.Disponible;
                    if (Integer.parseInt(sstock) == 0) {
                        te = TipoEtiqueta.SinStock;
                    }
                    else if (Integer.parseInt(sstock) <= Integer.parseInt(sstockcrit)) {
                        te = TipoEtiqueta.StockCritico;
                    }
                }
            }

            setTipoEtiqueta(te);
        }
        return this;
    }
}
