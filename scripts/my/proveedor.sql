CREATE TABLE IF NOT EXISTS proveedor (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	codigo varchar(20) UNIQUE NOT NULL,
	nombre TEXT NOT NULL,
	direccion TEXT DEFAULT NULL,
	telefono1 varchar(20) DEFAULT NULL,
	telefono2 varchar(20) DEFAULT NULL,
	correo varchar(150) DEFAULT NULL,
	nombre_contacto TEXT DEFAULT NULL,
	nota TEXT NOT NULL,
	fecha_registro DATE NOT NULL
);

