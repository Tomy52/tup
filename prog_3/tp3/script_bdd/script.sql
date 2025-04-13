CREATE TABLE usuarios (
                          id_usuario INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          nombre VARCHAR(255),
                          apellido VARCHAR(255),
                          dni VARCHAR(255),
                          email VARCHAR(255),
                          fecha_creacion VARCHAR(255)
);

INSERT INTO usuarios(id_usuario,nombre,apellido,dni,email,fecha_creacion) VALUES ('1', 'Juan', 'Pérez', '12345678', 'juan.perez@email.com', '2025-03-25 15:37:49');
INSERT INTO usuarios(id_usuario,nombre,apellido,dni,email,fecha_creacion) VALUES ('2', 'María', 'Gómez', '23456789', 'maria.gomez@email.com', '2025-03-25 15:37:49');
INSERT INTO usuarios(id_usuario,nombre,apellido,dni,email,fecha_creacion) VALUES ('3', 'Carlos', 'López', '34567890', 'carlos.lopez@email.com', '2025-03-25 15:37:49');
INSERT INTO usuarios(id_usuario,nombre,apellido,dni,email,fecha_creacion) VALUES ('4', 'Ana', 'Martínez', '45678901', 'ana.martinez@email.com', '2025-03-25 15:37:49');
INSERT INTO usuarios(id_usuario,nombre,apellido,dni,email,fecha_creacion) VALUES ('5', 'Pedro', 'Fernández', '56789012', 'pedro.fernandez@email.com', '2025-03-25 15:37:49');

