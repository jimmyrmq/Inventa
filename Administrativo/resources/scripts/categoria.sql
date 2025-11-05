CREATE TABLE IF NOT EXISTS Categoria (
	ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
	Nombre varchar(150) NOT NULL,
	Color varchar(11)
);

INSERT INTO Categoria (Nombre,Color) values ("General","255 154 7");