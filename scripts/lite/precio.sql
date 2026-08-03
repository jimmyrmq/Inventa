CREATE TABLE IF NOT EXISTS precio (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,

    variante_id INTEGER DEFAULT NULL,
    tipo_precio_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,

    valor NUMERIC NOT NULL,

    fecha_inicio TEXT  DEFAULT NULL,
    fecha_fin TEXT DEFAULT NULL,

    UNIQUE (variante_id, tipo_precio_id, fecha_inicio),

    FOREIGN KEY (variante_id) REFERENCES producto_variante (id)  ON DELETE RESTRICT,
    FOREIGN KEY (tipo_precio_id) REFERENCES tipo_precio (id)  ON DELETE RESTRICT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)  ON DELETE RESTRICT
);