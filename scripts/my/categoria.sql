CREATE TABLE IF NOT EXISTS categoria (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre varchar(150) NOT NULL,
	color varchar(11)
);

INSERT INTO categoria (nombre,color) values ("General","255 154 7");

