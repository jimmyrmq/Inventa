CREATE TABLE IF NOT EXISTS tipo_documento (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre TEXT NOT NULL,
	codigo TEXT,
	categoria_documento_id INT NOT NULL,
	lenguage_key TEXT,
	FOREIGN KEY (categoria_documento_id) REFERENCES categoria_documento (id) ON DELETE RESTRICT
);

INSERT INTO tipo_documento (nombre,codigo,categoria_documento_id,lenguage_key) VALUES
	('Ventas','100',2,'documento.ventas'),
	('Gastos','200',1,'documento.gasto'),
	('Orden de Compra','220',1,'documento.ordencompra'),
	('Devulcion','240',1,'documento.conteoinventario'),
    ('Remito','400',3,'documento.remito'),
	('Conteo de Inventario','300',3,'documento.conteoinventario'),
	('Reembolso','120',2,'documento.reembolso'),
	('Perdida y daño','400',4,'documento.pedadia');

