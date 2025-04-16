CREATE TABLE Clientes (
                          cliente_id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(50),
                          apellido VARCHAR(50)
);

CREATE TABLE Ventas (
                        venta_id INT AUTO_INCREMENT PRIMARY KEY,
                        cliente_id INT,
                        fecha_venta DATE,
                        valor DECIMAL(10,2),
                        FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

CREATE TABLE Empleados (
                           empleado_id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(50),
                           fecha_contratacion DATE,
                           salario DECIMAL(10,2),
                           departamento_id INT
);

CREATE TABLE Departamentos (
                               departamento_id INT AUTO_INCREMENT PRIMARY KEY,
                               nombre_departamento VARCHAR(50),
                               jefe_id INT
);

CREATE TABLE auditoria_ventas (
                                  venta_id INT,
                                  fecha_borrado DATETIME
);

-- Para 2.f agregamos la tabla productos
CREATE TABLE Productos (
                           producto_id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_producto VARCHAR(100),
                           stock INT
);

-- Insertando Clientes
INSERT INTO Clientes (nombre, apellido) VALUES
                                            ('Juan', 'Pérez'),
                                            ('Ana', 'Gómez'),
                                            ('Luis', 'Martínez');

-- Insertando Empleados
INSERT INTO Empleados (nombre, fecha_contratacion, salario, departamento_id) VALUES
                                                                                 ('Carlos Ruiz', '2015-03-15', 75000, 1),
                                                                                 ('Sofía Pérez', '2019-06-10', 68000, 2),
                                                                                 ('Miguel Torres', '2010-01-20', 90000, 1);

-- Insertando Departamentos
INSERT INTO Departamentos (nombre_departamento, jefe_id) VALUES
                                                             ('Ventas', 1),
                                                             ('Recursos Humanos', 2);

-- Insertando Ventas
INSERT INTO Ventas (cliente_id, fecha_venta, valor) VALUES
                                                        (1, '2024-05-10', 2000),
                                                        (1, '2024-06-11', 3500),
                                                        (2, '2024-03-15', 6000),
                                                        (3, '2024-04-20', 4500);

-- Insertando Productos
INSERT INTO Productos (nombre_producto, stock) VALUES
                                                   ('Notebook', 10),
                                                   ('Mouse', 25),
                                                   ('Teclado', 15);


-- Ej 1

DELIMITER //

CREATE PROCEDURE registrar_venta(IN cliente_idInsertar INT, IN valorVenta INT, OUT id_venta INT)
BEGIN
INSERT INTO Ventas(cliente_id, fecha_venta, valor) VALUES (cliente_idInsertar, NOW(), valorVenta);
SET id_venta = LAST_INSERT_ID();
END//

DELIMITER ;

DROP PROCEDURE registrar_venta;

CALL registrar_venta(1,7600,@idVenta);
SELECT @idVenta;

-- Ej 2

WITH totalVentasPorCliente AS (
    SELECT v.cliente_id,SUM(v.valor) AS total_vendido
    FROM ventas v
    GROUP BY v.cliente_id
)
SELECT * FROM totalVentasPorCliente t
WHERE total_vendido > 5000;

-- Ej 3

CREATE TRIGGER registrarEliminacionVentas
    BEFORE DELETE ON Ventas
    FOR EACH ROW
    INSERT INTO auditoria_ventas(venta_id, fecha_borrado)
    VALUES(OLD.venta_id,NOW());

DELETE FROM ventas WHERE venta_id = 5;

-- Ej 4

CREATE VIEW empleadosAntiguos AS (
                                 SELECT e.nombre, e.salario, DATEDIFF(NOW(),e.fecha_contratacion) AS tiempo_trabajado
                                 FROM empleados e
                                 WHERE DATEDIFF(NOW(),e.fecha_contratacion) >= 1825
                                     );

SELECT * FROM empleadosAntiguos;

-- Ej 5

DELIMITER //

CREATE PROCEDURE verificarExistenciaCliente(IN cliente_idVerificar INT)
BEGIN
    DECLARE cantidad INT;
SELECT COUNT(cliente_id) INTO cantidad FROM clientes
WHERE cliente_id = cliente_idVerificar;
IF (cantidad = 0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El cliente no existe';
end if;
end //

DELIMITER ;

DROP PROCEDURE verificarExistenciaCliente;

CALL verificarExistenciaCliente(25);

DELIMITER //

CREATE PROCEDURE obtenerTotalVentasCliente(IN cliente_idBuscado INT)
BEGIN
CALL verificarExistenciaCliente(cliente_idBuscado);
SELECT v.cliente_id,SUM(v.valor) AS total_vendido
FROM ventas v
WHERE v.cliente_id = cliente_idBuscado
GROUP BY v.cliente_id;
end //

DELIMITER ;

DROP PROCEDURE obtenerTotalVentasCliente;

CALL obtenerTotalVentasCliente(2);

-- Ej 6

DELIMITER //
CREATE PROCEDURE verificarStock(IN producto_idVerificar INT, IN nuevo_stock INT)
BEGIN
    DECLARE stock_actual INT;
SELECT stock INTO stock_actual FROM productos WHERE producto_id = producto_idVerificar;
IF (stock_actual - nuevo_stock < 0) THEN
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'El nuevo stock es negativo!';
end if;
end //

DELIMITER ;

DELIMITER //

DELIMITER //

CREATE PROCEDURE verificarExistenciaProducto(IN producto_idVerificar INT)
BEGIN
    DECLARE cantidad INT;
SELECT COUNT(producto_idVerificar) INTO cantidad FROM productos
WHERE producto_id = producto_idVerificar;
IF (cantidad = 0) THEN
        SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'El producto no existe';
end if;
end //

DELIMITER ;

CREATE PROCEDURE actualizarStock(IN producto_idCambiar INT, IN nuevo_stock INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLSTATE '45001'
BEGIN
SELECT 'Error actualizando stock';
ROLLBACK;
end;
    DECLARE EXIT HANDLER FOR SQLSTATE '45002'
BEGIN
SELECT 'Error actualizando stock';
ROLLBACK;
end;
START TRANSACTION;
CALL verificarExistenciaProducto(producto_idCambiar);
CALL verificarStock(producto_idCambiar,nuevo_stock);
UPDATE productos SET stock = stock - nuevo_stock WHERE producto_id = producto_idCambiar;
COMMIT;
END;

DELIMITER ;

DROP PROCEDURE actualizarStock;

CALL actualizarStock(1,30);