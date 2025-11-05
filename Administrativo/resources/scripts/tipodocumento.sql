CREATE TABLE IF NOT EXISTS TipoDocumento (
	ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	Nombre TEXT NOT NULL,
	Codigo TEXT,
	CategoriaDocumentoID INTEGER NOT NULL,
	AlmacenID INTEGER NOT NULL,
	LenguageKey TEXT,
	FOREIGN KEY (CategoriaDocumentoID) REFERENCES CategoriaDocumento (ID)  ON DELETE CASCADE,
	FOREIGN KEY (AlmacenID) REFERENCES Almacen (ID)  ON DELETE CASCADE
);

INSERT INTO TipoDocumento (Nombre,Codigo,CategoriaDocumentoID,AlmacenID,LenguageKey)  VALUES
    ('Ventas','100',2,1,'documento.ventas'),
    ('Gastos','200',1,1,'documento.gasto'),
    ('Orden de Compra','220',1,1,'documento.ordencompra'),
    ('Devulcion','240',1,1,'documento.conteoinventario'),
    ('Conteo de Inventario','300',3,1,'documento.conteoinventario'),
    ('Reembolso','120',2,1,'documento.reembolso'),
    ('Perdida y daño','400',4,1,'documento.pedadia');