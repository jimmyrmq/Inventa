CREATE TABLE IF NOT EXISTS producto_variante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    sku VARCHAR(255) DEFAULT NULL,
    codigo_barra VARCHAR(255) DEFAULT NULL,
    imagen VARCHAR(255) DEFAULT NULL,
    disponible TINYINT NOT NULL DEFAULT 1,
    costo_base DECIMAL(10,2) NOT NULL,
    precio_incluye_impuesto TINYINT NOT NULL DEFAULT 1,
    cant_mayor INT DEFAULT NULL,

    CONSTRAINT fk_producto_variante_producto
    FOREIGN KEY (producto_id)
    REFERENCES producto (id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_producto_variante_codbar
    ON producto_variante(codigo_barra);

CREATE INDEX idx_producto_variante_sku
    ON producto_variante(sku);