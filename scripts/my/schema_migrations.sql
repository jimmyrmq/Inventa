CREATE TABLE IF NOT EXISTS schema_migrations (
     version INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
     filename    varchar(150) NOT NULL,
     applied_at  varchar(150) NOT NULL,
     checksum    varchar(150) NOT NULL
);
