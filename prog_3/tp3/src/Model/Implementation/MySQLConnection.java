package Model.Implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;
    private static final String user = "root";
    private static final String pass = "1234";
    private static final String db_name = "tp3_prg3";
    private static final String url = "jdbc:mysql://localhost:3306/" + db_name;

    static {
        createDatabaseIfNotExists();
        createTablesIfNotExists();
    }

    private MySQLConnection() {
        try {
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            System.out.println("Error conectando a la bdd: " + e.getMessage());
        }
    }

    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private static void createDatabaseIfNotExists() {
//        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + db_name;
//        try (Connection conn = DriverManager.getConnection(url, user, pass);
//                Statement statement = conn.createStatement()) {
//            statement.executeUpdate(createDatabaseQuery);
//        } catch (SQLException e) {
//            System.out.println("Error creando la base de datos: " + e.getMessage());
//        }
    }

    private static void createTablesIfNotExists() {
//        String createAlumnosTableQuery = "CREATE TABLE IF NOT EXISTS alumnos(" +
//                "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
//                "nombre VARCHAR(50) NOT NULL," +
//                "apellido VARCHAR(50) NOT NULL," +
//                "edad INTEGER NOT NULL," +
//                "email VARCHAR(100) UNIQUE NOT NULL)";
//
//        String createDireccionesTableQuery = "CREATE TABLE IF NOT EXISTS direcciones(" +
//                "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
//                "calle VARCHAR(50) NOT NULL," +
//                "altura INTEGER NOT NULL," +
//                "alumno_id INTEGER NOT NULL," +
//                "FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE CASCADE)";
//
//        try (Connection conn = DriverManager.getConnection(url, user, pass);
//             Statement statement = conn.createStatement()) {
//            statement.executeUpdate(createAlumnosTableQuery);
//            statement.executeUpdate(createDireccionesTableQuery);
//        } catch (SQLException e) {
//            System.out.println("Error creando las tablas: " + e.getMessage());
//        }
    }
}
