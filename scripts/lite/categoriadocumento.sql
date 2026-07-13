CREATE TABLE IF NOT EXISTS categoria_documento (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	nombre varchar(150) NOT NULL,
	lenguage_key TEXT
);

INSERT INTO categoria_documento (nombre,lenguage_key) VALUES
('Gastos','documento.compra'),
('Ventas','documento.venta'),
('Inventario','documento.rencuentoinventario'),
('Perdida','documento.perdida');