-- Tabla Clientes
CREATE TABLE Clientes (
                          cliente_id INT PRIMARY KEY,
                          nombre VARCHAR(50),
                          apellido VARCHAR(50),
                          ciudad VARCHAR(50),
                          email VARCHAR(50)
);

-- Tabla Productos
CREATE TABLE Productos (
                           producto_id INT PRIMARY KEY,
                           nombre_producto VARCHAR(50),
                           categoria VARCHAR(50),
                           precio DECIMAL(10, 2)
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
                         pedido_id INT PRIMARY KEY,
                         cliente_id INT,
                         fecha_pedido DATE,
                         FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

-- Tabla Detalle_Pedido
CREATE TABLE Detalle_Pedido (
                                detalle_id INT PRIMARY KEY,
                                pedido_id INT,
                                producto_id INT,
                                cantidad INT,
                                FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id),
                                FOREIGN KEY (producto_id) REFERENCES Productos(producto_id)
);

-- Insertar registros en Clientes
INSERT INTO Clientes (cliente_id, nombre, apellido, ciudad, email) VALUES
                                                                       (1, 'Ana', 'García', 'Madrid', 'ana.garcia@email.com'),
                                                                       (2, 'Juan', 'Pérez', 'Barcelona', 'juan.perez@email.com'),
                                                                       (3, 'María', 'López', 'Madrid', 'maria.lopez@email.com'),
                                                                       (4, 'Carlos', 'Ruiz', 'Valencia', 'carlos.ruiz@email.com');

-- Insertar registros en Productos
INSERT INTO Productos (producto_id, nombre_producto, categoria, precio) VALUES
                                                                            (1, 'Laptop', 'Electrónicos', 1200.00),
                                                                            (2, 'Tablet', 'Electrónicos', 300.00),
                                                                            (3, 'Libro', 'Libros', 25.00),
                                                                            (4, 'Smartphone', 'Electrónicos', 800.00);

-- Insertar registros en Pedidos
INSERT INTO Pedidos (pedido_id, cliente_id, fecha_pedido) VALUES
                                                              (1, 1, '2023-10-26'),
                                                              (2, 1, '2023-11-10'),
                                                              (3, 2, '2023-11-05'),
                                                              (4, 3, '2023-10-28'),
                                                              (5, 4, '2023-11-15');

-- Insertar registros en Detalle_Pedido
INSERT INTO Detalle_Pedido (detalle_id, pedido_id, producto_id, cantidad) VALUES
                                                                              (1, 1, 1, 1),
                                                                              (2, 1, 2, 2),
                                                                              (3, 2, 4, 1),
                                                                              (4, 3, 3, 3),
                                                                              (5, 4, 1, 1),
                                                                              (6, 5, 2, 2),
                                                                              (7, 5, 4, 1);

-- Ej 1

SET PROFILING = 1;

SELECT c.nombre, c.apellido, c.email FROM clientes c
WHERE c.ciudad = "Madrid";

SHOW PROFILES;

CREATE INDEX idx_city ON Clientes(ciudad);

SELECT c.nombre, c.apellido, c.email FROM clientes c
WHERE c.ciudad = "Madrid";

SHOW PROFILES;

-- Ej 2

CREATE INDEX idx_clienteId_fechaPedido ON Pedidos(cliente_id,fecha_pedido);

SELECT c.nombre,c.apellido, COUNT(*) AS cant_pedidos FROM clientes c
JOIN pedidos p ON c.cliente_id = p.cliente_id
WHERE p.fecha_pedido > '2023-10-01' AND p.fecha_pedido < '2023-10-30'
GROUP BY c.nombre, c.apellido;

SHOW PROFILES;

-- Ej 3

CREATE UNIQUE INDEX idx_productoId ON Productos(producto_id);

INSERT INTO Productos VALUES (2,"prueba","prueba",10.99);

-- Ej 4

CREATE FULLTEXT INDEX idx_nombreCategoriaProducto ON Productos(nombre_producto,categoria);

SELECT * FROM Productos p
WHERE MATCH(nombre_producto,categoria) AGAINST ("Smartphone");

-- Ej 1V

CREATE VIEW clientes_por_ciudad AS
    (SELECT c.nombre, c.apellido, c.email
    FROM CLIENTES c
    WHERE c.ciudad = "Madrid")
WITH CHECK OPTION;

DROP VIEW clientes_por_ciudad;

SELECT * FROM clientes_por_ciudad;

INSERT INTO clientes_por_ciudad(nombre,apellido,email) VALUES ("prueba","prueba","prueba@mail.com");

-- Ej 2V

CREATE VIEW resumen_ventas_categoria AS
    (SELECT DISTINCT p.categoria, SUM(dp.cantidad) AS cant_ventas
    FROM productos p JOIN detalle_pedido dp
                          ON p.producto_id = dp.producto_id
    GROUP BY p.categoria);

SELECT * FROM resumen_ventas_categoria;

-- Ej 3V

CREATE VIEW clientes_total_pedidos AS
(
SELECT CONCAT(c.nombre," ", c.apellido) AS nombreCompleto, COUNT(p.pedido_id) AS cant_pedidos
FROM clientes c
         JOIN pedidos p
              ON c.cliente_id = p.cliente_id
GROUP BY p.cliente_id
    );

SELECT * FROM clientes_total_pedidos;

-- Ej 4V

CREATE VIEW productos_mas_vendidos_ciudad AS
(
SELECT DISTINCT c.ciudad, p.nombre_producto, SUM(dp.cantidad) AS cant_ventas
FROM productos p
         JOIN detalle_pedido dp
              ON p.producto_id = dp.producto_id
         JOIN pedidos ped ON dp.pedido_id = ped.pedido_id
         JOIN clientes c ON ped.cliente_id = c.cliente_id
GROUP BY c.ciudad, p.producto_id
    );

-- Ej 5V

CREATE VIEW ingresos_por_mes AS ();

SELECT DATE_FORMAT(ped.fecha_pedido,"%Y %M"),SUM(p.precio)
FROM pedidos ped JOIN detalle_pedido dp
ON ped.pedido_id = dp.pedido_id
JOIN productos p
ON dp.producto_id = p.producto_id
GROUP BY MONTH(ped.fecha_pedido), ped.pedido_id;

-- Ej 6V

CREATE VIEW productos_electronicos AS (SELECT * FROM productos p
                                      WHERE p.categoria = "Electrónicos");

CREATE VIEW ventas_electronicos AS
(
SELECT dp.*
FROM productos_electronicos p_e
         JOIN detalle_pedido dp
              ON p_e.producto_id = dp.producto_id)
WITH LOCAL CHECK OPTION;

SELECT * FROM ventas_electronicos;
