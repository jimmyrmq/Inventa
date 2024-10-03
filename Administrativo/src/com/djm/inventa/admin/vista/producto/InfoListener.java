package com.djm.inventa.admin.vista.producto;

import com.djm.common.GlobalFrame;
import com.djm.ui.component.OptionPane;

import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfoListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();

        OptionPane.warning(label.getToolTipText());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
