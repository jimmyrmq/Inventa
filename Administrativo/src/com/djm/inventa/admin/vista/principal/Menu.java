package com.djm.inventa.admin.vista.principal;

import com.djm.inventa.admin.vista.CONSTANTS;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.Graphics;

public class Menu extends JMenuBar {
    public Menu(){
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));

        ActionListenerMenu actionListenerMenu = new ActionListenerMenu();

        // Crear un menú (JMenu)
        JMenu sistema = new JMenu(CONSTANTS.LANG.getValue("menu.sistema"));
        JMenu producto = new JMenu(CONSTANTS.LANG.getValue("menu.producto"));
        JMenu documento = new JMenu(CONSTANTS.LANG.getValue("menu.documento"));
        JMenu cliente = new JMenu(CONSTANTS.LANG.getValue("menu.cliente"));

        sistema.setMnemonic('S');
        producto.setMnemonic('P');
        documento.setMnemonic('D');
        cliente.setMnemonic('C');

        //Sistema
        JMenuItem configuracion = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.configuracion"));
        configuracion.setActionCommand("CONFIGURACION");
        configuracion.addActionListener(actionListenerMenu);

        JMenuItem salir = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.salir"));
        salir.setActionCommand("SALIR");

        sistema.add(configuracion);
        sistema.addSeparator();
        sistema.add(salir);
        salir.addActionListener(actionListenerMenu);

        JMenuItem registroProducto = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.registro"));
        registroProducto.setActionCommand("REGISTRO_PRODUCTO");
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

        JMenuItem ordenCompra = new JMenuItem(CONSTANTS.LANG.getValue("menu.documento.ordencompra"));
        ordenCompra.setActionCommand("ORDEN_COMPRA");
        documento.add(ordenCompra);

        add(sistema);
        add(producto);
        add(documento);
        add(cliente);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 0)); // Color transparente
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
