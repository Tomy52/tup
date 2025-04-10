package Model.Implementation.Users;

import Model.Implementation.MySQLConnection;

import java.sql.Connection;
import java.sql.*;
import java.util.Optional;

public class UserDAO {

    private final Connection connection;

    public UserDAO() {
        connection = MySQLConnection.getInstance().getConnection();
    }

    public void agregar(User user) {
        String addUserQuery = "CALL agregarUsuario(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(addUserQuery)) {
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getApellido());
            statement.setString(3, user.getDni());
            statement.setString(4, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error agregando nuevo usuario: " + e.getMessage());
        }
    }

    public Optional<User> validarCredenciales(String username, String password) {
        String verifyCredentialsQuery = "CALL verificarCredenciales(?, ?)";
        Optional<User> user = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(verifyCredentialsQuery)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User result = new User();
                    result.setId_usuario(resultSet.getInt("id_usuario"));
                    result.setNombre(resultSet.getString("nombre"));
                    result.setApellido(resultSet.getString("apellido"));
                    result.setDni(resultSet.getString("dni"));
                    result.setEmail(resultSet.getString("email"));
                    result.setFecha_creacion(resultSet.getString("fecha_creacion"));
                    user = Optional.of(result);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error validando credenciales: " + e.getMessage());
        }
        return user;
    }

//    public void eliminar(int idAlumnoEliminar) {
//        String removeAlumnoQuery = "DELETE FROM alumnos WHERE id=" + idAlumnoEliminar;
//
//        try (Statement statement = connection.createStatement()) {
//            statement.execute(removeAlumnoQuery);
//        } catch (SQLException e) {
//            System.out.println("Error removiendo alumno id = " + idAlumnoEliminar + " : " + e.getMessage());
//        }
//    }
//
//    public void actualizarEdad(int id, int nuevaEdad) {
//        String updateEdadQuery = "UPDATE alumnos SET edad = ? WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(updateEdadQuery)) {
//            statement.setInt(1, nuevaEdad);
//            statement.setInt(2, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Error actualizando edad del alumno: " + e.getMessage());
//        }
//    }
//
//    public List<Alumno> obtenerTodos() {
//        List<Alumno> lista = new ArrayList<>();
//        String sql = "SELECT * FROM alumnos";
//
//        try (Statement statement = connection.createStatement();
//             ResultSet resultados = statement.executeQuery(sql)) {
//            while (resultados.next()) {
//                Alumno direccionAgregar = new Alumno(resultados.getInt("id"),
//                        resultados.getString("nombre"),
//                        resultados.getString("apellido"),
//                        resultados.getInt("edad"),
//                        resultados.getString("email"));
//                lista.add(direccionAgregar);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error leyendo usuarios: " + e.getMessage());
//        }
//
//        return lista;
//    }
}
