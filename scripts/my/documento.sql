CREATE TABLE IF NOT EXISTS documento (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	numero TEXT NOT NULL,
	usuario_id INT NOT NULL,
	cliente_id INT,
	fecha_emision TEXT NOT NULL,
	fecha_vencimiento TEXT,
	fecha_actualizacion TEXT,
	tipo_documento_id INT NOT NULL,
	almacen_id INT NOT NULL,
	nota TEXT,
	estado INT NOT NULL,
	impuesto DECIMAL(10,2),
	sub_total DECIMAL(10,2),
	total DECIMAL(10,2),
	proveedor_id INT,
	FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
	FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE,
	FOREIGN KEY (proveedor_id) REFERENCES proveedor (id) ON DELETE CASCADE,
	FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (id) ON DELETE CASCADE,
	FOREIGN KEY (almacen_id) REFERENCES almacen (id) ON DELETE CASCADE
);

