CREATE TABLE Clientes (
                          ClienteID INT PRIMARY KEY,
                          Nombre VARCHAR(50),
                          Apellido VARCHAR(50),
                          Email VARCHAR(100),
                          Telefono VARCHAR(15)
);
CREATE TABLE Pedidos (
                         PedidoID INT PRIMARY KEY,
                         FechaPedido DATE,
                         ClienteID INT,
                         Total_Pedido DECIMAL (10, 2),
                         FOREIGN KEY (ClienteID) REFERENCES Clientes(ClienteID)
);
CREATE TABLE Productos (
                           ProductoID INT PRIMARY KEY,
                           NombreProducto VARCHAR(100),
                           Precio DECIMAL(10, 2),
                           Stock INT
);

CREATE TABLE DetallesPedido (
                                DetalleID INT PRIMARY KEY,
                                PedidoID INT,
                                ProductoID INT,
                                Cantidad INT,
                                FOREIGN KEY (PedidoID) REFERENCES Pedidos(PedidoID),
                                FOREIGN KEY (ProductoID) REFERENCES Productos(ProductoID)
);

INSERT INTO Clientes (ClienteID, Nombre, Apellido, Email, Telefono)
VALUES
    (1, 'Juan', 'Pérez', 'juan@email.com', '123-456-7890'),
    (2, 'María', 'Gómez', 'maria@email.com', '987-654-3210'),
    (3, 'Carlos', 'López', 'carlos@email.com', '555-123-4567');
INSERT INTO Productos (ProductoID, NombreProducto, Precio)
VALUES
    (101, 'Producto 1', 10.99),
    (102, 'Producto 2', 19.99),
    (103, 'Producto 3', 5.99);
INSERT INTO Pedidos (PedidoID, FechaPedido, ClienteID)
VALUES
    (1001, '2023-10-15', 1),
    (1002, '2023-10-16', 2),
    (1003, '2023-10-17', 3),
    (1004, '2023-10-18', 1);
INSERT INTO DetallesPedido (DetalleID, PedidoID, ProductoID, Cantidad)
VALUES
    (2001, 1001, 101, 2),
    (2002, 1001, 102, 1),
    (2003, 1002, 103, 3),
    (2004, 1003, 101, 1),
    (2005, 1003, 103, 2),
    (2006, 1004, 102, 2);

