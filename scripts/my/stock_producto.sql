CREATE TABLE IF NOT EXISTS stock_producto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    producto_id BIGINT NOT NULL,
    almacen_id BIGINT NOT NULL,

    cantidad DECIMAL(18,3) NOT NULL,

    stock_minimo DECIMAL(18,3),
    stock_maximo DECIMAL(18,3),

    CONSTRAINT uk_stock_producto_almacen UNIQUE(producto_id, almacen_id), --Se crea un índice automáticamente.

    CONSTRAINT fk_stock_producto_producto FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE RESTRICT,

    CONSTRAINT fk_stock_producto_almacen FOREIGN KEY (almacen_id) REFERENCES almacen(id) ON DELETE RESTRICT

    ) ENGINE=InnoDB;

CREATE INDEX idx_stock_almacen ON stock_producto(almacen_id);