CREATE TABLE IF NOT EXISTS atributo_valores (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    atributo_id INTEGER NOT NULL,
    valor TEXT NOT NULL,

    UNIQUE(atributo_id, valor), --La combinación de atributo y valor debe ser única.

    FOREIGN KEY (atributo_id) REFERENCES atributos (id) ON DELETE CASCADE
);