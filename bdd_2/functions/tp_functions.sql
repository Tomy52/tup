CREATE TABLE Clientes (
                          cliente_id INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(50),
                          apellido VARCHAR(50),
                          ciudad VARCHAR(50),
                          email VARCHAR(50)
);

CREATE TABLE Libros (
                        libro_id INT PRIMARY KEY AUTO_INCREMENT,
                        nombre_libro VARCHAR(100),
                        autor VARCHAR(100),
                        precio DECIMAL(10, 2)
);

CREATE TABLE Ventas (
                        venta_id INT PRIMARY KEY AUTO_INCREMENT,
                        cliente_id INT,
                        fecha_venta DATE,
                        FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id)
);

CREATE TABLE Detalle_Venta (
                               detalle_venta_id INT PRIMARY KEY AUTO_INCREMENT,
                               venta_id INT,
                               libro_id INT,
                               cantidad INT,
                               FOREIGN KEY (venta_id) REFERENCES Ventas(venta_id),
                               FOREIGN KEY (libro_id) REFERENCES Libros(libro_id)
);

INSERT INTO Clientes (nombre, apellido, ciudad, email)
VALUES
    ('Carlos', 'Gomez', 'Madrid', 'carlos.gomez@email.com'),
    ('Ana', 'Martinez', 'Barcelona', 'ana.martinez@email.com'),
    ('Juan', 'Lopez', 'Sevilla', 'juan.lopez@email.com'),
    ('Maria', 'Perez', 'Valencia', 'maria.perez@email.com');

INSERT INTO Libros (nombre_libro, autor, precio)
VALUES
    ('Cien años de soledad', 'Gabriel García Márquez', 15.99),
    ('Don Quijote de la Mancha', 'Miguel de Cervantes', 12.50),
    ('La sombra del viento', 'Carlos Ruiz Zafón', 18.75),
    ('El código Da Vinci', 'Dan Brown', 20.99),
    ('Harry Potter y la piedra filosofal', 'J.K. Rowling', 25.00);

INSERT INTO Ventas (cliente_id, fecha_venta)
VALUES
    (1, '2025-03-10'),
    (2, '2025-03-12'),
    (3, '2025-03-15'),
    (4, '2025-03-17'),
    (1, '2025-03-20'),
    (2, '2025-03-22'),
    (3, '2025-03-25'),
    (4, '2025-03-28'),
    (1, '2025-03-30'),
    (2, '2025-04-01');

INSERT INTO Detalle_Venta (venta_id, libro_id, cantidad)
VALUES
    (1, 1, 2),  -- Carlos compra 2 copias de "Cien años de soledad" (venta 1)
    (1, 2, 1),  -- Carlos compra 1 copia de "Don Quijote de la Mancha" (venta 1)
    (2, 3, 1),  -- Ana compra 1 copia de "La sombra del viento" (venta 2)
    (3, 4, 3),  -- Juan compra 3 copias de "El código Da Vinci" (venta 3)
    (4, 5, 2),  -- Maria compra 2 copias de "Harry Potter y la piedra filosofal" (venta 4)
    (5, 3, 1),  -- Carlos compra 1 copia de "La sombra del viento" (venta 5)
    (6, 4, 2),  -- Ana compra 2 copias de "El código Da Vinci" (venta 6)
    (7, 1, 1),  -- Juan compra 1 copia de "Cien años de soledad" (venta 7)
    (8, 2, 1),  -- Maria compra 1 copia de "Don Quijote de la Mancha" (venta 8)
    (9, 5, 1),  -- Carlos compra 1 copia de "Harry Potter y la piedra filosofal" (venta 9)
    (10, 4, 3); -- Ana compra 3 copias de "El código Da Vinci" (venta 10)

-- Ej 1

DELIMITER //
CREATE FUNCTION nombreCompletoDeCliente(cliente_id_dado INT)
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
    DECLARE resultado VARCHAR(100);
    SELECT CONCAT(nombre," ",apellido) INTO resultado FROM Clientes c
    WHERE c.cliente_id = cliente_id_dado;
    RETURN resultado;
END //

DELIMITER ;

SELECT nombreCompletoDeCliente(1);

-- Ej 2

DELIMITER //
CREATE FUNCTION obtenerTotalVendidoLibro(nombreLibro VARCHAR(100))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total_vendido INT;
    SELECT SUM(d_v.cantidad) INTO total_vendido
    FROM libros l JOIN detalle_venta d_v
    ON l.libro_id = d_v.libro_id
    WHERE l.nombre_libro = nombreLibro;
    RETURN total_vendido;
END //

SELECT obtenerTotalVendidoLibro("El código Da Vinci");

-- Ej 3

DELIMITER //
CREATE FUNCTION obtenerClienteMasGastador()
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE id_clienteGastador INT;
    SELECT c.cliente_id INTO id_clienteGastador
    FROM clientes c
             JOIN ventas v
                  ON c.cliente_id = v.cliente_id
             JOIN detalle_venta d_v
                  ON v.venta_id = d_v.venta_id
             JOIN libros l
                  ON d_v.libro_id = l.libro_id
    GROUP BY c.cliente_id
    HAVING MAX(precio)
    LIMIT 1;
    RETURN id_clienteGastador;
END //

SELECT obtenerClienteMasGastador();