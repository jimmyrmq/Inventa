package com.djm.inventa.appshell.startup;

import com.djm.inventa.appshell.ui.desktop.UIManagerDesktopImpl;
import com.djm.inventa.appshell.ui.menu.MenuBuilder;
import com.djm.inventa.ui.ipanel.IMenuContribution;
import com.djm.inventa.ui.ipanel.IPluginInfo;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.inventa.core.i18n.I18nManager;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class ModuleLoader {

    private static IUIManager uiManager;
    private static MenuBuilder menuBuilder;
    private static List<IPluginInfo> plugins = new ArrayList<>();
    private static List<MenuItem> menuContributions = new ArrayList<>();

    // -------------------------------------------------------
    // Entrada principal
    // -------------------------------------------------------
    public static void cargarJars(MenuBuilder mBuilder) {
        menuBuilder = mBuilder;

        File directorio = new File("ext/");

        // Verifica que la carpeta exista y tenga JARs
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.out.println("[PluginLoader] Carpeta /ext no encontrada");
            return;
        }

        //Obtenemos la lista de los jar que se encuentra en extension
        File[] jars = directorio.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jar"));

        if (jars == null || jars.length == 0) {
            System.out.println("[PluginLoader] No se encontraron JARs en /ext");
            return;
        }

        System.out.println("[PluginLoader] JARs encontrados: " + jars.length);

        // ClassLoader separado por cada JAR para evitar conflictos
        for (File jar : jars) {
            cargarJar(jar);
        }

        //Cargar el menu
        menuContributions.stream().sorted(Comparator.comparing(MenuItem::orden)).
                forEach(m -> registrarEnMenu(m.menuContribution()));


        menuBuilder.buildMenus();

    }

    // -------------------------------------------------------
    // Carga individual de cada JAR
    // -------------------------------------------------------
    private static void cargarJar(File jar) {
        try {
            System.out.println("[PluginLoader] Cargando: " + jar.getName());

            //Crear UIManager con tu JDesktopPane global
            uiManager = new UIManagerDesktopImpl();

            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{ jar.toURI().toURL() },
                    IPluginInfo.class.getClassLoader() // parent classloader
            );

            ServiceLoader<IPluginInfo> loader = ServiceLoader.load(IPluginInfo.class, classLoader);

            for (IPluginInfo plugin : loader) {
                //guardar plugin
                plugins.add(plugin);

                // así cuando init() construya la UI, las claves ya están disponibles
                I18nManager.getInstance().registerModule(plugin.getId(), plugin.getIdDundle(), classLoader);

                // 2. Ciclo de vida
                plugin.init();
                plugin.start();

                // 3. Agregar al menú
                for (IMenuContribution menu : plugin.getMenus()) {
                    menuContributions.add(new MenuItem(menu, plugin.getMenuOrden()));
                }

            }

        } catch (Exception e) {
            System.err.println("[PluginLoader] Error cargando "+ jar.getName() + ": " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Registro en el menú
    // -------------------------------------------------------
    private static void registrarEnMenu(IMenuContribution menuContribution) {

        JMenuItem menuItem = menuContribution.getMenu();

        // Delegar al plugin (forma PRO)
        menuItem.addActionListener(e -> {
            menuContribution.onClick(uiManager);
        });

        menuBuilder.addMenu(menuContribution.getMenuGrupo(), menuItem, menuContribution.getMenuOrden(), menuContribution.getNuevoGrupo());
    }

    /*// Mostrar panel del plugin en la ventana principal
    private static void mostrarPanel(IPluginInfo plugin) {
        if (plugin.getTipoVista() == TipoVista.INTERNAL_FRAME) {

            JInternalFrame internalFrame = new CrearFrameInterno(null, plugin.getTitulo(), null,false, plugin.getId());
            Global.panelDesktop.addVentana(internalFrame, null);

        } else {
            // ✅ Vista normal — agrega al panel principal
            Global.panelDesktop.getDesktop().removeAll();
            //Global.panelDesktop.getDesktop().add(plugin.getPanel());
            Global.panelDesktop.getDesktop().revalidate();
            Global.panelDesktop.getDesktop().repaint();
        }
    }*/
}