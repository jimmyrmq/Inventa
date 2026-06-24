package com.djm.inventa.appshell.ui.menu;

import com.djm.inventa.appshell.startup.CONSTANTS;
import com.djm.inventa.ui.AparienciaLookFeel;
import com.djm.inventa.ui.IconManager;
import com.djm.inventa.ui.PropiedadesLookAndFeel;
import com.djm.ui.component.ColorFilter;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;

public class MenuBuilder {

    private JMenuBar menuBar;
    private ActionListener actionListenerMenu;

    private final Map<String, List<MenuEntry>> grupos = new LinkedHashMap<>();

    public MenuBuilder() {

        menuBar = new JMenuBar();
        actionListenerMenu = new ActionListenerMenu();

        menuBar.setOpaque(false);
        menuBar.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 0));

    }

    public void addUsuario() {
        menuBar.add(Box.createGlue());
        menuBar.add(new UsuarioButton());
    }

    public void addMenu(String grupo, JMenuItem menuItem, int orden) {

        if (grupo == null || grupo.isEmpty()) {
            grupo = "Otros";
        }

        grupos.computeIfAbsent(grupo, g -> new ArrayList<>())
                .add(new MenuEntry(menuItem, orden));
    }


    public void buildMenus() {

        menuBar.add(menuSistema());

        for (Map.Entry<String, List<MenuEntry>> entry : grupos.entrySet()) {

            JMenu menu = new JMenu(entry.getKey());

            entry.getValue().stream()
                    .sorted(Comparator.comparingInt(m -> m.orden))
                    .forEach(m -> menu.add(m.item));

            menuBar.add(menu);
        }

        menuBar.revalidate();
        menuBar.repaint();
    }

    private JMenu menuSistema() {

        JMenu sistema = new JMenu(CONSTANTS.LANG.getValue("menu.sistema"));
        sistema.setMnemonic('S');

        JMenuItem configuracion = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.configuracion"));
        configuracion.setActionCommand("CONFIGURACION");
        configuracion.addActionListener(actionListenerMenu);

        JMenu apariencia = new JMenu(CONSTANTS.LANG.getValue("menu.apariencia"));

        JCheckBoxMenuItem dark = new JCheckBoxMenuItem(CONSTANTS.LANG.getValue("menu.apariencia.dark"));
        dark.setActionCommand("DARK");
        dark.addActionListener(actionListenerMenu);

        JCheckBoxMenuItem light = new JCheckBoxMenuItem(CONSTANTS.LANG.getValue("menu.apariencia.light"));
        light.setActionCommand("LIGHT");
        light.addActionListener(actionListenerMenu);

        apariencia.add(dark);
        apariencia.add(light);

        AparienciaLookFeel look = PropiedadesLookAndFeel.getAparienciaLookFeel("Apariencia.lookandfeel");

        if (look != null && "dark".equalsIgnoreCase(look.getDescripcion())) {
            dark.setSelected(true);
        } else {
            light.setSelected(true);
        }

        ButtonGroup bg = new ButtonGroup();
        bg.add(dark);
        bg.add(light);

        JMenuItem salir = new JMenuItem(CONSTANTS.LANG.getValue("menu.sistema.salir"));
        salir.setIcon(new ImageIcon(ColorFilter.filterImage(
                IconManager.get16("exit"), java.awt.Color.RED, true)));

        salir.setActionCommand("SALIR");
        salir.addActionListener(e -> {
            Salir.getInstance().exitSystem();
        });

        sistema.add(configuracion);
        sistema.addSeparator();
        sistema.add(apariencia);
        sistema.addSeparator();
        sistema.add(salir);

        return sistema;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private static class MenuEntry {

        JMenuItem item;
        int orden;

        MenuEntry(JMenuItem item, int orden) {
            this.item = item;
            this.orden = orden;
        }
    }
}