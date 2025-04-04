CREATE DATABASE Ahorcado;
USE  Aorcado;
CREATE TABLE Usuario (
	Numero INT PRIMARY KEY,
	Nombre VARCHAR(50) NOT NULL,
	Apellido1 VARCHAR(50) NOT NULL,
	Apellido2 VARCHAR(50) NOT NULL
);
CREATE TABLE Admin (
	Numero INT PRIMARY KEY,
	Nivel INT NOT NULL,
	FOREIGN KEY (Numero) REFERENCES Usuario(Numero)
);
CREATE TABLE Jugador (
	Numero INT PRIMARY KEY,
	Cuota DECIMAL(10,2) NOT NULL,
	FOREIGN KEY (Numero) REFERENCES Usuario(Numero)
);
CREATE TABLE Juegos (
	idJuego INT PRIMARY KEY AUTO_INCREMENT,
	Idioma VARCHAR(10) NOT NULL,
	horaSistema TIME NOT NULL,
	fechaSistema DATE NOT NULL
);
CREATE TABLE PalabraFrase (
	idPartida INT PRIMARY KEY AUTO_INCREMENT,
	Idioma VARCHAR(10) NOT NULL,
	Nombre VARCHAR(255) NOT NULL
);
CREATE TABLE Partida (
	Numero INT,
	idJuego INT,
	idPalabra INT,
	registroPuntaje INT NOT NULL,
	duracionPartida TIME NOT NULL,
	clasificacionesFinales TEXT NOT NULL,
	PRIMARY KEY (Numero, idJuego, idPalabra),
	FOREIGN KEY (Numero) REFERENCES Jugador(Numero),
	FOREIGN KEY (idJuego) REFERENCES Juegos(idJuego),
	FOREIGN KEY (idPalabra) REFERENCES PalabraFrase(idPartida)
);
CREATE TABLE Historico (
	idPalabra INT PRIMARY KEY AUTO_INCREMENT,
	Idioma VARCHAR(10) NOT NULL,
	nombre VARCHAR(255) NOT NULL,
	PalabraFrase TEXT NOT NULL
);
CREATE TABLE Sinonimo (
	idSinonimo INT PRIMARY KEY AUTO_INCREMENT,
	idPalabra INT NOT NULL,
	FOREIGN KEY (idPalabra) REFERENCES Historico(idPalabra)
);
