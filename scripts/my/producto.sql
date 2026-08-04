CREATE TABLE IF NOT EXISTS producto (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,

    codigo VARCHAR(100) NOT NULL,
    nombre VARCHAR(255) NOT NULL,

    modelo VARCHAR(255) DEFAULT NULL,

    marca_id INT DEFAULT NULL,
    categoria_id INT DEFAULT NULL,
    unidad_medida_id INT DEFAULT NULL,

    precio_incluye_impuesto TINYINT(1) NOT NULL DEFAULT 1,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    movimiento_negativo TINYINT(1) NOT NULL DEFAULT 0,
    no_requiere_stock TINYINT(1) NOT NULL DEFAULT 0,

    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    nota TEXT DEFAULT NULL,

    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    fecha_eliminacion DATETIME DEFAULT NULL,
    usuario_eliminacion_id INT DEFAULT NULL,

    UNIQUE KEY uk_producto_codigo (codigo),

    CONSTRAINT fk_producto_categoria
    FOREIGN KEY (categoria_id)
    REFERENCES categoria(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_producto_marca
    FOREIGN KEY (marca_id)
    REFERENCES marca(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_producto_unidad_medida
    FOREIGN KEY (unidad_medida_id)
    REFERENCES unidad_medida(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;