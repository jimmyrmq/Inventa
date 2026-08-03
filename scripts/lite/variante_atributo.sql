-- Es la relacion del Producto con las variantes y sus atributos
CREATE TABLE IF NOT EXISTS variante_atributo (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    producto_variante_id INTEGER NOT NULL,
    atributo_valor_id INTEGER NOT NULL,

    UNIQUE(producto_variante_id, atributo_valor_id), --La combinación de variante y valor debe ser única.

    FOREIGN KEY (producto_variante_id) REFERENCES producto_variante (id) ON DELETE CASCADE,
    FOREIGN KEY (atributo_valor_id) REFERENCES atributo_valores (id) ON DELETE CASCADE
);