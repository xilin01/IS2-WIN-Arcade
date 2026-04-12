-- Limpieza
DROP TABLE IF EXISTS productsInventory; 
DROP TABLE IF EXISTS gamesInventory; 
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS packageDetail;
DROP TABLE IF EXISTS gamesPackage;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS user;

-- Tablas
CREATE TABLE user (
    name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    
    PRIMARY KEY (name)
);
CREATE TABLE staff (
    name VARCHAR(50) NOT NULL,

	PRIMARY KEY (name),
    FOREIGN KEY (name) REFERENCES user(name)
);
CREATE TABLE player (
    name VARCHAR(50) NOT NULL,
    points INT DEFAULT 0 NOT NULL,
    gamesPlayed INT DEFAULT 0 NOT NULL,
    
    PRIMARY KEY (name),
	FOREIGN KEY (name) REFERENCES user(name)
);
CREATE TABLE game (
    name VARCHAR(50) NOT NULL,
    basePoints INT NOT NULL,
    
    PRIMARY KEY (name, basePoints)
);
CREATE TABLE gamesPackage (
    name VARCHAR(50) NOT NULL,
    price DECIMAL(5, 2) NOT NULL,
    
    PRIMARY KEY (name)
);
CREATE TABLE packageDetail (
	packageName VARCHAR(50) NOT NULL,
    gameName VARCHAR(50) NOT NULL,
    gameBasePoints INT NOT NULL,
    amount INT NOT NULL,
    
    PRIMARY KEY (packageName, gameName, gameBasePoints),
    FOREIGN KEY (packageName) REFERENCES gamesPackage(name),
    FOREIGN KEY (gameName, gameBasePoints) REFERENCES game(name, basePoints)
);
CREATE TABLE product (
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    
    PRIMARY KEY (name)
);
CREATE TABLE gamesInventory (
    playerName VARCHAR(50) NOT NULL,
    gameName VARCHAR(50) NOT NULL,
    gameBasePoints INT NOT NULL,
    amount INT NOT NULL,
    
    PRIMARY KEY (playerName, gameName, gameBasePoints),
    FOREIGN KEY (playerName) REFERENCES player(name),
    FOREIGN KEY (gameName, gameBasePoints) REFERENCES game(name, basePoints)
);
CREATE TABLE productsInventory (
    playerName VARCHAR(50) NOT NULL,
    productName VARCHAR(50) NOT NULL,
    amount INT NOT NULL,
    
    PRIMARY KEY (playerName, productName),
    FOREIGN KEY (playerName) REFERENCES player(name),
    FOREIGN KEY (productName) REFERENCES product(name)
);

-- Inicialización
INSERT INTO user (name, password, active) VALUES
('Xiang', 'xiang', TRUE),
('MarioR', 'marior', TRUE),
('MarioG', 'mariog', TRUE),
('Diego', 'diego', TRUE),
('Luis', 'luis', TRUE),
('Adrian', 'adrian', TRUE),
('Jug1', 'jug1', TRUE),
('Jug2', 'Jug2', TRUE);
INSERT INTO staff (name) VALUES
('Xiang'),
('MarioR'),
('MarioG'),
('Diego'),
('Luis'),
('Adrian');
INSERT INTO player (name, points, gamesPlayed) VALUES
('Jug1', 0, 0),
('Jug2', 1000, 100);
INSERT INTO game (name, basePoints) VALUES
('Ruleta', 10),
('Ruleta', 20),
('Blackjack', 10),
('Blackjack', 20);
INSERT INTO gamesPackage (name, price) VALUES
('Paq0', 9.99),
('Paq1', 29.99),
('Paq2', 49.99);
INSERT INTO packageDetail (packageName, gameName, gameBasePoints, amount) VALUES
('Paq0', 'Ruleta', 10, 1),
('Paq0', 'Blackjack', 10, 1),
('Paq1', 'Ruleta', 10, 2),
('Paq1', 'Ruleta', 20, 1),
('Paq1', 'Blackjack', 10, 2),
('Paq1', 'Blackjack', 20, 1),
('Paq2', 'Ruleta', 10, 3),
('Paq2', 'Ruleta', 20, 3),
('Paq2', 'Blackjack', 10, 3),
('Paq2', 'Blackjack', 20, 3);
INSERT INTO product (name, price) VALUES
('Camiseta', 100),
('Taza', 50),
('Pin', 10);
INSERT INTO gamesInventory (playerName, gameName, gameBasePoints, amount) VALUES
('Jug1', 'Ruleta', 10, 10),
('Jug1', 'Ruleta', 20, 10),
('Jug1', 'Blackjack', 10, 10),
('Jug1', 'Blackjack', 20, 10);
INSERT INTO productsInventory (playerName, productName, amount) VALUES
('Jug1', 'Camiseta', 2),
('Jug1', 'Taza', 1);

-- Visualización
SELECT * FROM user;
SELECT * FROM staff;
SELECT * FROM player;
SELECT * FROM game;
SELECT * FROM gamesPackage;
SELECT * FROM packageDetail;
SELECT * FROM product;
SELECT * FROM gamesInventory;
SELECT * FROM productsInventory;
