CREATE TABLE stock_producto (
    id INTEGER PRIMARY KEY, --AUTOINCREMENT, YA LO HACE AUTOMATICAMENTE
    producto_id INTEGER NOT NULL,
    almacen_id INTEGER NOT NULL,
    cantidad NUMERIC NOT NULL,
    stock_minimo NUMERIC,
    stock_maximo NUMERIC,

    UNIQUE(producto_id, almacen_id), --El UNIQUE automáticamente crea un índice interno.

    FOREIGN KEY(producto_id) REFERENCES producto(id) ON DELETE RESTRICT,
    FOREIGN KEY(almacen_id) REFERENCES almacen(id) ON DELETE RESTRICT
);

CREATE INDEX idx_stock_almacen ON stock_producto(almacen_id);