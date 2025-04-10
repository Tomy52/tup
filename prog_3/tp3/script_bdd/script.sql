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
        SIGNAL SQLSTATE '45000'
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

CALL agregarUsuario('Juan','Perez','12345678','juan.perez@email.com');

DELIMITER //

CREATE PROCEDURE verificarCredenciales(IN usernameVerificar VARCHAR(255), IN passVerificar VARCHAR(255))
BEGIN
    DECLARE encontrado INT;
    SELECT COUNT(*) INTO encontrado
    FROM credenciales
    WHERE username = usernameVerificar AND pass = passVerificar;

    IF encontrado = 0 THEN
        SIGNAL SQLSTATE '45001'
            SET MESSAGE_TEXT = 'Las credenciales son incorrectas';
    END IF;
END //

DELIMITER ;

DROP PROCEDURE verificarCredenciales;

CREATE PROCEDURE traerUsuario(IN idUsuarioTraer INT)
BEGIN
    -- Hay que hacer esto para el login. Hacer select con el where incluyendo user y pass
    SELECT *
    FROM usuarios
    WHERE username = usernameVerificar AND pass = passVerificar;
end;
