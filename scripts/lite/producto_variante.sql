CREATE TABLE IF NOT EXISTS producto_variante (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    producto_id INTEGER NOT NULL,
    sku TEXT DEFAULT NULL,
    codigo_barra TEXT DEFAULT NULL,
    imagen TEXT DEFAULT NULL,
    disponible INTEGER NOT NULL DEFAULT 1,
    costo_base REAL NOT NULL,
    precio_incluye_impuesto INTEGER NOT NULL DEFAULT 1,
    cant_mayor INTEGER DEFAULT NULL,

    FOREIGN KEY (producto_id) REFERENCES producto (id)  ON DELETE CASCADE
);

CREATE INDEX idx_producto_variante_codbar ON producto_variante(codigo_barra);
CREATE INDEX idx_producto_variante_sku ON producto_variante(sku);
