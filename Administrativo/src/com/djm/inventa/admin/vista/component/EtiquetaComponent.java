package com.djm.inventa.admin.vista.component;

import com.djm.ui.themes.global.GlobalUI;

import javax.swing.JComponent;
import javax.swing.UIManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.djm.ui.component.ITipoEtiqueta;

public class EtiquetaComponent extends JComponent {
    private ITipoEtiqueta ITipoEtiqueta;
    //private final int width = 90;
    //private final int height = 14;
    private String title ;
    private Font font;
    private int posx_tt, posy_tt;
    private boolean selected;
    private boolean hasFocus;
    private Color colBack;
    private Color colBackSelected;
    private Color colBackNoSelected;
    private Color colBackFocus;

    public EtiquetaComponent(ITipoEtiqueta ITipoEtiqueta){
        setOpaque(false);
        setFocusable(false);

        font = UIManager.getFont("Table.font");
        colBackNoSelected = UIManager.getColor("Table.background");
        colBackSelected = UIManager.getColor("Table.selectionBackground");
        colBackFocus = UIManager.getColor("Table.selectionInactiveBackground");

        setTipoEtiqueta(ITipoEtiqueta);
    }

    public void setTipoEtiqueta(ITipoEtiqueta ITipoEtiqueta) {
        if(ITipoEtiqueta !=null) {
            this.ITipoEtiqueta = ITipoEtiqueta;
            title = ITipoEtiqueta.getTitle();
            //System.out.println(">>>>>"+title+"<<<<<");
            repaint();
            //System.out.println("2 "+ getSize().width+"2.0 "+ getPreferredSize().width);
        }
    }

    private void calculateTextXY(){

        FontMetrics fmt = getFontMetrics(font);
        if(posx_tt != getWidth())
            posx_tt = ((getWidth()-fmt.stringWidth(title))/2);

        if(posy_tt != getHeight())
            posy_tt = (((getHeight() - fmt.getHeight()) / 2) + fmt.getAscent());
    }

    /*public TipoEtiqueta getTipoEtiqueta() {
        return tipoEtiqueta;
    }*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        colBack = colBackNoSelected; //selected ? colBackSelected : (hasFocus?colBackFocus:colBackNoSelected);
        if(hasFocus && selected)
            colBack = colBackSelected;
        else if(selected)
            colBack = colBackFocus;

        g2.setColor(colBack);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(ITipoEtiqueta.getColor());
        g2.fillRoundRect(3, 2, getWidth() - 5, getHeight() - 4, 4, 4);

        if(title!=null) {
            calculateTextXY();

            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString(title, posx_tt, posy_tt);
        }

        g2.dispose();
        g.dispose();
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public void setHasFocus(boolean focus){
        hasFocus = focus;
    }
    @Override
    public void updateUI(){

        font = UIManager.getFont("Table.font");
        colBackNoSelected = UIManager.getColor("Table.background");
        colBackSelected = UIManager.getColor("Table.selectionBackground");
        colBackFocus = UIManager.getColor("Table.selectionInactiveBackground");

        super.updateUI();
    }
}
