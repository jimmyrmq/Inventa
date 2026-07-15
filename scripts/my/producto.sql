CREATE TABLE IF NOT EXISTS producto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    codigo_barra VARCHAR(50) DEFAULT NULL,
    nombre VARCHAR(50) NOT NULL,
    unidad_medida VARCHAR(10) DEFAULT NULL,
    modelo VARCHAR(20) DEFAULT NULL,
    serie VARCHAR(20) DEFAULT NULL,
    marca_id INT DEFAULT NULL,
    categoria_id INT DEFAULT NULL,
    precio_costo DOUBLE NOT NULL,
    utilidad INT DEFAULT 0,
    precio1 DOUBLE NOT NULL,
    precio2 DOUBLE NOT NULL,
    precio3 DOUBLE NOT NULL,
    cant_mayor INT DEFAULT NULL,
    precio_incluye_impuesto TINYINT(1) NOT NULL DEFAULT 1,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    cantidad_disponible INT DEFAULT NULL,
    stock_critico INT DEFAULT NULL,
    no_requiere_stock TINYINT(1) NOT NULL DEFAULT 0,
    req_aprobacion_precio_especial TINYINT(1) NOT NULL DEFAULT 1,
    movimiento_negativo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    nota TEXT DEFAULT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE,
    FOREIGN KEY (marca_id) REFERENCES marca(id) ON DELETE CASCADE

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;