-- Ej 1

CREATE TABLE empleados (
                           id INT PRIMARY KEY,
                           nombre VARCHAR(50),
                           salario DECIMAL(10,2)
);

INSERT INTO empleados VALUES (1, 'Juan Pérez', 3500);
INSERT INTO empleados VALUES (2, 'Ana Gómez', 4200);
INSERT INTO empleados VALUES (3, 'Carlos Ruiz', 5000);

DELIMITER //
CREATE PROCEDURE ObtenerSalario(IN idEmpleado INT, OUT salarioEmpleado DECIMAL)
BEGIN
    IF idEmpleado IN (SELECT id FROM empleados) THEN
    SELECT e.salario INTO salarioEmpleado
    FROM empleados e
    WHERE idEmpleado = id;
    ELSE
        SELECT -1 INTO salarioEmpleado;
        END IF;
END //
DELIMITER ;

CALL ObtenerSalario(1,@salarioEmpleado);
SELECT @salarioEmpleado;

-- Ej 2

DELIMITER //
CREATE PROCEDURE CalcularDescuento(IN precio DECIMAL, OUT precioDescuento DECIMAL)
BEGIN
    SELECT precio - (precio / 10) INTO precioDescuento;
END //
DELIMITER ;

CALL CalcularDescuento(10,@precioDescuento);
SELECT @precioDescuento;

-- Ej 3

DELIMITER //
CREATE PROCEDURE DuplicarNumero (INOUT numero INT)
BEGIN
    SELECT numero * 2 INTO numero;
END //
DELIMITER ;

SET @numero = 2;
CALL DuplicarNumero(@numero);
SELECT @numero;

-- Ej 4

DELIMITER //
CREATE PROCEDURE VerificarEdad(IN edad INT, OUT mensaje VARCHAR(50))
BEGIN
    IF edad < 18 THEN
        SELECT "Menor de edad" INTO mensaje;
    ELSE
        SELECT "Mayor de edad" INTO mensaje;
    END IF;
END //
DELIMITER ;

CALL VerificarEdad(100,@mensaje);
SELECT @mensaje;

-- Ej 5

