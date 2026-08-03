CREATE TABLE IF NOT EXISTS stock_producto (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    variante_id INTEGER NOT NULL,
    almacen_id INTEGER NOT NULL,
    cantidad NUMERIC NOT NULL,
    stock_minimo NUMERIC NOT NULL,
    stock_maximo NUMERIC,

    UNIQUE(variante_id, almacen_id),--El UNIQUE automáticamente crea un índice interno.

    FOREIGN KEY (variante_id) REFERENCES producto_variante (id) ON DELETE RESTRICT,
    FOREIGN KEY (almacen_id) REFERENCES almacenes (id) ON DELETE RESTRICT
 );

CREATE INDEX idx_stock_almacen ON stock_producto(almacen_id);
CREATE INDEX idx_stock_variante ON stock_producto(variante_id);