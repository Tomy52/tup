package Controller.Implementation;

import Controller.Exception.NoAutorizadoException;
import Controller.Exception.NoEncontradoException;
import Model.Implementation.Users.Usuario;
import Model.Implementation.Users.UsuarioDao;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserController {
    private final UsuarioDao dao;
    private Optional<Usuario> usuarioLogueado = Optional.empty();

    public UserController() {
        dao = new UsuarioDao();
    }

    public void agregar(String nombre, String apellido, String dni, String email) {
        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setNombre(nombre);
        usuarioNuevo.setApellido(apellido);
        usuarioNuevo.setDni(dni);
        usuarioNuevo.setEmail(email);

        dao.agregar(usuarioNuevo);
    }

    public void iniciarSesion(String usuario, String pass) throws CredentialException {
        usuarioLogueado = dao.iniciarSesion(usuario,pass);
        if (usuarioLogueado.isEmpty()) throw new CredentialException("El usuario y/o la contrasenia son incorrectos");
    }

    public Usuario obtenerUsuarioLogueado() {
        return Optional.of(usuarioLogueado).get().orElse(new Usuario());
    }

    public List<Usuario> obtenerTodos() throws NoAutorizadoException {
        if (obtenerUsuarioLogueado().getNivelPermisos().toString().equals("CLIENTE")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }
        return dao.obtenerTodos();
    }

    public Usuario buscarUsuario(String dni, String email) throws NoAutorizadoException {
        if (obtenerUsuarioLogueado().getNivelPermisos().toString().equals("CLIENTE")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }
        List<Usuario> resultados = dao.obtenerTodos().stream()
                .filter(usuario -> Objects.equals(usuario.getDni(), dni) ||
                        Objects.equals(usuario.getEmail(),email))
                .toList();

        if (resultados.isEmpty()) {
            throw new NoEncontradoException("No se pudo encontrar ningun usuario");
        }

        return resultados.getFirst();
    }

    public void modificarUsuario(String parametro, String valor, int id_usuario) throws NoAutorizadoException {
        String nivelUsuario = obtenerUsuarioLogueado().getNivelPermisos().toString();
        dao.modificarUsuario(parametro,valor,id_usuario);
        if (parametro.equals("permiso") && !nivelUsuario.equals("ADMINISTRADOR")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }
        if (id_usuario != obtenerUsuarioLogueado().getId_usuario() && nivelUsuario.equals("CLIENTE")) {
            throw new NoAutorizadoException("Los clientes solo pueden modificar datos propios");
        }
        if (nivelUsuario.equals("GESTOR")) {
            // a completar
        }
    }
//
//    public void eliminarAlumno(int id) {
//        dao.eliminar(id);
//    }

}
