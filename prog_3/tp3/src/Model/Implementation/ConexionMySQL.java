package Model.Implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static ConexionMySQL instancia;
    private Connection conexion;
    private static final String usuario = "root";
    private static final String pass = "1234";
    private static final String nombre_bdd = "tp3_prg3";
    private static final String url = "jdbc:mysql://localhost:3306/" + nombre_bdd;

    private ConexionMySQL() {
        try {
            conexion = DriverManager.getConnection(url, usuario,pass);
        } catch (SQLException e) {
            System.out.println("Error conectando a la bdd: " + e.getMessage());
        }
    }

    public static ConexionMySQL getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }

        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}
