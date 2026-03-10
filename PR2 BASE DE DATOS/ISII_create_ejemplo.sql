DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
	id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contrasenya VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS personal;
CREATE TABLE personal (
	id INT NOT NULL,

    FOREIGN KEY (id)
		REFERENCES usuario (id)
);

DROP TABLE IF EXISTS jugador;
CREATE TABLE jugador (
	id INT NOT NULL,
    puntos INT DEFAULT 0 NOT NULL,
	FOREIGN KEY (id)
		REFERENCES usuario (id)
);

DROP TABLE IF EXISTS jugador_estandar;
CREATE TABLE jugador_estandar (
	id INT NOT NULL,
    
    FOREIGN KEY (id)
		REFERENCES usuario (id)
);

DROP TABLE IF EXISTS jugador_premium;
CREATE TABLE jugador_premium (
	id INT NOT NULL,
    
    FOREIGN KEY (id)
		REFERENCES jugador (id)
);

DROP TABLE IF EXISTS inventario;
CREATE TABLE inventario (
	id_jugador INT NOT NULL,
	lista_juegos JSON DEFAULT (JSON_OBJECT()) NOT NULL,
    lista_productos JSON DEFAULT (JSON_OBJECT()) NOT NULL,
    
    FOREIGN KEY (id_jugador)
		REFERENCES jugador(id)
);

DROP TABLE IF EXISTS juego;
CREATE TABLE juego (
	id INT NOT NULL,
    nombre VARCHAR(20)
);

DROP TABLE IF EXISTS paquete_juegos;
CREATE TABLE paquete_juegos (
	id INT NOT NULL,
    precio DOUBLE NOT NULL,
    numero_juegos INT DEFAULT(0) NOT NULL,
    lista_juegos JSON DEFAULT (JSON_OBJECT()) NOT NULL
);

DROP TABLE IF EXISTS producto;
CREATE TABLE producto (
	id INT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    precio INT NOT NULL
);

DROP TABLE IF EXISTS tienda;
CREATE TABLE tienda (
    lista_juegos JSON DEFAULT (JSON_OBJECT()) NOT NULL,
    numero_juegos INT DEFAULT 0 NOT  NULL
);


