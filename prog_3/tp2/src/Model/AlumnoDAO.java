package Model;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    private final Connection connection;

    public AlumnoDAO() {
        connection = ConexionMySQL.getInstance().getConnection();
    }

    public void agregar(String nombre, String apellido, int edad, String email) {
        String insertAlumnoQuery = "INSERT INTO alumnos (nombre, apellido, edad, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertAlumnoQuery)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setInt(3, edad);
            statement.setString(4, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error agregando nuevo alumno: " + e.getMessage());
        }
    }

    public void eliminar(int idAlumnoEliminar) {
        String removeAlumnoQuery = "DELETE FROM alumnos WHERE id=" + idAlumnoEliminar;

        try (Statement statement = connection.createStatement()) {
            statement.execute(removeAlumnoQuery);
        } catch (SQLException e) {
            System.out.println("Error removiendo alumno id = " + idAlumnoEliminar + " : " + e.getMessage());
        }
    }

    public void actualizarEdad(int id, int nuevaEdad) {
        String updateEdadQuery = "UPDATE alumnos SET edad = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateEdadQuery)) {
            statement.setInt(1, nuevaEdad);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error actualizando edad del alumno: " + e.getMessage());
        }
    }

    public List<Alumno> obtenerTodos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";

        try (Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(sql)) {
            while (resultados.next()) {
                Alumno direccionAgregar = new Alumno(resultados.getInt("id"),
                        resultados.getString("nombre"),
                        resultados.getString("apellido"),
                        resultados.getInt("edad"),
                        resultados.getString("email"));
                lista.add(direccionAgregar);
            }
        } catch (SQLException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
        }

        return lista;
    }
}
