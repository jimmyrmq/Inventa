CREATE TABLE IF NOT EXISTS tipo_movimiento (
      id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
      nombre TEXT NOT NULL,
      descripcion TEXT,
      lenguage_key TEXT
    );


INSERT INTO tipo_movimiento (nombre,Descripcion,lenguage_key)  VALUES
    ('AGREGADO_RAPIDO','Agregado Rapido','documento.ventas'),
    ('COMPRA','Compra mercadería a un proveedor','documento.ventas'),
    ('VENTA','Venta de un producto a un cliente','documento.ventas'),
    ('DEVOLUCION_CLIENTE','Devolución de cliente','documento.ventas'),
    ('DEVOLUCION_PROVEEDOR','Devolución de proveedor','documento.ventas'),
    ('AJUSTE','Se ajusta el valor de la cantidad','documento.ventas'),
    ('AJUSTE_POSITIVO','Ajuste positivo','documento.ventas'),
    ('AJUSTE_NEGATIVO','Ajuste negativo','documento.ventas'),
    ('TRANSFERENCIA_ENTRADA','Transferencia Entrada','documento.ventas'),
    ('TRANSFERENCIA_SALIDA','Transferencia Salida','documento.ventas');