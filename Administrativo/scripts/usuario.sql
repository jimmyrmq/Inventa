CREATE TABLE IF NOT EXISTS Usuario (
	ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	Nombre TEXT NOT NULL,
	Email TEXT,
	NivelAcceso INTEGER NOT NULL,
	Clave TEXT NOT NULL,
	Habilitado INT(1) NOT NULL
);

insert into Usuario (Nombre,NivelAcceso,Clave,Habilitado) values ('Principal',0,'',1);