CREATE TABLE credenciales (
                              id_credencial INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                              id_usuario INT,
                              username VARCHAR(255),
                              pass VARCHAR(255),
                              permiso VARCHAR(255),
                              FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

INSERT INTO credenciales(id_credencial, id_usuario, username, pass, permiso) VALUES ('1', '1', 'juanperez', '1234', 'CLIENTE');
INSERT INTO credenciales(id_credencial, id_usuario, username, pass, permiso) VALUES ('2', '2', 'mariagomez', '1234', 'ADMINISTRADOR');
INSERT INTO credenciales(id_credencial, id_usuario, username, pass, permiso) VALUES ('3', '3', 'carloslopez', '1234', 'GESTOR');
INSERT INTO credenciales(id_credencial, id_usuario, username, pass, permiso) VALUES ('4', '4', 'anamartinez', '1234', 'CLIENTE');
INSERT INTO credenciales(id_credencial, id_usuario, username, pass, permiso) VALUES ('5', '5', 'pedrofernandez', 'hashedpassword5', 'ADMINISTRADOR');

CREATE TABLE cuentas (
                         id_cuenta INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                         id_usuario INT,
                         tipo VARCHAR(255),
                         saldo DOUBLE,
                         fecha_creacion VARCHAR(255),
                         FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('1', '1', 'CAJA_AHORRO', '15000.5', '2025-03-25 15:37:49');
INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('2', '1', 'CUENTA_CORRIENTE', '5000.75', '2025-03-25 15:37:49');
INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('3', '2', 'CAJA_AHORRO', '30000.0', '2025-03-25 15:37:49');
INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('4', '3', 'CUENTA_CORRIENTE', '12000.2', '2025-03-25 15:37:49');
INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('5', '4', 'CAJA_AHORRO', '8000.9', '2025-03-25 15:37:49');
INSERT INTO cuentas(id_cuenta, id_usuario, tipo, saldo, fecha_creacion) VALUES ('6', '5', 'CUENTA_CORRIENTE', '25000.0', '2025-03-25 15:37:49');

DELIMITER //

CREATE PROCEDURE verificarUnaSolaCajaAhorro(IN idUsuarioVerificar VARCHAR(255))
BEGIN
    DECLARE encontrado INT;
    SELECT COUNT(*) INTO encontrado
    FROM cuentas
    WHERE id_usuario = idUsuarioVerificar AND tipo = 'CAJA_AHORRO';

    IF encontrado > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El usuario ya tiene una caja de ahorro';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarNombreUsuario(IN usernameVerificar VARCHAR(255))
BEGIN
    DECLARE encontrado INT;
    SELECT COUNT(*) INTO encontrado
    FROM credenciales
    WHERE username = usernameVerificar;

    IF encontrado > 0 THEN
        SIGNAL SQLSTATE '45001'
            SET MESSAGE_TEXT = 'El nombre de usuario ya existe';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE agregarUsuario(IN nombreAgregar VARCHAR(255),
                                IN apellidoAgregar VARCHAR(255),
                                IN dniAgregar VARCHAR(255),
                                IN emailAgregar VARCHAR(255))
BEGIN
    DECLARE idUsuarioAgregar INT;
    DECLARE usernameAgregar VARCHAR(255);
    START TRANSACTION;
    INSERT INTO usuarios(nombre, apellido, dni, email, fecha_creacion)
    VALUES(nombreAgregar,apellidoAgregar,dniAgregar,emailAgregar,NOW());

    SET idUsuarioAgregar = LAST_INSERT_ID();
    SET usernameAgregar = CONCAT(nombreAgregar,apellidoAgregar);

    CALL verificarNombreUsuario(usernameAgregar);

    INSERT INTO credenciales(id_usuario,username,pass,permiso)
    VALUES(idUsuarioAgregar,usernameAgregar,'1234','CLIENTE');


    CALL verificarUnaSolaCajaAhorro(idUsuarioAgregar);

    INSERT INTO cuentas(id_usuario,tipo,saldo,fecha_creacion)
    VALUES(idUsuarioAgregar,'CAJA_AHORRO', 0, NOW());
    COMMIT;
END //
DELIMITER ;

CREATE VIEW usuariosCredenciales AS
SELECT u.*,c.username,c.pass,c.permiso
FROM usuarios u
         JOIN credenciales c
              ON u.id_usuario = c.id_usuario;

SELECT * FROM usuariosCredenciales;

DELIMITER //

CREATE PROCEDURE login(IN usernameVerificar VARCHAR(255), IN passVerificar VARCHAR(255))
BEGIN
    SELECT *
    FROM usuariosCredenciales
    WHERE username = usernameVerificar AND pass = passVerificar;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE buscarUsuario(IN idUsuarioBuscar INT)
BEGIN
    SELECT *
    FROM usuariosCredenciales
    WHERE id_usuario = idUsuarioBuscar;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarUsuarioNoExiste(IN usernameVerificar VARCHAR(255))
BEGIN
    DECLARE encontrado INT;
    SELECT COUNT(*) INTO encontrado
    FROM credenciales cred JOIN cuentas c on cred.id_usuario = c.id_usuario
    WHERE username = usernameVerificar;

    IF encontrado > 0 THEN
        SIGNAL SQLSTATE '45002'
            SET MESSAGE_TEXT = 'Existen registros remanentes del usuario';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE eliminarUsuario(IN idUsuarioEliminar INT)
BEGIN
    START TRANSACTION;
    DELETE
    FROM usuarios
    WHERE id_usuario = idUsuarioEliminar;
    CALL verificarUsuarioNoExiste(idUsuarioEliminar);
    COMMIT;
END //

DELIMITER ;

DROP PROCEDURE eliminarUsuario;

CREATE VIEW usuariosCuentas AS
    SELECT u.nombre,u.apellido,c.*
    FROM usuarios u JOIN cuentas c
    ON u.id_usuario = c.id_usuario;


DELIMITER //

CREATE PROCEDURE crearCuenta(IN idUsuarioTitular INT, IN tipoCuenta VARCHAR(255))
BEGIN
    START TRANSACTION;
    IF tipoCuenta = 'CAJA_AHORRO' THEN
        CALL verificarUnaSolaCajaAhorro(idUsuarioTitular);
    END IF;
    INSERT INTO cuentas(id_usuario,tipo,saldo,fecha_creacion)
        VALUES(idUsuarioTitular,tipoCuenta,0,NOW());
    COMMIT;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarSaldoEsCero(IN idCuentaEliminar INT)
BEGIN
    DECLARE saldoCuentaEliminar INT;
    SELECT saldo INTO saldoCuentaEliminar FROM cuentas WHERE id_cuenta = idCuentaEliminar;
    IF saldoCuentaEliminar != 0 THEN
        SIGNAL SQLSTATE '45003'
            SET MESSAGE_TEXT = 'El saldo de la cuenta no es 0';
    END IF;
end //

DELIMITER //

CREATE PROCEDURE eliminarCuenta(IN idCuentaEliminar INT)
BEGIN
    START TRANSACTION;
    CALL verificarSaldoEsCero(idCuentaEliminar);
    DELETE FROM cuentas WHERE id_cuenta = idCuentaEliminar;
    COMMIT;
END//

DELIMITER ;

DELIMITER //
CREATE PROCEDURE obtenerCuentasDeUsuario(IN idUsuarioTitular INT)
BEGIN
    SELECT * FROM cuentas
        WHERE id_usuario = idUsuarioTitular;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarMontoNoNegativo(IN montoVerificar DOUBLE)
BEGIN
    IF montoVerificar < 0 THEN
        SIGNAL SQLSTATE '45004' SET MESSAGE_TEXT = "El monto no puede ser inferior a 0";
        ELSE
        SELECT 'exito' AS estado_verificacion;
    END IF;
end //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE depositar(IN idCuentaDepositar INT, IN monto DOUBLE)
BEGIN
    START TRANSACTION;
    CALL verificarMontoNoNegativo(monto);
    UPDATE cuentas SET saldo = saldo + monto
    WHERE id_cuenta = idCuentaDepositar;
    COMMIT;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE buscarCuenta(IN idCuentaBuscar INT)
BEGIN
    SELECT *
    FROM cuentas
    WHERE id_cuenta = idCuentaBuscar;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE transferir(IN cuentaOrigen INT, IN cuentaDestino INT, IN monto DOUBLE)
BEGIN
    START TRANSACTION;
    CALL verificarSaldoOrigen(cuentaOrigen,monto);
    UPDATE cuentas SET saldo = saldo - monto WHERE id_cuenta = cuentaOrigen;
    UPDATE cuentas SET saldo = saldo + monto WHERE id_cuenta = cuentaDestino;
    COMMIT;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE verificarSaldoOrigen(IN cuentaOrigen INT, IN monto DOUBLE)
BEGIN
    DECLARE saldoOrigen DOUBLE;

    SELECT c.saldo INTO saldoOrigen FROM cuentas c WHERE c.id_cuenta = cuentaOrigen;
    IF (saldoOrigen - monto < 0) THEN
        SIGNAL SQLSTATE '45005' SET MESSAGE_TEXT = 'No hay fondos suficientes';
    ELSE
        SELECT 'exito' AS estado_verificacion;
    END IF;
END //

DELIMITER ;

DROP PROCEDURE verificarSaldoOrigen;

CREATE VIEW ids_usuarios AS (
    SELECT id_usuario
        FROM usuarios
                            )


