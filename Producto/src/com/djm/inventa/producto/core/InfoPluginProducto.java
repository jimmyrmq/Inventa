package com.djm.inventa.producto.core;

import com.djm.inventa.core.AppContext;
import com.djm.inventa.producto.vista.producto.PanelManagerProducto;
import com.djm.inventa.producto.vista.producto.ProductoListener;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.ipanel.IMenuContribution;
import com.djm.inventa.ui.ipanel.IPluginInfo;
import com.djm.inventa.ui.ipanel.IUIManager;

import javax.swing.JMenuItem;
import java.util.List;

public class InfoPluginProducto implements IPluginInfo {

    @Override
    public String getId() {
        return "ext_productos";
    }

    @Override
    public String getNombre() {
        return "Registro Producto";
    }

    @Override
    public String getDescripcion() {
        return "Registro de Producto";
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
        return "producto";
    }

    @Override
    public Integer getMenuOrden() {
        return 1;
    }

    @Override
    public void start() {
        System.out.println("Al cargar el plugin Producto");
    }

    @Override
    public void stop() {
        System.out.println("Al cerrar el plugin OC");
    }

    @Override
    public List<IMenuContribution> getMenus() {

        return List.of(

            //Producto
            new IMenuContribution() {

                @Override
                public JMenuItem getMenu() {
                    JMenuItem menuItem = new JMenuItem(CONSTANTS.i18n.getValue("menu.producto.registro"));
                    menuItem.setIcon(IconManager.getIcon(getClass().getResource("/icons/product.png")));
                    return menuItem;
                }
                public String getMenuGrupo() { return CONSTANTS.i18n.getValue("menu.producto"); }
                public int getMenuOrden() { return 1; }
                public void onClick(IUIManager uiManager) {
                    PanelManagerProducto panelManagerProducto = new PanelManagerProducto();

                    //Crear la accion aqui
                    ProductoListener productoListener = new ProductoListener(panelManagerProducto);
                    panelManagerProducto.setActionListener(productoListener);

                    uiManager.showView(panelManagerProducto, InfoPluginProducto.this);
                }
            }
        );
    }
}