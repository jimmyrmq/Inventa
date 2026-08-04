CREATE TABLE IF NOT EXISTS moneda (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    nombre TEXT NOT NULL UNIQUE,
    simbolo TEXT NOT NULL UNIQUE
);


INSERT INTO moneda (nombre, simbolo) VALUES
                                            ('Peso', '$'),
                                            ('Dólar', 'USD'),
                                            ('Euro', '€'),
                                            ('Bolivares', 'Bs');