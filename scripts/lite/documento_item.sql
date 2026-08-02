CREATE TABLE IF NOT EXISTS documento_item (
	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
    documento_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad NUMERIC NOT NULL DEFAULT(0),
    precio_unitario NUMERIC,
    precio_neto NUMERIC,
    precio_total NUMERIC NULL DEFAULT(0),
    FOREIGN KEY (documento_id) REFERENCES documento (id)  ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto (id)  ON DELETE CASCADE
);