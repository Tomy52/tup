package Model.Implementation.Usuario;

import Model.Implementation.ConexionMySQL;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDao {

    private final Connection conexion;

    public UsuarioDao() {
        conexion = ConexionMySQL.getInstancia().getConexion();
    }

    public void agregar(Usuario usuario) {
        String agregarUsuarioQuery = "CALL agregarUsuario(?,?,?,?)";
        try (PreparedStatement statement = conexion.prepareStatement(agregarUsuarioQuery)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getDni());
            statement.setString(4, usuario.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error agregando nuevo usuario: " + e.getMessage());
        }
    }

    public Optional<Usuario> iniciarSesion(String username, String pass) {
        Optional<Usuario> resultado = Optional.empty();
        String iniciarSesionQuery = "CALL login(?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(iniciarSesionQuery)) {
            statement.setString(1,username);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                resultado = Optional.of(mapearUsuario(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error iniciando sesion: " + e.getMessage());
        }

        return resultado;
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(resultSet.getInt("id_usuario"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setApellido(resultSet.getString("apellido"));
        usuario.setDni(resultSet.getString("dni"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setFecha_creacion(resultSet.getString("fecha_creacion"));
        usuario.setNivelPermisos(resultSet.getString("permiso"));
        return usuario;
    }

    public Optional<Usuario> obtenerUsuario(int id_usuario) {
        Optional<Usuario> resultado = Optional.empty();
        String obtenerUsuarioQuery = "CALL buscarUsuario(?)";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerUsuarioQuery)) {
            statement.setInt(1,id_usuario);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                resultado = Optional.of(mapearUsuario(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuario: " + e.getMessage());
        }

        return resultado;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String obtenerUsuariosQuery = "SELECT * FROM usuariosCredenciales";

        try (Statement statement = conexion.createStatement();
             ResultSet resultados = statement.executeQuery(obtenerUsuariosQuery)) {
            while (resultados.next()) {
                lista.add(mapearUsuario(resultados));
            }
        } catch (SQLException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
        }

        return lista;
    }

    public void modificarUsuario(String parametro, String valor, int id_usuario) {
        String modificarUsuarioQuery = "UPDATE usuariosCredenciales SET " + parametro + " = ? WHERE id_usuario = ?";

        try (PreparedStatement statement = conexion.prepareStatement(modificarUsuarioQuery)) {
            statement.setString(1,valor);
            statement.setInt(2,id_usuario);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error modificando usuario: " + e.getMessage());
        }
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

}
