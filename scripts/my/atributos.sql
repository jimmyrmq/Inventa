CREATE TABLE IF NOT EXISTS atributos (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(150) NOT NULL
) ENGINE=InnoDB;


INSERT INTO atributos (nombre) VALUES
                                   ('Talla'),
                                   ('Color'),
                                   ('Modelo'),
                                   ('Presentación'),
                                   ('Tamaño'),
                                   ('Peso'),
                                   ('Género'),
                                   ('Capacidad'),
                                   ('Material'),
                                   ('Dimensiones'),
                                   ('Volumen'),
                                   ('Sabor'),
                                   ('Fragancia'),
                                   ('Número de Serie'),
                                   ('Compatibilidad'),
                                   ('Voltaje'),
                                   ('Potencia'),
                                   ('Memoria'),
                                   ('Almacenamiento'),
                                   ('Procesador'),
                                   ('Sistema Operativo');