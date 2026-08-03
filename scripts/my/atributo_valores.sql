CREATE TABLE IF NOT EXISTS atributo_valores (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    atributo_id INT NOT NULL,
    valor VARCHAR(255) NOT NULL,

    UNIQUE KEY uk_atributo_valor (atributo_id, valor),

    CONSTRAINT fk_atributo_valores_atributo FOREIGN KEY (atributo_id) REFERENCES atributos(id) ON DELETE CASCADE
) ENGINE=InnoDB;
