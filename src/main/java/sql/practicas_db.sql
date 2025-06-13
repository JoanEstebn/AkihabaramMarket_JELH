/**
 * La base de datos que nos hará falta para poder almacenar todos los datos tanto de clientes como de productos.
 * Autor: Joan Esteban Londoño Hernández
 * Versión: 1.0.0.0 - 13/06/2025
 */

DROP DATABASE IF EXISTS AkihabaraDB_db_JELH;
CREATE DATABASE AkihabaraDB_db_JELH;

USE AkihabaraDB_db_JELH;

CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10,2),
    stock INT
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Inserción de 5 productos de ejemplo
INSERT INTO productos (nombre, categoria, precio, stock) VALUES
('Figura de Anya Forger - SPYxFAMILY', 'Figura', 59.95, 8),
('Manga Chainsaw Man Vol.1', 'Manga', 9.99, 20),
('Póster Studio Ghibli - Colección Clásica', 'Póster', 15.50, 15),
('Llavero Tanjiro Kamado - Demon Slayer', 'Llavero', 4.75, 50),
('Camiseta Naruto Uzumaki - Talla M', 'Ropa', 19.99, 30);
