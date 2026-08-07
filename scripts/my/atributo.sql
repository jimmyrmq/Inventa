CREATE TABLE IF NOT EXISTS atributo (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(150) NOT NULL
) ENGINE=InnoDB;


INSERT INTO atributo (nombre) VALUES
                                  ('Talla'),
                                  ('Volumen'),
                                  ('Peso'),
                                  ('Color'),
                                  ('Modelo'),
                                  ('Presentación'),
                                  ('Capacidad'),
                                  ('Tamaño'),
                                  ('Género'),
                                  ('Material'),
                                  ('Dimensiones'),
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