create table AuditoriaClientes (
                                   AuditoriaID INT PRIMARY KEY AUTO_INCREMENT,
                                   ClienteID INT,
                                   Nombre VARCHAR(50),
                                   Apellido VARCHAR(50),
                                   Email VARCHAR(100),
                                   Telefono VARCHAR(15),
                                   TipoOperacion VARCHAR(30),
                                   FechaInsercion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table AuditoriaPedidos (
                                  AuditoriaID INT PRIMARY KEY AUTO_INCREMENT,
                                  PedidoID INT,
                                  FechaPedido DATE,
                                  ClienteID INT,
                                  Total_Pedido DECIMAL (10, 2),
                                  TipoOperacion VARCHAR(30),
                                  FechaInsercion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table AuditoriaProductos (
                                    AuditoriaID INT PRIMARY KEY AUTO_INCREMENT,
                                    ProductoID INT,
                                    NombreProducto VARCHAR(100),
                                    Precio DECIMAL(10, 2),
                                    Stock INT,
                                    TipoOperacion VARCHAR(30),
                                    FechaInsercion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ej 1

CREATE TRIGGER auditarInsercionClientes
    AFTER INSERT ON Clientes
    FOR EACH ROW
    INSERT INTO AuditoriaClientes(ClienteID, Nombre, Apellido, Email, Telefono, TipoOperacion)
    VALUES (NEW.ClienteID,NEW.Nombre,NEW.Apellido,NEW.Email,NEW.Telefono,'Insercion');

-- INSERT INTO Clientes (ClienteID, Nombre, Apellido, Email, Telefono)
-- VALUES (4,'Pedro','Fernandez','pfernandez@gmail.com','11-2222-3333');

-- Ej 2

CREATE TRIGGER auditarActualizacionClientes
    AFTER UPDATE ON Clientes
    FOR EACH ROW
    INSERT INTO AuditoriaClientes(ClienteID, Nombre, Apellido, Email, Telefono, TipoOperacion)
    VALUES (NEW.ClienteID, NEW.Nombre, NEW.Apellido, NEW.Email, NEW.Telefono, 'Modificacion');

-- UPDATE Clientes SET Nombre = 'Jose' WHERE ClienteID = 4;

-- Ej 3

CREATE TRIGGER auditarEliminacionClientes
    BEFORE DELETE ON Clientes
    FOR EACH ROW
    INSERT INTO AuditoriaClientes(ClienteID, Nombre, Apellido, Email, Telefono, TipoOperacion)
    VALUES (OLD.ClienteID,OLD.Nombre,OLD.Apellido,OLD.Email,OLD.Telefono,'Eliminacion');

-- DELETE FROM Clientes WHERE ClienteID = 4;

-- Ej 4

DELIMITER //

CREATE PROCEDURE calcularTotalPedido(IN PedidoIDBuscado INT, OUT totalPedido DECIMAL(10,2))

BEGIN
SELECT SUM(Cantidad * Precio) INTO totalPedido FROM DetallesPedido
JOIN Productos P on DetallesPedido.ProductoID = P.ProductoID
WHERE PedidoID = PedidoIDBuscado
GROUP BY PedidoID;

END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE modificarTotalPedido(IN PedidoIDBuscado INT)

BEGIN
    CALL calcularTotalPedido(PedidoIDBuscado,@nuevoTotalPedido);
    UPDATE Pedidos SET Total_Pedido = @nuevoTotalPedido
    WHERE PedidoID = PedidoIDBuscado;
END //

DELIMITER ;

CREATE TRIGGER actualizarTotalPedidoInsercion
    AFTER INSERT ON DetallesPedido
    FOR EACH ROW
    CALL modificarTotalPedido(NEW.PedidoID);

CREATE TRIGGER actualizarTotalPedidoActualizacion
    AFTER UPDATE ON DetallesPedido
    FOR EACH ROW
    CALL modificarTotalPedido(NEW.PedidoID);

-- UPDATE DetallesPedido SET Cantidad = 1 WHERE PedidoID = 1001 AND ProductoID = 102;

-- SELECT Total_Pedido FROM Pedidos WHERE PedidoID = 1001;

-- Ej 5

DELIMITER //

CREATE PROCEDURE validarStockProducto(IN ProductoIDValidar INT, IN CantidadSolicitada INT)
BEGIN
    DECLARE stockProducto INT;
    SELECT Stock INTO stockProducto FROM Productos WHERE ProductoID = ProductoIDValidar;

    IF stockProducto - CantidadSolicitada < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El stock del producto pedido es menor al disponible';
    ELSEIF CantidadSolicitada = 0 THEN
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'El stock del producto pedido no puede ser 0';
    END IF;

END //

DELIMITER ;

CREATE TRIGGER validarStockInsercion
    BEFORE INSERT ON DetallesPedido
    FOR EACH ROW
    CALL validarStockProducto(NEW.ProductoID,NEW.Cantidad);

-- Ej 6

DELIMITER //

CREATE PROCEDURE validarPrecioProducto(IN Precio DECIMAL(10,2))
BEGIN
    IF Precio < 0 THEN
        SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'El precio no puede ser negativo';
    END IF;

END //

DELIMITER ;

CREATE TRIGGER validarPrecioInsercion
    BEFORE INSERT ON Productos
    FOR EACH ROW
    CALL validarPrecioProducto(NEW.Precio);

CREATE TRIGGER validarPrecioActualizacion
    BEFORE UPDATE ON Productos
    FOR EACH ROW
    CALL validarPrecioProducto(NEW.Precio);

-- Ej 7


CREATE TRIGGER auditarInsercionPedidos
    AFTER INSERT ON Pedidos
    FOR EACH ROW
    INSERT INTO AuditoriaPedidos(PedidoID, FechaPedido, ClienteID, Total_Pedido, TipoOperacion)
    VALUES (NEW.PedidoID,NEW.FechaPedido,NEW.ClienteID,NEW.Total_Pedido,'Insercion');

-- Ej 8

DELIMITER //

CREATE TRIGGER auditarActualizacionPedidos
    AFTER UPDATE ON Pedidos
    FOR EACH ROW
BEGIN
    CALL calcularTotalPedido(NEW.PedidoID,@nuevoTotalPedido);
    INSERT INTO AuditoriaPedidos(PedidoID, FechaPedido, ClienteID, Total_Pedido, TipoOperacion)
    VALUES (NEW.PedidoID,NEW.FechaPedido,NEW.ClienteID,@nuevoTotalPedido,'Modificacion');
END //

DELIMITER ;

-- Ej 9

DELIMITER //

CREATE TRIGGER auditarEliminacionPedidos
    BEFORE DELETE
    ON Pedidos
    FOR EACH ROW
BEGIN
    DELETE FROM DetallesPedido WHERE PedidoID = OLD.PedidoID;
    INSERT INTO AuditoriaPedidos(PedidoID, FechaPedido, ClienteID, Total_Pedido, TipoOperacion)
    VALUES (OLD.PedidoID, OLD.FechaPedido, OLD.ClienteID, OLD.Total_Pedido, 'Eliminacion');
END //

DELIMITER ;

-- DELETE FROM Pedidos WHERE PedidoID = 1001;

-- Ej 10

DELIMITER //

CREATE PROCEDURE actualizarStockProducto(IN ProductoIDActualizar INT, IN CantidadSolicitada INT)
BEGIN
    CALL validarStockProducto(ProductoIDActualizar,CantidadSolicitada);
    UPDATE Productos SET Stock = Stock - cantidadSolicitada
    WHERE ProductoID = ProductoIDActualizar;
END //

DELIMITER ;

CREATE TRIGGER actualizarStockProductosInsercion
    AFTER INSERT ON DetallesPedido
    FOR EACH ROW
    CALL actualizarStockProducto(NEW.ProductoID,NEW.Cantidad);

CREATE TRIGGER actualizarStockProductosActualizacion
    BEFORE UPDATE ON DetallesPedido
    FOR EACH ROW
    CALL actualizarStockProducto(NEW.ProductoID,NEW.Cantidad - OLD.Cantidad);

DROP TRIGGER actualizarStockProductosActualizacion;

-- Ej 12

CREATE TRIGGER auditarInsercionProductos
    AFTER INSERT ON Productos
    FOR EACH ROW
    INSERT INTO AuditoriaProductos(ProductoID, NombreProducto, Precio, Stock, TipoOperacion)
    VALUES (NEW.ProductoID,NEW.NombreProducto,NEW.Precio,NEW.Stock,'Insercion');

-- Ej 13

CREATE TRIGGER auditarActualizacionProductos
    AFTER UPDATE ON Productos
    FOR EACH ROW
    INSERT INTO AuditoriaProductos(ProductoID, NombreProducto, Precio, Stock, TipoOperacion)
    VALUES (NEW.ProductoID,NEW.NombreProducto,NEW.Precio,NEW.Stock,'Modificacion');

-- Ej 14

CREATE TRIGGER auditarEliminacionProductos
    BEFORE DELETE ON Productos
    FOR EACH ROW
    INSERT INTO AuditoriaProductos(ProductoID, NombreProducto, Precio, Stock, TipoOperacion)
    VALUES (OLD.ProductoID,OLD.NombreProducto,OLD.Precio,OLD.Stock,'Eliminacion');

-- Ej 15

CREATE TRIGGER actualizarStockProductosEliminacion
    AFTER DELETE ON DetallesPedido
    FOR EACH ROW
    UPDATE Productos SET Stock = Stock + OLD.Cantidad
    WHERE ProductoID = OLD.ProductoID;

-- DELETE FROM DetallesPedido WHERE ProductoID = 101 AND PedidoID = 1003;


