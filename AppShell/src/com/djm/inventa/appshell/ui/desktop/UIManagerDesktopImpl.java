package com.djm.inventa.appshell.ui.desktop;

import com.djm.inventa.appshell.Global;
import com.djm.inventa.ui.ipanel.IPluginInfo;
import com.djm.inventa.ui.ipanel.IPanelDataAction;
import com.djm.inventa.ui.ipanel.IUIManager;
import com.djm.inventa.ui.ipanel.CrearFrameInterno;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class UIManagerDesktopImpl implements IUIManager {

    private final Map<String, JInternalFrame> ventanas = new HashMap<>();

    public UIManagerDesktopImpl() {}

    @Override
    public void showView(IPanelDataAction view, IPluginInfo plugin) {

        String viewId = view.getId();
        System.out.println("[PluginLoader] Cargando panel ID: " + viewId);
        view.setUIManager(this);

        JInternalFrame existente = ventanas.get(viewId);

        if (existente != null && !existente.isClosed()) {
            try {
                existente.setIcon(false);
                existente.setSelected(true);
            } catch (Exception ignored) {}

            existente.toFront();
            return;
        }

        JDesktopPane desktopPane = Global.panelDesktop.getDesktop();

        //crear nueva ventana
        CrearFrameInterno internalFrame = new CrearFrameInterno(view,false,
                Global.panelDesktop.getDesktop().getBounds());

        desktopPane.add(internalFrame);
        // guardar
        ventanas.put(viewId, internalFrame);

        internalFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

            @Override
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
                System.out.println("[UIManager] Cerrando plugin: " + plugin.getNombre());
                pluginClose(plugin, viewId);
            }
        });


        SwingUtilities.invokeLater(() -> {
            try {
                internalFrame.setSelected(true);
            } catch (Exception ignored) {}

            internalFrame.toFront();

            // ✅ 🔥 AHORA sí el panel puede pedir foco
            view.onViewShown();
        });

    }

    @Override
    public void closeView(String viewId) {
        pluginClose( null, viewId);
    }

    private void pluginClose(IPluginInfo plugin, String viewId){

        if(plugin != null) {
            plugin.stop();
        }

        JInternalFrame internalFrame =  ventanas.get(viewId);

        if(internalFrame != null) {
            internalFrame.setVisible(false);
            internalFrame.dispose();
        }

        //eliminar del map
        ventanas.remove(viewId);
    }

    /*JInternalFrame internalFrame = new CrearFrameInterno(null, plugin.getTitulo(), null,false, plugin.getId());
            Global.panelDesktop.addVentana(internalFrame, null);*/
}