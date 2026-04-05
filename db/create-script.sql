-- Limpieza
DROP TABLE IF EXISTS inventario_producto; 
DROP TABLE IF EXISTS inventario_juego; 
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS paquete_detalle;
DROP TABLE IF EXISTS paquete_juegos;
DROP TABLE IF EXISTS juego;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS usuario;

-- Tablas
CREATE TABLE usuario (
    dni VARCHAR(9) NOT NULL,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    
    PRIMARY KEY (dni)
);
CREATE TABLE personal (
    dni VARCHAR(9) NOT NULL,

	PRIMARY KEY (dni),
    FOREIGN KEY (dni) REFERENCES usuario(dni)
);
CREATE TABLE jugador (
    dni VARCHAR(9) NOT NULL,
    puntos INT DEFAULT 0 NOT NULL,
    partidas_jugadas INT DEFAULT 0 NOT NULL,
    
    PRIMARY KEY (dni),
	FOREIGN KEY (dni) REFERENCES usuario(dni)
);
CREATE TABLE juego (
	id INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    
    PRIMARY KEY (id)
);
CREATE TABLE paquete_juegos (
    id INT NOT NULL,
    precio DOUBLE NOT NULL,
    
    PRIMARY KEY (id)
);
CREATE TABLE paquete_detalle (
    id_paquete INT NOT NULL,
    id_juego INT NOT NULL,
    puntos INT NOT NULL,
    cantidad INT NOT NULL,
    
    PRIMARY KEY (id_paquete, id_juego),
    FOREIGN KEY (id_paquete) REFERENCES paquete_juegos(id),
    FOREIGN KEY (id_juego) REFERENCES juego(id)
);
CREATE TABLE producto (
	id INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    precio INT NOT NULL,
    
    PRIMARY KEY (id)
);
CREATE TABLE inventario_juego (
    dni_jugador VARCHAR(9) NOT NULL,
    id_juego INT NOT NULL,
    puntos INT NOT NULL,
    cantidad INT NOT NULL,
    
    PRIMARY KEY (dni_jugador, id_juego),
    FOREIGN KEY (dni_jugador) REFERENCES jugador(dni),
    FOREIGN KEY (id_juego) REFERENCES juego(id)
);
CREATE TABLE inventario_producto (
    dni_jugador VARCHAR(9) NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    
    PRIMARY KEY (dni_jugador, id_producto),
    FOREIGN KEY (dni_jugador) REFERENCES jugador(dni),
    FOREIGN KEY (id_producto) REFERENCES producto(id)
);

-- Inicialización
INSERT INTO usuario (dni, nombre, contrasena, activo) VALUES
('11111111A', 'Xiang', 'xiang', TRUE),
('22222222B', 'MarioR', 'marior', TRUE),
('33333333C', 'MarioG', 'mariog', TRUE),
('44444444D', 'Diego', 'diego', TRUE),
('55555555E', 'Luis', 'luis', TRUE),
('66666666F', 'Adrian', 'adrian', TRUE),
('00000000A', 'Jug1', 'jug1', TRUE),
('00000001A', 'Jug2', 'Jug2', TRUE);
INSERT INTO personal (dni) VALUES
('11111111A'),
('22222222B'),
('33333333C'),
('44444444D'),
('55555555E'),
('66666666F');
INSERT INTO jugador (dni, puntos, partidas_jugadas) VALUES
('00000000A', 0, 0),
('00000001A', 1000, 100);
INSERT INTO juego (id, nombre) VALUES
(0, 'Ruleta'),
(1, 'Blackjack'); 
INSERT INTO paquete_juegos (id, precio) VALUES
(0, 19.99),
(1, 49.99);
INSERT INTO paquete_detalle (id_paquete, id_juego, puntos, cantidad) VALUES
(0, 0, 100, 1),
(1, 0, 150, 1),
(1, 1, 500, 2);
INSERT INTO producto (id, nombre, precio) VALUES
(0, 'Camiseta', 20),
(1, 'Taza', 10),
(2, 'Pin', 5);
INSERT INTO inventario_juego (dni_jugador, id_juego, puntos, cantidad) VALUES
('00000001A', 0, 100, 10),
('00000001A', 1, 500, 10);
INSERT INTO inventario_producto (dni_jugador, id_producto, cantidad) VALUES
('00000001A', 0, 1),
('00000001A', 1, 1);

-- Visualización
SELECT * FROM usuario;
SELECT * FROM personal;
SELECT * FROM jugador;
SELECT * FROM juego;
SELECT * FROM paquete_juegos;
SELECT * FROM paquete_detalle;
SELECT * FROM producto;
SELECT * FROM inventario_juego;
SELECT * FROM inventario_producto;
