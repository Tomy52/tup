package Controller.Implementation;

import Model.Implementation.Users.User;
import Model.Implementation.Users.UserDAO;

import java.util.Optional;

public class UserController {
    private final UserDAO dao;

    public UserController() {
        dao = new UserDAO();
    }

    public void agregar(String nombre, String apellido, String dni, String email) {
        User usuarioNuevo = new User();

        usuarioNuevo.setNombre(nombre);
        usuarioNuevo.setApellido(apellido);
        usuarioNuevo.setDni(dni);
        usuarioNuevo.setEmail(email);

        dao.agregar(usuarioNuevo);
    }

    public Optional<User> login(String username, String password) {
        return dao.validarCredenciales(username, password);
    }

//    public List<Alumno> listarAlumnos() {
//        return dao.obtenerTodos();
//    }
//
//    public void eliminarAlumno(int id) {
//        dao.eliminar(id);
//    }
}
