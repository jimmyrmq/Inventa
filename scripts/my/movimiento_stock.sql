CREATE TABLE IF NOT EXISTS movimiento_stock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    usuario_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    almacen_id BIGINT NOT NULL,

    cantidad DECIMAL(18,3) NOT NULL,

    tipo_movimiento_id INT NOT NULL,

    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    observacion TEXT,

    stock_anterior DECIMAL(18,3) NOT NULL,
    stock_nuevo DECIMAL(18,3) NOT NULL,

    referencia_id INT,
    referencia_numero INT,

    CONSTRAINT fk_movimiento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE RESTRICT,
    CONSTRAINT fk_movimiento_producto FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE RESTRICT,
    CONSTRAINT fk_movimiento_almacen FOREIGN KEY (almacen_id) REFERENCES almacen(id) ON DELETE RESTRICT
    CONSTRAINT fk_movimiento_tipo_movimiento FOREIGN KEY (tipo_movimiento_id) REFERENCES tipo_movimiento(id) ON DELETE RESTRICT
    CONSTRAINT fk_movimiento_tipo_documento FOREIGN KEY (referencia_id) REFERENCES tipo_documento(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_movimiento_producto ON movimiento_stock(producto_id);
CREATE INDEX idx_movimiento_almacen ON movimiento_stock(almacen_id);
CREATE INDEX idx_movimiento_fecha ON movimiento_stock(fecha);
--CREATE INDEX idx_movimiento_producto_almacen_fecha ON movimiento_stock(producto_id, almacen_id, fecha);