CREATE TABLE IF NOT EXISTS CategoriaDocumento (
	ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	Nombre varchar(150) NOT NULL,
	LenguageKey TEXT
);

INSERT INTO CategoriaDocumento (Nombre,LenguageKey) VALUES
('Gastos','documento.compra'),
('Ventas','documento.venta'),
('Inventario','documento.rencuentoinventario'),
('Perdida','documento.perdida');