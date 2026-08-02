CREATE TABLE IF NOT EXISTS tipo_movimiento (
    id INT NOT NULL AUTO_INCREMENT,
    nombre_id VARCHAR(100) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    factor_stock TINYINT NOT NULL CHECK (factor_stock IN (-1, 0, 1)),
    --language_key VARCHAR(100),
    PRIMARY KEY (id)
);

INSERT INTO tipo_movimiento
(nombre_id,label, descripcion, factor_stock)-- , language_key)
VALUES
    ('AGREGADO_RAPIDO', 'Agregado Rapido','Se agrega un nuevo producto al stock de forma rápida.', 1), --, 'movimiento.agregado_rapido'),
    ('COMPRA', 'Compra','Compra de mercadería a un proveedor.', 1), --, 'movimiento.compra'),
    ('VENTA', 'Venta', 'Venta de un producto a un cliente.', -1), --, 'movimiento.venta'),
    ('DEVOLUCION_CLIENTE','Devolución de cliente', 'El cliente devuelve un producto vendido.', 1), --, 'movimiento.devolucion_cliente'),
    ('DEVOLUCION_PROVEEDOR','Devolución de proveedor','Devolución de mercadería a un proveedor.', -1), --, 'movimiento.devolucion_proveedor'),
    ('AJUSTE', 'Ajuste', 'Se ajusta el valor del stock.', 0), --, 'movimiento.ajuste'),
    ('AJUSTE_POSITIVO', 'Ajuste Positivo', 'Se encontro más stock del que decía el sistema.', 1), --, 'movimiento.ajuste'),
    ('AJUSTE_NEGATIVO', 'Ajuste Negativo', 'Se encontro menos stock del que decía el sistema.', -1), --, 'movimiento.ajuste'),
    ('TRANSFERENCIA_ENTRADA', 'Transferencia Entrada','Se recibe stock desde otra sucursal o depósito.', 1), --, 'movimiento.transferencia_entrada'),
    ('TRANSFERENCIA_SALIDA', 'Transferencia Salida', 'Se envía stock a otra sucursal o depósito.', -1); --, 'movimiento.transferencia_salida');