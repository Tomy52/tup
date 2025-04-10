package Controller;

import Model.Alumno;
import Model.AlumnoDAO;

import java.util.List;

public class AlumnoController {
    private final AlumnoDAO dao;

    public AlumnoController() {
        dao = new AlumnoDAO();
    }

    public void agregarAlumno(String nombre, String apellido, int edad, String email) {
        dao.agregar(nombre,apellido,edad,email);
    }

    public List<Alumno> listarAlumnos() {
        return dao.obtenerTodos();
    }

    public void eliminarAlumno(int id) {
        dao.eliminar(id);
    }
}
