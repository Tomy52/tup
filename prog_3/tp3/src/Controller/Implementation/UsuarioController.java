package Controller.Implementation;

import Controller.Exception.NoAutorizadoException;
import Controller.Exception.NoEncontradoException;
import Model.Implementation.Usuario.Usuario;
import Model.Implementation.Usuario.UsuarioDao;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsuarioController {
    private final UsuarioDao dao;
    private Optional<Usuario> logueado = Optional.empty();

    public UsuarioController() {
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

    public void iniciarSesion(String username, String pass) throws CredentialException {
        logueado = dao.iniciarSesion(username,pass);
        if (logueado.isEmpty()) throw new CredentialException("El usuario y/o la contrasenia son incorrectos");
    }

    public Usuario obtenerLogueado() {
        return Optional.of(logueado).get().orElse(new Usuario());
    }

    public List<Usuario> obtenerTodos() throws NoAutorizadoException {
        if (obtenerLogueado().getNivelPermisos().toString().equals("CLIENTE")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }
        return dao.obtenerTodos();
    }

    private Optional<Usuario> obtener(int id_usuario) {
        return dao.obtenerUsuario(id_usuario);
    }

    public Usuario buscar(String dni, String email) throws NoAutorizadoException {
        if (obtenerLogueado().getNivelPermisos().toString().equals("CLIENTE")) {
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

    private boolean esCliente(int id_usuario) {
        Usuario usuarioModificar = Optional.of(obtener(id_usuario)).get().orElse(new Usuario());
        return usuarioModificar.getNivelPermisos().toString().equals("CLIENTE");
    }

    public void modificar(String parametro, String valor, int id_usuario) throws NoAutorizadoException {
        String nivelUsuario = obtenerLogueado().getNivelPermisos().toString();
        dao.modificarUsuario(parametro,valor,id_usuario);
        if (parametro.equals("permiso") && !nivelUsuario.equals("ADMINISTRADOR")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }
        if (id_usuario != obtenerLogueado().getId_usuario() && nivelUsuario.equals("CLIENTE")) {
            throw new NoAutorizadoException("Los clientes solo pueden modificar datos propios");
        }
        if (nivelUsuario.equals("GESTOR") && !esCliente(id_usuario)) {
            throw new NoAutorizadoException("Los gestores solo pueden modificar clientes");
        }
    }

//
//    public void eliminarAlumno(int id) {
//        dao.eliminar(id);
//    }

}
