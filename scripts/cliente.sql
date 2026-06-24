CREATE TABLE IF NOT EXISTS Cliente (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	codigo varchar(12) NOT NULL,
	nroDocumento varchar(15),
	nombre varchar(50) NOT NULL,
	telefono varchar(20) DEFAULT NULL,
	direccion varchar(120) DEFAULT NULL,
	fecha date NOT NULL
);
