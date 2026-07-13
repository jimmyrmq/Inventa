CREATE TABLE IF NOT EXISTS usuario (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	nombre TEXT NOT NULL,
	email TEXT,
	nivel_acceso INTEGER NOT NULL,
	clave TEXT NOT NULL,
	habilitado INT(1) NOT NULL
);

insert into usuario (nombre,nivel_acceso,clave,habilitado) values ('Principal',0,'',1);
