CREATE TABLE IF NOT EXISTS producto_variante (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    producto_id INT NOT NULL,

    sku VARCHAR(100) DEFAULT NULL,
    codigo_barra VARCHAR(100) DEFAULT NULL,
    imagen TEXT DEFAULT NULL,

    disponible TINYINT NOT NULL DEFAULT 1,

    CONSTRAINT fk_producto_variante_producto FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_producto_variante_codbar ON producto_variante(codigo_barra);

CREATE INDEX idx_producto_variante_sku ON producto_variante(sku);