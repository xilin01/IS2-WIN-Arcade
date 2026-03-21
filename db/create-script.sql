DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS paquete_juegos;
DROP TABLE IF EXISTS juego;
DROP TABLE IF EXISTS inventario;
DROP TABLE IF EXISTS jugador_premium;
DROP TABLE IF EXISTS jugador_estandar;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS usuario;


CREATE TABLE usuario (
    dni VARCHAR(15) NOT NULL UNIQUE,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenya VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    
    PRIMARY KEY(dni)
);

CREATE TABLE personal (
    dni VARCHAR(15) NOT NULL,

    FOREIGN KEY (dni)
		REFERENCES usuario (dni)
);

CREATE TABLE jugador (
    dni VARCHAR(15) NOT NULL,
    puntos INT DEFAULT 0 NOT NULL,
	FOREIGN KEY (dni)
		REFERENCES usuario (dni)
);

CREATE TABLE jugador_estandar (
    dni VARCHAR(15) NOT NULL,
    
    FOREIGN KEY (dni)
		REFERENCES usuario (dni)
);

CREATE TABLE jugador_premium (
    dni VARCHAR(15) NOT NULL,
    
    FOREIGN KEY (dni)
		REFERENCES jugador (dni)
);

CREATE TABLE inventario (
	dni_jugador VARCHAR(15) NOT NULL,
	lista_juegos JSON DEFAULT (JSON_OBJECT()) NOT NULL,
    lista_productos JSON DEFAULT (JSON_OBJECT()) NOT NULL,
    
    FOREIGN KEY (dni_jugador)
		REFERENCES jugador(dni)
);

CREATE TABLE juego (
	id INT NOT NULL,
    nombre VARCHAR(20)
);

CREATE TABLE paquete_juegos (
	id INT NOT NULL,
    precio DOUBLE NOT NULL,
    numero_juegos INT DEFAULT(0) NOT NULL,
    lista_juegos JSON DEFAULT (JSON_OBJECT()) NOT NULL
);

CREATE TABLE producto (
	id INT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    precio INT NOT NULL
);