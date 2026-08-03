-- Es la relacion del Producto con las variantes y sus atributos
CREATE TABLE IF NOT EXISTS variante_atributos (
      id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
      producto_variante_id INT NOT NULL,
      atributo_valor_id INT NOT NULL,

      UNIQUE KEY uk_variante_atributo_valor (producto_variante_id, atributo_valor_id),

    CONSTRAINT fk_variante_atributos_variante
    FOREIGN KEY (producto_variante_id)
    REFERENCES producto_variante(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_variante_atributos_valor
    FOREIGN KEY (atributo_valor_id)
    REFERENCES atributo_valores(id)
    ON DELETE CASCADE

    ) ENGINE=InnoDB;