DELIMITER //
CREATE PROCEDURE SumarHastaN(IN n INT, OUT sumaHastaN INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= n DO
        SET sumaHastaN = i + 1;
        SET i = i + 1;
        END WHILE;
END //
DELIMITER ;

CALL SumarHastaN(2,@sumaHastaN);
SELECT @sumaHastaN;

-- EJERCICIOS TP

-- Ej 1

DELIMITER //
CREATE PROCEDURE insertarEnTablaInexistente()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SELECT ("La tabla no existe, se finalizara la ejecucion");
        END;
    INSERT INTO empleos(nombre) VALUES ("Programador");
END //
DELIMITER ;

CALL insertarEnTablaInexistente();

-- Ej 2

DELIMITER //
CREATE PROCEDURE crearTablaExistente()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SELECT("La tabla ya existe, se finalizara la ejecucion");
        END;
    CREATE TABLE empleados (
                               id INT PRIMARY KEY,
                               nombre VARCHAR(50),
                               salario DECIMAL(10,2));
END //
DELIMITER ;

CALL crearTablaExistente();

-- Ej 3

DELIMITER //
CREATE PROCEDURE seleccionarRegistroInexistente()
BEGIN
    DECLARE idResultado INT;
    DECLARE EXIT HANDLER FOR NOT FOUND
        BEGIN
            SELECT "El registro no existe, se finalizara la ejecucion" as mensaje_error;
            END;
    SELECT id INTO idResultado FROM empleados
        WHERE id = 5;
END //
DELIMITER ;

CALL seleccionarRegistroInexistente();

-- Ej 4

DELIMITER //
CREATE PROCEDURE manejoCombinado()
BEGIN
    BEGIN
        DECLARE EXIT HANDLER FOR SQLSTATE "42S02"
            BEGIN
                SELECT "La tabla donde se intenta cargar el dato no existe" AS mensaje_error;
                END;
        INSERT INTO tabla_inexistente(dato) VALUES (1);
        END;
    BEGIN
        DECLARE EXIT HANDLER FOR SQLSTATE "42S01"
            BEGIN
                SELECT "La tabla que se intenta cargar ya existe" AS mensaje_error;
                END;
        CREATE TABLE empleados (
                                   id INT PRIMARY KEY,
                                   nombre VARCHAR(50),
                                   salario DECIMAL(10,2));
        END;
END //
DELIMITER ;

CALL manejoCombinado();

-- Ej 5

DELIMITER //
CREATE PROCEDURE validarIdSocio(IN id_socio_dado INT)
BEGIN
    DECLARE encontrado INT;
    DECLARE EXIT HANDLER FOR NOT FOUND
        BEGIN
            SELECT ("El id de socio no existe") AS mensaje_error;
            SIGNAL SQLSTATE '45001' SET message_text = "El socio no existe";
        END;
    SELECT id_socio INTO encontrado FROM socios WHERE id_socio_dado = id_socio;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE validarIdPlan(IN id_plan_dado INT)
BEGIN
    DECLARE encontrado INT;
    DECLARE EXIT HANDLER FOR NOT FOUND
        BEGIN
            SELECT ("El id del plan no existe") AS mensaje_error;
            SIGNAL SQLSTATE '45000' SET message_text = "El plan no existe";
        END;
    SELECT id_plan INTO encontrado FROM planes WHERE id_plan_dado = id_plan;
END //

DELIMITER ;

DROP PROCEDURE validarIdPlan;

DELIMITER //
CREATE PROCEDURE insertarActividad(IN actividad INT, IN socio INT, IN plan INT)
BEGIN
    CALL validarIdSocio(socio);
    CALL validarIdPlan(plan);
    INSERT INTO actividades(id_actividad, id_socio, id_plan) VALUES (actividad, socio, plan);
END //

DELIMITER ;

CALL insertarActividad(8,2,1);

-- Ej 6

DELIMITER //
CREATE PROCEDURE seleccionarSocio(IN socio INT)
BEGIN
    DECLARE socioEncontrado INT;
    DECLARE EXIT HANDLER FOR NOT FOUND
        BEGIN
            SELECT "No existe el socio ingresado";
        END;
    SELECT id_socio INTO socioEncontrado FROM socios WHERE socio = id_socio;
END //
DELIMITER ;

DROP PROCEDURE seleccionarSocio;

CALL seleccionarSocio(2);

-- Ej 7

DELIMITER //
CREATE PROCEDURE registrarSocioConPlan(IN nombre VARCHAR(50), IN apellido VARCHAR(50), IN fechaNacimiento DATE, IN direccion VARCHAR(20), IN telefono VARCHAR(20), IN idPlan INT)
BEGIN
    START TRANSACTION;
    CALL validarIdPlan(idPlan);
    INSERT INTO socios(nombre, apellido, fecha_nacimiento, direccion, telefono)
    VALUES (nombre,apellido,fechaNacimiento,direccion,telefono);

    INSERT INTO actividades(id_socio,id_plan) VALUES (LAST_INSERT_ID(),idPlan);
    COMMIT;
    SELECT "Operacion exitosa!" AS resultado_operacion;
END //

DROP PROCEDURE registrarSocioConPlan;

CALL registrarSocioConPlan("prueba","entrada",now(),"Calle 456","1122223333",2);

-- Ej 8

DELIMITER //
CREATE PROCEDURE actualizarPlanYRegistrarActividad(IN idPlan INT, IN nuevoPrecio DECIMAL(10,2), IN idSocio INT)
BEGIN
    START TRANSACTION;
    CALL validarIdPlan(idPlan);
    UPDATE planes SET precio = nuevoPrecio WHERE id_plan = idPlan;

    CALL validarIdSocio(idSocio);
    INSERT INTO actividades(id_socio,id_plan) VALUES (idSocio,idPlan);
    COMMIT;
    SELECT "Operacion exitosa!" AS resultado_operacion;
END //

CALL actualizarPlanYRegistrarActividad(1,10.99,1);

-- Ej 9

DELIMITER //
CREATE PROCEDURE eliminarSocioYActividades(IN idSocio INT)
BEGIN
    START TRANSACTION;
    CALL validarIdSocio(idSocio);
    DELETE FROM actividades WHERE id_socio = idSocio;
    DELETE FROM socios WHERE id_socio = idSocio;
    COMMIT;
    SELECT "Operacion exitosa!" AS resultado_operacion;
END //

CALL eliminarSocioYActividades(5);