CREATE TABLE IF NOT EXISTS precio (
    id INT AUTO_INCREMENT PRIMARY KEY,

    variante_id INT DEFAULT NULL,
    tipo_precio_id INT NOT NULL,
    usuario_id INT NOT NULL,

    valor DECIMAL(10,2) NOT NULL,

    fecha_inicio DATETIME DEFAULT NULL,
    fecha_fin DATETIME DEFAULT NULL,

    UNIQUE KEY uq_precio_variante_tipo_fecha (variante_id, tipo_precio_id, fecha_inicio),

    CONSTRAINT fk_precio_variante
    FOREIGN KEY (variante_id) REFERENCES producto_variante (id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_precio_tipo
    FOREIGN KEY (tipo_precio_id) REFERENCES tipo_precio (id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_precio_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
    ON DELETE RESTRICT
);