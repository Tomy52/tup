CREATE TABLE Equipos (
                         EquipoID INT PRIMARY KEY AUTO_INCREMENT,
                         NombreEquipo VARCHAR(100),
                         Ciudad VARCHAR(100),
                         FechaFundacion DATE
);
INSERT INTO Equipos (NombreEquipo, Ciudad, FechaFundacion)
VALUES
    ('Tigres FC', 'Buenos Aires', '1985-03-21'),
    ('Águilas Doradas', 'Mendoza', '1990-07-15'),
    ('Leones Negros', 'Cordoba', '1978-11-05');
CREATE TABLE Jugadores (
                           JugadorID INT PRIMARY KEY AUTO_INCREMENT,
                           Nombre VARCHAR(100),
                           Apellido VARCHAR(100),
                           FechaNacimiento DATE,
                           EquipoID INT,
                           FOREIGN KEY (EquipoID) REFERENCES Equipos(EquipoID)
);
INSERT INTO Jugadores (Nombre, Apellido, FechaNacimiento, EquipoID)
VALUES
    ('Lucas', 'González', '1995-04-12', 1),
    ('Matías', 'Rodríguez', '1993-09-23', 2),
    ('Diego', 'Fernández', '1990-01-15', 3);
CREATE TABLE Torneos (
                         TorneoID INT PRIMARY KEY AUTO_INCREMENT,
                         NombreTorneo VARCHAR(100),
                         FechaInicio DATE,
                         FechaFin DATE,
                         Ubicacion VARCHAR(100)
);
INSERT INTO Torneos (NombreTorneo, FechaInicio, FechaFin, Ubicacion)
VALUES
    ('Torneo Apertura', '2025-04-01', '2025-04-30', 'Buenos Aires'),
    ('Torneo Clausura', '2025-05-01', '2025-05-31', 'Cordoba');
CREATE TABLE Partidos (
                          PartidoID INT PRIMARY KEY AUTO_INCREMENT,
                          TorneoID INT,
                          EquipoLocalID INT,
                          EquipoVisitanteID INT,
                          FechaPartido DATE,
                          Resultado VARCHAR(50),
                          FOREIGN KEY (TorneoID) REFERENCES Torneos(TorneoID),
                          FOREIGN KEY (EquipoLocalID) REFERENCES Equipos(EquipoID),
                          FOREIGN KEY (EquipoVisitanteID) REFERENCES Equipos(EquipoID)
);
INSERT INTO Partidos (TorneoID, EquipoLocalID, EquipoVisitanteID, FechaPartido,
                      Resultado)
VALUES
    (1, 1, 2, '2025-04-10', '2-1'),
    (1, 2, 3, '2025-04-15', '0-0');

CREATE TABLE EstadisticasJugador(
                                    EstadisticaID INT PRIMARY KEY AUTO_INCREMENT,
                                    JugadorID INT,
                                    PartidoID INT,
                                    Goles INT,
                                    Asistencias INT,
                                    TarjetasAmarillas INT,
                                    TarjetasRojas INT,
                                    FOREIGN KEY (JugadorID) REFERENCES Jugadores(JugadorID),
                                    FOREIGN KEY (PartidoID) REFERENCES Partidos(PartidoID)
);

-- Ej 1

# Registrar un nuevo partido: procedimiento almacenado llamado registrarPartido.
# Este procedimiento debe:
# o Recibir como parámetros los IDs de los equipos (local y visitante), el ID del
# torneo y la fecha del partido.
# o Verificar que los IDs de los equipos y del torneo existan.
# o Registrar un nuevo partido en la tabla Partidos.
# o Manejar errores mediante transacciones (ROLLBACK en caso de IDs
# inexistentes o errores de SQL).
# o Devolver un mensaje indicando el éxito o el error de la operación.

DELIMITER //

