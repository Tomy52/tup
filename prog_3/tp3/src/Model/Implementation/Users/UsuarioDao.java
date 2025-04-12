package Model.Implementation.Users;

import Model.Implementation.MySQLConnection;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDao {

    private final Connection connection;

    public UsuarioDao() {
        connection = MySQLConnection.getInstance().getConnection();
    }

    public void agregar(Usuario usuario) {
        String addUserQuery = "CALL agregarUsuario(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(addUserQuery)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getDni());
            statement.setString(4, usuario.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error agregando nuevo usuario: " + e.getMessage());
        }
    }

    public Optional<Usuario> iniciarSesion(String user, String pass) {
        Optional<Usuario> resultado = Optional.empty();
        String loginQuery = "CALL login(?,?)";

        try (PreparedStatement statement = connection.prepareStatement(loginQuery)) {
            statement.setString(1,user);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Usuario usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId_usuario(resultSet.getInt("id_usuario"));
                usuarioEncontrado.setNombre(resultSet.getString("nombre"));
                usuarioEncontrado.setApellido(resultSet.getString("apellido"));
                usuarioEncontrado.setDni(resultSet.getString("dni"));
                usuarioEncontrado.setEmail(resultSet.getString("email"));
                usuarioEncontrado.setFecha_creacion(resultSet.getString("fecha_creacion"));
                usuarioEncontrado.setNivelPermisos(resultSet.getString("permiso"));

                resultado = Optional.of(usuarioEncontrado);
            }
        } catch (SQLException e) {
            System.out.println("Error iniciando sesion: " + e.getMessage());
        }

        return resultado;
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
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuariosCredenciales";

        try (Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(sql)) {
            while (resultados.next()) {
                Usuario usuarioAgregar = new Usuario();
                usuarioAgregar.setId_usuario(resultados.getInt("id_usuario"));
                usuarioAgregar.setNombre(resultados.getString("nombre"));
                usuarioAgregar.setApellido(resultados.getString("apellido"));
                usuarioAgregar.setDni(resultados.getString("dni"));
                usuarioAgregar.setEmail(resultados.getString("email"));
                usuarioAgregar.setFecha_creacion(resultados.getString("fecha_creacion"));
                usuarioAgregar.setNivelPermisos(resultados.getString("permiso"));
                lista.add(usuarioAgregar);
            }
        } catch (SQLException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
        }

        return lista;
    }

    public void modificarUsuario(String parametro, String valor, int id_usuario) {
        String updateUserQuery = "UPDATE usuariosCredenciales SET " + parametro + " = ? WHERE id_usuario = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateUserQuery)) {
            statement.setString(1,valor);
            statement.setInt(2,id_usuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error modificando usuario: " + e.getMessage());
        }
    }


}
