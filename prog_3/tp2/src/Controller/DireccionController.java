package Controller;

import Model.Direccion;
import Model.DireccionDAO;

import java.util.List;

public class DireccionController {
    private final DireccionDAO dao;

    public DireccionController() {
        dao = new DireccionDAO();
    }

    public void agregarDireccion(String calle, int altura, int alumno_id) {
        dao.agregar(calle,altura,alumno_id);
    }

    public List<Direccion> listarDirecciones() {
        return dao.obtenerTodos();
    }

    public void eliminarDireccion(int id) {
        dao.eliminar(id);
    }
}
