package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.vista.CONSTANTS;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.Graphics;

public class Menu extends JMenuBar {
    private ActionListenerMenu actionListenerMenu = new ActionListenerMenu();
    public Menu(){
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));

        // Crear un menú (JMenu)
        JMenu sistema = new JMenu(CONSTANTS.LANG.getValue("menu.sistema"));
        JMenu documento = new JMenu(CONSTANTS.LANG.getValue("menu.documento"));
        JMenu cliente = new JMenu(CONSTANTS.LANG.getValue("menu.cliente"));

        sistema.setMnemonic('S');
        documento.setMnemonic('D');
        cliente.setMnemonic('C');

        //Sistema
        JMenuItem configuracion = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.configuracion"));
        configuracion.setActionCommand("CONFIGURACION");
        configuracion.addActionListener(actionListenerMenu);

        JMenu apariencia = new JMenu(CONSTANTS.LANG.getValue("menu.apariencia"));

        JCheckBoxMenuItem dark = new JCheckBoxMenuItem(CONSTANTS.LANG.getValue("menu.apariencia.dark"));
        dark.setActionCommand("DARK");
        dark.addActionListener(actionListenerMenu);
        apariencia.add(dark);

        JCheckBoxMenuItem light = new JCheckBoxMenuItem(CONSTANTS.LANG.getValue("menu.apariencia.light"));
        light.setSelected(true);
        light.setActionCommand("LIGHT");
        light.addActionListener(actionListenerMenu);
        apariencia.add(light);

        ButtonGroup bg = new ButtonGroup();
        bg.add(dark);
        bg.add(light);

        JMenuItem salir = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.salir"));
        salir.setActionCommand("SALIR");

        sistema.add(configuracion);
        sistema.addSeparator();
        sistema.add(apariencia);
        sistema.addSeparator();
        sistema.add(salir);
        salir.addActionListener(actionListenerMenu);

        JMenuItem ordenCompra = new JMenuItem(CONSTANTS.LANG.getValue("menu.documento.ordencompra"));
        ordenCompra.setActionCommand("ORDEN_COMPRA");
        documento.add(ordenCompra);

        add(sistema);
        add(menuProducto());
        add(documento);
        add(cliente);
    }

    private JMenu menuProducto(){
        JMenu producto = new JMenu(CONSTANTS.LANG.getValue("menu.producto"));
        producto.setMnemonic('P');

        JMenuItem registroProducto = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.registro"));
        registroProducto.setActionCommand("PRODUCTO");
        registroProducto.addActionListener(actionListenerMenu);
        producto.add(registroProducto);

        JMenuItem stock = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.stock"));
        stock.setActionCommand("STOCK_PRODUCTO");
        producto.add(stock);

        JMenuItem entrada = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.entrada"));
        entrada.setActionCommand("ENTRADA_PRODUCTO");
        producto.add(entrada);

        JMenuItem importar = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.importar"));
        importar.setActionCommand("IMPORTAR_PRODUCTO");
        producto.add(importar);

        return producto;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 0)); // Color transparente
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
