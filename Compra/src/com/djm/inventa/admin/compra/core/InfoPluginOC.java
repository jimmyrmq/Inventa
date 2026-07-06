package com.djm.inventa.admin.compra.core;

import com.djm.inventa.admin.compra.vista.proveedor.PanelProveedor;
import com.djm.inventa.ui.ipanel.IMenuContribution;
import com.djm.inventa.ui.ipanel.IPluginInfo;
import com.djm.inventa.ui.ipanel.IUIManager;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class InfoPluginOC implements IPluginInfo {

    @Override
    public String getId() {
        return "orden_compra";
    }

    @Override
    public String getNombre() {
        return "Orden de Compra";
    }

    @Override
    public String getDescripcion() {
        return "Orden de Compra";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getAutor() {
        return "Jimmy Morales";
    }

    @Override
    public String getIdDundle() {
        return "compra";
    }

    @Override
    public Integer getMenuOrden() {
        return 2;
    }


    @Override
    public void init() {
        System.out.println("Al cargar el plugin OC");
    }

    @Override
    public void start() {
        System.out.println("Al iniciar el plugin OC");
    }

    @Override
    public void stop() {
        System.out.println("Al cerrar el plugin OC");
    }


    @Override
    public List<IMenuContribution> getMenus() {

        return List.of(

                //Orden de Compra
                new IMenuContribution() {
                    @Override
                    public JMenuItem getMenu() {
                        JMenuItem menuItem = new JMenuItem("Orden Compra");
                        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
                        return menuItem;
                    }

                    @Override
                    public String getMenuGrupo() { return "Documentos"; }

                    @Override
                    public int getMenuOrden() { return 1; }

                    @Override
                    public void onClick(IUIManager uiManager) {
                        uiManager.showView(new PanelProveedor(),InfoPluginOC.this);
                    }
                },

                //Proveedor
                new IMenuContribution() {
                    @Override
                    public JMenuItem getMenu() {
                        JMenuItem menuItem = new JMenuItem("Proveedor");
                        return menuItem;
                    }
                    @Override
                    public String getMenuGrupo() { return "Documentos"; }

                    @Override
                    public int getMenuOrden() { return 2; }

                    @Override
                    public boolean getNuevoGrupo() {
                        return true;
                    }

                    @Override
                    public void onClick(IUIManager uiManager) {
                        uiManager.showView(new PanelProveedor(),InfoPluginOC.this);
                    }
                }
        );
    }
}