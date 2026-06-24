package com.djm.inventa.ui.ipanel;


public interface IUIManager {
    void showView(IPanelDataAction view,  IPluginInfo plugin);
    void closeView(String viewId);
}
