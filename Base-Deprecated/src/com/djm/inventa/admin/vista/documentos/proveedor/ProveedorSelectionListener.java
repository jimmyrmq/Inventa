package com.djm.inventa.admin.vista.documentos.proveedor;

import com.djm.inventa.modelo.Proveedor;

// Listener support for notifying when a proveedor is selected (double click or Enter)
public interface ProveedorSelectionListener {
    void proveedorSelected(Proveedor p);
}
