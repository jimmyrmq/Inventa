CREATE TABLE IF NOT EXISTS movimiento_stock (
	id INTEGER PRIMARY KEY, --AUTOINCREMENT, YA LO HACE AUTOMATICAMENTE
    usuario_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    almacen_id INTEGER NOT NULL,
	cantidad NUMERIC NOT NULL,
    tipo INTEGER NOT NULL,-- CHECK(tipo IN ('A','E', 'S')),
    fecha TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacion TEXT ,
    stock_anterior NUMERIC NOT NULL,
    stock_nuevo NUMERIC NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE RESTRICT,-- SET NULL,
  FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE RESTRICT,
  FOREIGN KEY (almacen_id) REFERENCES almacen (id) ON DELETE RESTRICT
);

CREATE INDEX idx_movimiento_producto ON movimiento_stock(producto_id);
CREATE INDEX idx_movimiento_almacen ON movimiento_stock(almacen_id);
CREATE INDEX idx_movimiento_fecha ON movimiento_stock(fecha);
-- CREATE INDEX idx_movimiento_producto_almacen_fecha ON movimiento_stock(producto_id, almacen_id, fecha);