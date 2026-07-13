CREATE TABLE IF NOT EXISTS usuario (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre TEXT NOT NULL,
	email TEXT,
	nivel_acceso INT NOT NULL,
	clave TEXT NOT NULL,
	habilitado TINYINT(1) NOT NULL
);

INSERT INTO usuario (nombre,nivel_acceso,clave,habilitado) VALUES ('Principal',0,'',1);

