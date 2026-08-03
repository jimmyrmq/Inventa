CREATE TABLE IF NOT EXISTS unidad_medida (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    abreviacion VARCHAR(20) NOT NULL,


    UNIQUE KEY uk_unidad_nombre (nombre),
    UNIQUE KEY uk_unidad_abreviacion (abreviacion)
) ENGINE=InnoDB;