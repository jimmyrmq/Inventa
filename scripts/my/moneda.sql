CREATE TABLE IF NOT EXISTS moneda (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    simbolo VARCHAR(20) NOT NULL,

    PRIMARY KEY (id),

    UNIQUE KEY uk_moneda_nombre (nombre),
    UNIQUE KEY uk_moneda_simbolo (simbolo)
) ENGINE=InnoDB;

INSERT INTO moneda (nombre, simbolo) VALUES
                                         ('Peso', '$'),
                                         ('Dólar', 'USD'),
                                         ('Euro', '€'),
                                         ('Bolivares', 'Bs');