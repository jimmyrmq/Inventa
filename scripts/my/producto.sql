CREATE TABLE IF NOT EXISTS Producto (
                                        ID BIGINT NOT NULL AUTO_INCREMENT,
                                        Codigo VARCHAR(20) NOT NULL UNIQUE,
    CodigoBarra VARCHAR(50) DEFAULT NULL,
    Nombre VARCHAR(50) NOT NULL,
    UnidadMedida VARCHAR(10) DEFAULT NULL,
    Modelo VARCHAR(20) DEFAULT NULL,
    Serie VARCHAR(20) DEFAULT NULL,

    MarcaID INT DEFAULT NULL,
    CategoriaID INT DEFAULT NULL,

    PrecioCosto DOUBLE NOT NULL,
    Utilidad INT DEFAULT 0,

    Precio1 DOUBLE NOT NULL,
    Precio2 DOUBLE NOT NULL,
    Precio3 DOUBLE NOT NULL,

    CantMayor INT DEFAULT NULL,

    PrecioIncluyeImpuesto TINYINT(1) NOT NULL DEFAULT 1,
    Disponible TINYINT(1) NOT NULL DEFAULT 1,

    CantidadDisponible INT DEFAULT NULL,
    StockCritico INT DEFAULT NULL,

    NoRequiereStock TINYINT(1) NOT NULL DEFAULT 0,
    ReqAprobPrecioEspecial TINYINT(1) NOT NULL DEFAULT 1,

    ProveedorID INT DEFAULT NULL,

    FechaActualizacion DATETIME DEFAULT NULL,
    FechaCreado DATETIME DEFAULT CURRENT_TIMESTAMP,

    Nota TEXT DEFAULT NULL,

    PRIMARY KEY (ID),

    FOREIGN KEY (CategoriaID) REFERENCES Categoria(ID) ON DELETE CASCADE,
    FOREIGN KEY (MarcaID) REFERENCES Marca(ID) ON DELETE CASCADE,
    FOREIGN KEY (ProveedorID) REFERENCES Proveedor(ID) ON DELETE CASCADE

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;