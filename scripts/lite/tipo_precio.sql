CREATE TABLE IF NOT EXISTS tipo_precio (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    nombre TEXT NOT NULL UNIQUE
);

INSERT INTO tipo_precio (nombre) VALUES
                     ('Venta'),
                     ('Mayorista'),
                     ('Distribuidor');
                     -- ('Especial'),
                     -- ('Promoción'),
                     -- ('Descuento'),
                     -- ('Liquidación'),
                     -- ('Oferta')