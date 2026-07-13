CREATE TABLE IF NOT EXISTS documento_item (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	documento_id INT NOT NULL,
	producto_id INT NOT NULL,
	cantidad DECIMAL(10,2) NOT NULL DEFAULT 0,
	precio_unitario DECIMAL(10,2),
	precio_neto DECIMAL(10,2),
	precio_total DECIMAL(10,2) DEFAULT 0,
	FOREIGN KEY (documento_id) REFERENCES documento (id) ON DELETE CASCADE,
	FOREIGN KEY (producto_id) REFERENCES producto (id) ON DELETE CASCADE
);

