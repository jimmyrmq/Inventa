package com.djm.inventa.admin.compra.vista.proveedor;


import com.djm.inventa.admin.compra.modelo.Proveedor;

// Listener support for notifying when a proveedor is selected (double click or Enter)
public interface ProveedorSelectionListener {
    void proveedorSelected(Proveedor p);
}
