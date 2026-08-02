CREATE TABLE IF NOT EXISTS categoria_documento (
       id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
       nombre TEXT NOT NULL,
       codigo TEXT,
       lenguage_key TEXT
);

INSERT INTO categoria_documento (id, nombre, codigo, lenguage_key) VALUES
    (1, 'Egresos', '100', 'categoria.egresos'),
    (2, 'Ingresos', '200', 'categoria.ingresos'),
    (3, 'Inventario', '300', 'categoria.inventario'),
    (4, 'Ajustes de Inventario', '400', 'categoria.ajustes');