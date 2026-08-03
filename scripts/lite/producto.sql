CREATE TABLE IF NOT EXISTS producto (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,

    codigo TEXT NOT NULL UNIQUE,
    nombre TEXT NOT NULL,

    modelo TEXT DEFAULT NULL,
    serie TEXT DEFAULT NULL,

    marca_id INTEGER DEFAULT NULL,
    categoria_id INTEGER DEFAULT NULL,

    cant_mayor INTEGER DEFAULT NULL,

    precio_incluye_impuesto INTEGER NOT NULL DEFAULT 1,
    disponible INTEGER NOT NULL DEFAULT 1,

    cantidad_disponible INTEGER DEFAULT NULL,

    movimiento_negativo INTEGER NOT NULL DEFAULT 0,
    no_requiere_stock INTEGER NOT NULL DEFAULT 0,
    req_aprobacion_precio_especial INTEGER NOT NULL DEFAULT 1,

    fecha_actualizacion TEXT  DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion TEXT DEFAULT CURRENT_TIMESTAMP,

    nota TEXT DEFAULT NULL,

    eliminado INTEGER NOT NULL DEFAULT 0,
    fecha_eliminacion TEXT DEFAULT NULL,
    usuario_eliminacion_id  INTEGER DEFAULT NULL,

    FOREIGN KEY (categoria_id) REFERENCES categoria (id) ON DELETE CASCADE,
    FOREIGN KEY (marca_id) REFERENCES marca (id) ON DELETE CASCADE
);