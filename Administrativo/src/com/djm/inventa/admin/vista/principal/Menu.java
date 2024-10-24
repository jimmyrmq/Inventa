package com.djm.inventa.admin.vista.principal;


import com.djm.inventa.admin.util.PropiedadesSistema;
import com.djm.inventa.admin.vista.CONSTANTS;
import com.djm.ui.component.ColorFilter;
import com.djm.util.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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

        sistema.setMnemonic('S');
        documento.setMnemonic('D');

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

        light.setActionCommand("LIGHT");
        light.addActionListener(actionListenerMenu);
        apariencia.add(light);

        AparienciaLookFeel look = PropiedadesSistema.getAparienciaLookFeel("Apariencia.lookandfeel");

        if("dark".equalsIgnoreCase(look.getDescripcion())){
            dark.setSelected(true);
        }else
            light.setSelected(true);

        ButtonGroup bg = new ButtonGroup();
        bg.add(dark);
        bg.add(light);

        JMenuItem salir = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.salir"));
        salir.setIcon( new ImageIcon(ColorFilter.filterImage(Image.getIcon("16/exit.png"), Color.RED, true)));
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
        add(menuCliente());
        add(menuVentas());
        add(Box.createGlue());
        add(new UsuarioButton());
    }

    private JMenu menuProducto(){
        JMenu producto = new JMenu(CONSTANTS.LANG.getValue("menu.producto"));
        JMenu importar = new JMenu(CONSTANTS.LANG.getValue("menu.producto.importar"));

        producto.setMnemonic('P');

        JMenuItem registroProducto = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.registro"));
        registroProducto.setActionCommand("PRODUCTO");
        registroProducto.addActionListener(actionListenerMenu);

        JMenuItem stock = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.stock"));
        stock.setActionCommand("STOCK");
        stock.addActionListener(actionListenerMenu);

        JMenuItem importarArchivo = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.importarProduto"));
        importarArchivo.setActionCommand("IMPORTAR_PRODUCTO");
        importarArchivo.addActionListener(actionListenerMenu);

        JMenuItem importarOC = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.importarProdutoOC"));
        importarOC.setActionCommand("IMPORTAR_PRODUCTO_OC");
        importarOC.addActionListener(actionListenerMenu);

        JMenuItem defectuoso = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.defectuoso"));
        defectuoso.setActionCommand("DEFECTUOSO");
        defectuoso.addActionListener(actionListenerMenu);

        JMenuItem cambioPrecio = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.modificarprecio"));
        cambioPrecio.setActionCommand("CAMBIO_PRECIO");
        cambioPrecio.addActionListener(actionListenerMenu);

        JMenuItem bloqueo = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.bloqueodesb"));
        bloqueo.setActionCommand("BLOQUEO_DESBLOQUEO");
        bloqueo.addActionListener(actionListenerMenu);

        JMenuItem promociones = new JMenuItem(CONSTANTS.LANG.getValue("menu.producto.promocion"));
        promociones.setActionCommand("PROMOCIONES");
        promociones.addActionListener(actionListenerMenu);

        importar.add(importarArchivo);
        importar.add(importarOC);

        JCheckBoxMenuItem verLista = new JCheckBoxMenuItem(CONSTANTS.LANG.getValue("menu.producto.verlista"));
        verLista.setActionCommand("PANEL_PRODUCTO");
        verLista.setSelected(PropiedadesSistema.getBoolean("PanelProducto.mostrar"));
        verLista.addActionListener(actionListenerMenu);

        producto.add(registroProducto);
        producto.add(promociones);
        producto.addSeparator();
        producto.add(stock);
        producto.add(importar);
        producto.addSeparator();
        producto.add(cambioPrecio);
        producto.add(bloqueo);
        producto.addSeparator();
        producto.add(defectuoso);
        producto.addSeparator();
        producto.add(verLista);

        return producto;
    }

    private JMenu menuCliente() {
        JMenu cliente = new JMenu(CONSTANTS.LANG.getValue("menu.cliente"));
        cliente.setMnemonic('C');

        JMenuItem registro = new JMenuItem(CONSTANTS.LANG.getValue("menu.cliente.registro"));
        registro.setActionCommand("REGISTRO_CLIENTE");
        registro.addActionListener(actionListenerMenu);

        JMenuItem deuda = new JMenuItem(CONSTANTS.LANG.getValue("menu.cliente.deuda"));
        deuda.setActionCommand("DEUDA_CLIENTE");
        deuda.addActionListener(actionListenerMenu);

        cliente.add(registro);
        cliente.addSeparator();
        cliente.add(deuda);

        return cliente;
    }

    private JMenu menuVentas(){
        JMenu ventas = new JMenu(CONSTANTS.LANG.getValue("menu.ventas"));
        ventas.setMnemonic('V');

        JMenuItem facturacion = new JMenuItem(CONSTANTS.LANG.getValue("menu.ventas.facturacion"));
        facturacion.setActionCommand("FACTURACION");

        JMenuItem detalleFactura = new JMenuItem(CONSTANTS.LANG.getValue("menu.ventas.detallefactura"));
        detalleFactura.setActionCommand("DETALLES_FACTURA");;

        JMenuItem cierreCaja = new JMenuItem(CONSTANTS.LANG.getValue("menu.ventas.cierrecaja"));
        cierreCaja.setActionCommand("LISTA_FACTURA");

        JMenuItem devolucion = new JMenuItem(CONSTANTS.LANG.getValue("menu.ventas.devolucion"));
        devolucion.setActionCommand("LISTA_FACTURA");

        ventas.add(facturacion);
        ventas.add(detalleFactura);
        ventas.addSeparator();
        ventas.add(cierreCaja);
        ventas.addSeparator();
        ventas.add(devolucion);

        return  ventas;
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 0)); // Color transparente
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
