package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;
import com.djm.util.LayoutPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelTitulo extends JPanel {
    private JLabel lTitulo;
    private Color colFore, colBack;

    public PanelTitulo(){
        super(new GridBagLayout());
        setOpaque(true);
        setVisible(false);

        //setPreferredSize(new Dimension(0,40));
        setBackground(colBack);

        lTitulo = new JLabel();
        lTitulo.setIcon(Image.getIcon("24/product.png"));

        lTitulo.setForeground(colFore);
        Font font = UIManager.getFont("Label.font");
        Font font2 = font.deriveFont(23f);
        font2 = font2.deriveFont(2);
        lTitulo.setFont(font2);

        add(lTitulo, LayoutPanel.constantePane(0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.FIRST_LINE_START, 0, 10, 0, 0, 1.0f, 1.0f));
    }

    public void setTitulo(String titulo){
        lTitulo.setText(titulo);
    }
    @Override
    public void updateUI(){
        colFore =PropiedadesSistema.getColor("Label.colorDarker");//new Color(48, 103, 222);//UIManager.getColor("TextField.background");
        colBack = UIManager.getColor("Panel.background").darker();
        if(PropiedadesSistema.getPropiedad("Apariencia.lookandfeel").equals("LIGTH")){
            colFore =  PropiedadesSistema.getColor("Panel.backgroundTitle");
            colBack = Color.WHITE;//PropiedadesSistema.getColor("Button.color");
        }

        //setPreferredSize(new Dimension(0,40));
        setBackground(colBack);

        if(lTitulo != null)
            lTitulo.setForeground(colFore);

        super.updateUI();
    }
    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

       Graphics2D g2d = (Graphics2D) g;

        // Define los colores del degradado
        Color color1 = Color.BLUE;
        Color color2 = Color.GREEN;
        Color col = UIManager.getColor("Panel.background");

        // Crear el degradado
        GradientPaint gradient = new GradientPaint(0, 0, col, 0, getHeight(), col.darker());
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }*/
}
