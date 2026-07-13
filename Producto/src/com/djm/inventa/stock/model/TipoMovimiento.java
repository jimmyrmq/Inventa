package com.djm.inventa.stock.model;

public enum TipoMovimiento {

    COMPRA((byte)1, "Compra", "Compra mercadería a un proveedor.", OperacionStock.SUMA),
    VENTA((byte)2, "Venta", "Venta de un producto a un cliente", OperacionStock.RESTA),
    DEVOLUCION_CLIENTE((byte)3, "Devolución de cliente", "El cliente devuelve un producto vendido.", OperacionStock.SUMA),
    DEVOLUCION_PROVEEDOR((byte)4, "Devolución de proveedor", "Devolucion de mercadería al proveedor.", OperacionStock.RESTA),
    AJUSTE_POSITIVO((byte)5, "Ajuste positivo", "Se encontro más stock del que decía el sistema.", OperacionStock.SUMA),
    AJUSTE_NEGATIVO((byte)6, "Ajuste negativo", "Se encontro menos stock del que decía el sistema.", OperacionStock.RESTA),
    //INVENTARIO((byte)7, "Inventario","Recuento físico para dejar el stock igual al contado.", OperacionStock.AJUSTE),
    TRANSFERENCIA_ENTRADA((byte)8, "Transferencia Entrada","Se recibe stock desde otra sucursal o depósito.", OperacionStock.SUMA),
    TRANSFERENCIA_SALIDA((byte)9, "Transferencia Salida","Se envía stock a otra sucursal o depósito.", OperacionStock.RESTA);

    private final byte codigo;
    private final String nombre;
    private final String descripcion;
    private final OperacionStock operacion;

    TipoMovimiento(byte codigo,String nombre, String descripcion, OperacionStock operacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.operacion = operacion;
    }

    public byte getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public OperacionStock getOperacion() {
        return operacion;
    }

    public static TipoMovimiento fromId(int id) {
        for (TipoMovimiento tipo : values()) {
            if (tipo.getCodigo() == id) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de movimiento inválido: " + id);
    }
}
