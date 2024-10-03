CREATE TABLE IF NOT EXISTS Proveedor (
  ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
  Codigo varchar(20) UNIQUE NOT NULL,
  Nombre TEXT  NOT NULL,
  Direccion TEXT DEFAULT NULL,
  Telefono1 varchar(20) DEFAULT NULL,
  Telefono2 varchar(20) DEFAULT NULL,
  Correo varchar(150) DEFAULT NULL,
  NombreContacto TEXT DEFAULT NULL,
  Nota TEXT NOT NULL,
  FechaRegistro DATE NOT NULL
);