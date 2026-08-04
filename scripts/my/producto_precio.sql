CREATE TABLE IF NOT EXISTS producto_precio (
    id INT AUTO_INCREMENT PRIMARY KEY,

    variante_id INT DEFAULT NULL,
    tipo_precio_id INT NOT NULL,
    moneda_id INT DEFAULT NULL,
    usuario_id INT NOT NULL,

    valor DECIMAL(15,2) NOT NULL,
    requiere_autorizacion TINYINT(1) NOT NULL DEFAULT 0,

    fecha_inicio DATETIME DEFAULT NULL,
    fecha_fin DATETIME DEFAULT NULL,

    UNIQUE KEY uq_precio_variante_tipo_fecha (variante_id, tipo_precio_id, fecha_inicio),

    CONSTRAINT fk_producto_precio_variante
    FOREIGN KEY (variante_id)
    REFERENCES producto_variante(id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_producto_precio_tipo_precio
    FOREIGN KEY (tipo_precio_id)
    REFERENCES tipo_precio(id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_producto_precio_moneda
    FOREIGN KEY (moneda_id)
    REFERENCES moneda(id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_producto_precio_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES usuarios(id)
    ON DELETE RESTRICT
) ENGINE=InnoDB;