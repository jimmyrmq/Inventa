CREATE TABLE IF NOT EXISTS producto_precio (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,

    variante_id INTEGER DEFAULT NULL,
    tipo_precio_id INTEGER NOT NULL,
    moneda_id INTEGER DEFAULT NULL,
    usuario_id INTEGER NOT NULL,

    valor NUMERIC NOT NULL,
    requiere_autorizacion INTEGER DEFAULT 0,

    fecha_inicio TEXT  DEFAULT NULL,
    fecha_fin TEXT DEFAULT NULL,

    disponible INTEGER NOT NULL DEFAULT 1,
    --UNIQUE (variante_id, tipo_precio_id, fecha_inicio),

    FOREIGN KEY (variante_id) REFERENCES producto_variante (id)  ON DELETE RESTRICT,
    FOREIGN KEY (tipo_precio_id) REFERENCES tipo_precio (id)  ON DELETE RESTRICT,
    FOREIGN KEY (moneda_id) REFERENCES moneda (id)  ON DELETE RESTRICT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)  ON DELETE RESTRICT
);