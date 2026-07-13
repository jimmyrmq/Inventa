package com.djm.inventa.admin.util;

import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class BorderUtil implements Border {
    private int thickness = 1;
    private int titleHeight;
    private String title;
    private Color colTextTile;
    private Color colBorder;//new Color(190,190,190);
    private Font fontTitle;//new Color(190,190,190);

    public BorderUtil(String titulo){
        this.title = titulo;
        colTextTile = UIManager.getColor("Label.foreground");//new Color(190,190,190);//
        colBorder =  UIManager.getColor("Component.borderColor");
        fontTitle = UIManager.getFont("Label.font");//new Color(190,190,190);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setFont(fontTitle);
        FontMetrics metrics = g.getFontMetrics(fontTitle);
        // Dibujar el título
        //int titleWidth = metrics.stringWidth(title);
        titleHeight = metrics.getHeight();

       // Dibujar el borde
        g2.setColor(colBorder);
         /*g2.fillRect(x, y+titleHeight, width, thickness); // Parte superior
        g2.fillRect(x, y + height - thickness, width, thickness); // Parte inferior
        g2.fillRect(x, y+titleHeight, thickness, height); // Lado izquierdo
        g2.fillRect(x + width - thickness, y+titleHeight, thickness, height); // Lado derecho*/
        g2.drawRoundRect(x, y+titleHeight, width - 1, height - titleHeight -1, 7, 7);

        // Dibujar el título centrado
        g2.setColor(colTextTile);
        g2.drawString(title, x + thickness + 5, y + (titleHeight + thickness) / 2);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        if(titleHeight == 0)
            titleHeight = 15;
        return new Insets(titleHeight+10, 10, 10, 10); // Espaciado del borde
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
