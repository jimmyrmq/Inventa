CREATE TABLE IF NOT EXISTS stock_producto (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    variante_id INT NOT NULL,
    almacen_id INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    stock_minimo DECIMAL(10,2) NOT NULL,
    stock_maximo DECIMAL(10,2) DEFAULT NULL,

    UNIQUE KEY uk_stock_variante_almacen (variante_id, almacen_id),

    -- ERROR ORIGINAL: producto_id no existe en esta tabla
    -- UNIQUE KEY uk_stock_producto_almacen (producto_id, almacen_id),

    CONSTRAINT fk_stock_producto_variante FOREIGN KEY (variante_id) REFERENCES producto_variante(id) ON DELETE RESTRICT,
    CONSTRAINT fk_stock_producto_almacen FOREIGN KEY (almacen_id) REFERENCES almacenes(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_stock_almacen ON stock_producto(almacen_id);
CREATE INDEX idx_stock_variante ON stock_producto(variante_id);