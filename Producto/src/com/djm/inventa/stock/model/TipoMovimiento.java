package com.djm.inventa.stock.model;

public enum TipoMovimiento {

    AGREGADO_RAPIDO((byte)1, "Agregado Rapido", "Se agrego un nuevo producto al stock de forma rapida.", OperacionStock.SUMA),
    COMPRA((byte)2, "Compra", "Compra mercadería a un proveedor.", OperacionStock.SUMA),
    VENTA((byte)3, "Venta", "Venta de un producto a un cliente", OperacionStock.RESTA),
    DEVOLUCION_CLIENTE((byte)4, "Devolución de cliente", "El cliente devuelve un producto vendido.", OperacionStock.SUMA),
    DEVOLUCION_PROVEEDOR((byte)5, "Devolución de proveedor", "Devolucion de mercadería al proveedor.", OperacionStock.RESTA),
    AJUSTE((byte)6, "Ajuste", "Se ajusta el valor de la cantidad.", OperacionStock.AJUSTE),
    AJUSTE_POSITIVO((byte)7, "Ajuste positivo", "Se encontro más stock del que decía el sistema.", OperacionStock.SUMA),
    AJUSTE_NEGATIVO((byte)8, "Ajuste negativo", "Se encontro menos stock del que decía el sistema.", OperacionStock.RESTA),
    //INVENTARIO((byte)9, "Inventario","Recuento físico para dejar el stock igual al contado.", OperacionStock.AJUSTE),
    TRANSFERENCIA_ENTRADA((byte)10, "Transferencia Entrada","Se recibe stock desde otra sucursal o depósito.", OperacionStock.SUMA),
    TRANSFERENCIA_SALIDA((byte)11, "Transferencia Salida","Se envía stock a otra sucursal o depósito.", OperacionStock.RESTA);

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
