CREATE TABLE IF NOT EXISTS tipo_precio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
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