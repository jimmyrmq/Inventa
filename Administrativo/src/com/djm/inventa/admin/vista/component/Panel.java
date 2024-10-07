package com.djm.inventa.admin.vista.component;

import com.djm.util.LayoutPanel;

import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class Panel extends JPanel {

    private Color color1 = UIManager.getColor("Panel.background");
    private Color color2 = UIManager.getColor("TextField.background");
    private Color color3 = UIManager.getColor("TextField.foreground");
    private int borderThickness = 10; // Grosor de la línea

    public Panel(){
        super(new GridBagLayout());
        setOpaque(false);
    }

    public void setPanel(JPanel panel){
        add(panel, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, borderThickness, borderThickness, borderThickness, borderThickness, 0.0f, 0.0f));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Habilitar antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Definir el área redondeada
        Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15,15);

        // Establecer el color de fondo
        g2d.setColor(new Color(0,0,0,50));
        g2d.fill(roundedRectangle);

        // Crear el efecto de sombra
        Color shadowColor = color1;
        for (int i = 0; i < borderThickness; i++) {
            // Establecer un color con transparencia
            Color currentShadowColor = new Color(shadowColor.getRed(),
                    shadowColor.getGreen(),
                    shadowColor.getBlue(),
                    (int) (255 * (1 - (float) i / borderThickness)));
            g2d.setColor(currentShadowColor);
            g2d.drawRect(i, i, getWidth() - 1 - i * 2, getHeight() - 1 - i * 2);
        }

        // Opcional: Rellenar el panel con un color de fondo
        g2d.setColor(color1); // Color de fondo del panel
        g2d.fillRect(borderThickness, borderThickness, getWidth() - 2 * borderThickness, getHeight() - 2 * borderThickness);

        g2d.dispose();
    }

}