CREATE PROCEDURE verificarExistenciaEquipo(IN EquipoID_buscar INT)
BEGIN
    DECLARE encontrado INT DEFAULT 0;

    SELECT COUNT(EquipoID) INTO encontrado
    FROM Equipos
    WHERE EquipoID_buscar = EquipoID
    GROUP BY EquipoID;

    IF (encontrado = 0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El equipo no existe';
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarExistenciaTorneo(IN TorneoID_buscar INT)
BEGIN
    DECLARE encontrado INT DEFAULT 0;

    SELECT COUNT(TorneoID) INTO encontrado
    FROM torneos
    WHERE TorneoID_buscar = TorneoID
    GROUP BY TorneoID;

    IF (encontrado = 0) THEN
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'El torneo no existe';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE registrarPartido(IN EquipoLocalID_cargar INT, IN EquipoVisitanteID_cargar INT,
                                  IN TorneoID_cargar INT, IN FechaPartido_cargar DATE)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SELECT 'Error' AS estado_operacion;
            ROLLBACK;
        END;
    START TRANSACTION;
    CALL verificarExistenciaEquipo(EquipoLocalID_cargar);
    CALL verificarExistenciaEquipo(EquipoVisitanteID_cargar);
    CALL verificarExistenciaTorneo(TorneoID_cargar);

    INSERT INTO Partidos(TorneoID, EquipoLocalID, EquipoVisitanteID, FechaPartido)
        VALUES(TorneoID_cargar,EquipoLocalID_cargar,
               EquipoVisitanteID_cargar, FechaPartido_cargar);
    COMMIT;
    SELECT 'Exito' AS estado_operacion;
END //

DELIMITER ;

-- Ej 2

# Registrar estadísticas de un jugador: procedimiento almacenado llamado
# registrarEstadisticasJugador que:
# o Reciba como parámetros el ID del jugador, el ID del partido, goles,
# asistencias y tarjetas (amarillas y rojas).
# o Verifique que los IDs proporcionados (jugador y partido) existan.
# o Si un ID no existe, genere un error personalizado usando SIGNAL.
# o Inserte las estadísticas del jugador en la tabla EstadisticasJugador.
# o Devuelva un mensaje de confirmación si la operación es exitosa.

DELIMITER //

CREATE PROCEDURE verificarExistenciaJugador(IN JugadorID_buscar INT)
BEGIN
    DECLARE encontrado INT DEFAULT 0;

    SELECT COUNT(JugadorID) INTO encontrado
    FROM Jugadores
    WHERE JugadorID_buscar = JugadorID
    GROUP BY (JugadorID);

    IF (encontrado = 0) THEN
        SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'El jugador no existe';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarExistenciaPartido(IN PartidoID_buscar INT)
BEGIN
    DECLARE encontrado INT DEFAULT 0;

    SELECT COUNT(PartidoID) INTO encontrado
    FROM Partidos
    WHERE PartidoID_buscar = PartidoID
    GROUP BY (PartidoID);

    IF (encontrado = 0) THEN
        SIGNAL SQLSTATE '45003' SET MESSAGE_TEXT = 'El partido no existe';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE registrarEstadisticasJugador(IN JugadorID_cargar INT, IN PartidoID_cargar INT, IN Goles_cargar INT,
                                              IN Asistencias_cargar INT, IN TarjetasAmarillas_cargar INT,
                                              IN TarjetasRojas_cargar INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SELECT 'Error' AS estado_operacion;
            ROLLBACK;
        END;
    START TRANSACTION;
    CALL verificarExistenciaJugador(JugadorID_cargar);
    CALL verificarExistenciaPartido(PartidoID_cargar);

    INSERT INTO EstadisticasJugador(JugadorID, PartidoID, Goles, Asistencias, TarjetasAmarillas, TarjetasRojas)
        VALUES(JugadorID_cargar,PartidoID_cargar,Goles_cargar,Asistencias_cargar,
               TarjetasAmarillas_cargar,TarjetasRojas_cargar);
    COMMIT;
    SELECT 'Exito' AS estado_operacion;
END //

DELIMITER ;

-- Ej 3

# Actualizar estadísticas de un jugador: procedimiento almacenado llamado
# actualizarEstadisticasJugador que:
# o Reciba como parámetros el ID del jugador, el ID del partido, goles,
# asistencias y tarjetas.
# o Verifique que los IDs existan.
# o Actualice las estadísticas del jugador en la tabla EstadisticasJugador.
# o Maneje errores mediante transacciones, usando ROLLBACK en caso de
# fallas.
# o Devuelva un mensaje de éxito o error.

DELIMITER //

CREATE PROCEDURE verificarExistenciaEstadistica(IN EstadisticaID_buscar INT)
BEGIN
    DECLARE encontrado INT DEFAULT 0;

    SELECT COUNT(EstadisticaID) INTO encontrado
    FROM EstadisticasJugador
    WHERE EstadisticaID_buscar = EstadisticaID
    GROUP BY (EstadisticaID);

    IF (encontrado = 0) THEN
        SIGNAL SQLSTATE '45004' SET MESSAGE_TEXT = 'La estadistica no existe';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE actualizarEstadisticasJugador(IN EstadisticaID_modificar INT,IN JugadorID_modificar INT, IN PartidoID_modificar INT, IN Goles_modificar INT,
                                               IN Asistencias_modificar INT, IN TarjetasAmarillas_modificar INT,
                                               IN TarjetasRojas_modificar INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SELECT 'Error' AS estado_operacion;
            ROLLBACK;
        END;
    START TRANSACTION;
    CALL verificarExistenciaEstadistica(EstadisticaID_modificar);
    CALL verificarExistenciaJugador(JugadorID_modificar);
    CALL verificarExistenciaPartido(PartidoID_modificar);

    UPDATE EstadisticasJugador SET JugadorID = JugadorID_modificar,
                                   PartidoID = PartidoID_modificar,
                                   Goles = Goles_modificar,
                                   Asistencias = Asistencias_modificar,
                                   TarjetasAmarillas = TarjetasAmarillas_modificar,
                                   TarjetasRojas = TarjetasRojas_modificar
        WHERE EstadisticaID = EstadisticaID_modificar;
    COMMIT;
    SELECT 'Exito' AS estado_operacion;
END //

DELIMITER ;

-- Ej 4

# Auditar inscripciones a torneos: TRIGGER llamado auditarInscripcionTorneo
# que:
# o Se dispare después de insertar un registro en la tabla Torneos.
# o Inserte en la tabla AuditoriaInscripciones el ID del torneo y su nombre.

CREATE TABLE AuditoriaInscripciones(
    AuditoriaInscripcionID INT PRIMARY KEY AUTO_INCREMENT,
    TorneoID INT,
    NombreTorneo VARCHAR(100),
    FOREIGN KEY (TorneoID) REFERENCES Torneos(TorneoID));

CREATE TRIGGER auditarInscripcionTorneo
    AFTER INSERT ON Torneos
    FOR EACH ROW
    INSERT INTO AuditoriaInscripciones(TorneoID,NombreTorneo)
    VALUES(NEW.TorneoID,NEW.NombreTorneo);

-- Ej 5

# Auditar cambios en resultados de partidos: TRIGGER llamado
# auditarCambioResultado que:
# o Se dispare después de actualizar un registro en la tabla Partidos.
# o Si el resultado del partido cambia, registre los valores antiguos y nuevos en
# la tabla AuditoriaResultados.

CREATE TABLE AuditoriaResultados(
    AuditoriaResultadosID INT PRIMARY KEY AUTO_INCREMENT,
    PartidoID INT,
    Resultado VARCHAR(50),
    FOREIGN KEY (PartidoID) REFERENCES Partidos(PartidoID)
);

DELIMITER //

CREATE TRIGGER auditarCambioResultado
    AFTER UPDATE ON Partidos
    FOR EACH ROW
    BEGIN
        INSERT INTO AuditoriaResultados(PartidoID, Resultado)
            VALUES(OLD.PartidoID, OLD.Resultado);
        INSERT INTO AuditoriaResultados(PartidoID, Resultado)
            VALUES(NEW.PartidoID, NEW.Resultado);
END //

DELIMITER ;

-- Ej 6

# Calcular el promedio de goles por jugador: función llamada
# promedioGolesPorJugador que:
# o Reciba como parámetro el ID de un jugador.
# o Calcule el promedio de goles del jugador dividiendo el total de goles entre
# la cantidad de partidos jugados.
# o Devuelva el resultado como un valor decimal.

INSERT INTO EstadisticasJugador (JugadorID, PartidoID, Goles, Asistencias, TarjetasAmarillas, TarjetasRojas)
VALUES
    (1, 1, 2, 1, 0, 0),
    (2, 1, 1, 0, 1, 0),
    (3, 1, 0, 2, 0, 1),
    (1, 2, 3, 1, 0, 0),
    (2, 2, 1, 1, 1, 0),
    (3, 2, 0, 0, 0, 0),
    (1, 3, 2, 0, 0, 0),
    (2, 3, 1, 2, 0, 0),
    (3, 3, 0, 1, 0, 1);

DELIMITER //

CREATE FUNCTION obtenerTotalGolesJugador(JugadorID_buscar INT)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE totalGoles INT;
    SELECT SUM(Goles) INTO totalGoles
        FROM EstadisticasJugador
            WHERE JugadorID = JugadorID_buscar
    GROUP BY JugadorID;

    RETURN totalGoles;
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION obtenerTotalPartidosJugados(JugadorID_buscar INT)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE totalPartidosJugados INT;
    SELECT COUNT(PartidoID) INTO totalPartidosJugados
        FROM EstadisticasJugador
            WHERE JugadorID = JugadorID_buscar
    GROUP BY JugadorID;

    RETURN totalPartidosJugados;
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION promedioGolesPorJugador(JugadorID INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE totalGoles INT;
    DECLARE totalPartidosJugados INT;
    SET totalGoles = obtenerTotalGolesJugador(JugadorID);
    SET totalPartidosJugados = obtenerTotalPartidosJugados(JugadorID);

    RETURN totalGoles / totalPartidosJugados;
END //

DELIMITER ;

-- Ej 7

# Obtener estadísticas de un jugador: función llamada estadisticasJugador que:
# o Reciba como parámetro el ID de un jugador.
# o Devuelva una cadena de texto con el nombre completo del jugador, el total
# de goles y asistencias registrados en la tabla EstadisticasJugador.

DELIMITER //

CREATE FUNCTION obtenerTotalAsistencias(JugadorID_buscar INT)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE totalAsistencias INT;
    SELECT SUM(Asistencias) INTO totalAsistencias
    FROM EstadisticasJugador
    WHERE JugadorID = JugadorID_buscar
    GROUP BY JugadorID;

    RETURN totalAsistencias;
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION obtenerNombreCompletoJugador(JugadorID_buscar INT)
    RETURNS VARCHAR(100)
    DETERMINISTIC
BEGIN
    DECLARE nombreCompleto VARCHAR(100);
    SELECT CONCAT(Nombre,' ',Apellido) INTO nombreCompleto
        FROM Jugadores
            WHERE JugadorID = JugadorID_buscar;
    RETURN nombreCompleto;
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION estadisticasJugador(JugadorID INT)
RETURNS VARCHAR(150)
DETERMINISTIC
BEGIN
    DECLARE mensaje VARCHAR(150);
    DECLARE totalGoles INT;
    DECLARE totalAsistencias INT;
    DECLARE nombreCompleto VARCHAR(100);

    SET totalGoles = obtenerTotalGolesJugador(JugadorID);
    SET totalAsistencias = obtenerTotalAsistencias(JugadorID);
    SET nombreCompleto = obtenerNombreCompletoJugador(JugadorID);

    SET mensaje = CONCAT(nombreCompleto,', total goles: ', totalGoles, ', total asistencias: ', totalAsistencias);
    RETURN mensaje;
END //

DELIMITER ;

SELECT estadisticasJugador(1);

-- Ej 8 -- DIFICIL

# Crear una consulta utilizando una CTE llamada TarjetasPorJugador que:
# o Agrupe las tarjetas amarillas y rojas de cada jugador en partidos.
# o Combine las tarjetas acumuladas utilizando UNION ALL.
# o En la consulta principal, muestre el nombre del jugador y la cantidad total
# de tarjetas acumuladas (amarillas + rojas), ordenadas en orden
# descendente.

WITH tarjetasPorJugador AS (SELECT JugadorID, SUM(TarjetasAmarillas) AS tarjetas
                           FROM EstadisticasJugador
                           GROUP BY JugadorID
                           UNION ALL
                           SELECT JugadorID, SUM(TarjetasRojas)
                           FROM EstadisticasJugador
                           GROUP BY JugadorID)
SELECT j.Nombre, j.Apellido, SUM(txj.tarjetas) AS cantidadTarjetas
FROM Jugadores j,
     tarjetasPorJugador txj
WHERE j.JugadorID = txj.JugadorID
GROUP BY j.Nombre, j.Apellido
ORDER BY cantidadTarjetas DESC;

-- Ej 9

# Estadísticas de los mejores jugadores: CTE llamada TopJugadores que:
# o Seleccione los jugadores con un total de goles mayor a 5 y asistencias
# mayor o igual a 3.
# o En la consulta principal, muestra el nombre, apellido, equipo, total de
# goles y asistencias de los jugadores destacados.

WITH topJugadores AS (SELECT JugadorID
                      FROM EstadisticasJugador
                      WHERE obtenerTotalGolesJugador(JugadorID) > 5
                        AND obtenerTotalAsistencias(JugadorID) >= 3)
SELECT j.Nombre,
       j.Apellido,
       e.NombreEquipo,
       obtenerTotalGolesJugador(j.JugadorID),
       obtenerTotalAsistencias(j.JugadorID)
FROM Jugadores j,
     Equipos e,
     topJugadores topj
WHERE j.JugadorID = topj.JugadorID;

