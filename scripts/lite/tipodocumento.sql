CREATE TABLE IF NOT EXISTS tipo_documento (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	nombre TEXT NOT NULL,
	codigo TEXT,
	categoria_documento_id INTEGER NOT NULL,
	almacen_id INTEGER NOT NULL,
	lenguage_key TEXT,
	FOREIGN KEY (categoria_documento_id) REFERENCES categoria_documento (id)  ON DELETE CASCADE,
	FOREIGN KEY (almacen_id) REFERENCES almacen (id)  ON DELETE CASCADE
);

INSERT INTO tipo_documento (nombre,codigo,categoria_documento_id,almacen_id,lenguage_key)  VALUES
    ('Ventas','100',2,1,'documento.ventas'),
    ('Gastos','200',1,1,'documento.gasto'),
    ('Orden de Compra','220',1,1,'documento.ordencompra'),
    ('Devulcion','240',1,1,'documento.conteoinventario'),
    ('Conteo de Inventario','300',3,1,'documento.conteoinventario'),
    ('Reembolso','120',2,1,'documento.reembolso'),
    ('Perdida y daño','400',4,1,'documento.pedadia');