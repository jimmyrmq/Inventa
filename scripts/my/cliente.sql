CREATE TABLE IF NOT EXISTS cliente (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	codigo varchar(12) NOT NULL,
	nro_documento varchar(15),
	nombre varchar(50) NOT NULL,
	telefono varchar(20) DEFAULT NULL,
	direccion varchar(120) DEFAULT NULL,
	fecha date NOT NULL
);

