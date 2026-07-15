package com.djm.inventa.producto;

import com.djm.inventa.producto.dev.FrameRunDev;
import com.djm.inventa.stock.model.TipoMovimiento;

import javax.swing.UIManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ProductoApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando el programa... ");

        try {
            // Establece el Look and Feel del sistema operativo actual
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            FrameRunDev frameMain = new FrameRunDev();
        });
    }
}