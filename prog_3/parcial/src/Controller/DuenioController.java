package Controller;

import Controller.Exception.DuenioNoExisteException;
import Model.Implementation.Duenio.Duenio;
import Model.Implementation.Duenio.DuenioDao;

import java.util.List;
import java.util.Optional;

public class DuenioController {
    private final DuenioDao dao;

    public DuenioController() {
        this.dao = new DuenioDao();
    }

    public void agregar(String nombreCompleto, String dni, String telefono) {
        Duenio duenioAgregar = new Duenio();

        duenioAgregar.setNombreCompleto(nombreCompleto);
        duenioAgregar.setDni(dni);
        duenioAgregar.setTelefono(telefono);

        dao.agregar(duenioAgregar);
    }

    public Duenio obtenerUno(int idDuenio) throws DuenioNoExisteException {
        Optional<Duenio> duenio = dao.obtenerUno(idDuenio);

        if (duenio.isEmpty()) {
            throw new DuenioNoExisteException("El duenio buscado no existe");
        }

        return duenio.get();
    }

    public List<Duenio> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public void modificar(int id, String nombreCompleto, String dni, String telefono) {
        try {
            obtenerUno(id);
            Duenio duenioModificar = new Duenio();

            duenioModificar.setNombreCompleto(nombreCompleto);
            duenioModificar.setDni(dni);
            duenioModificar.setTelefono(telefono);

            dao.modificar(id, duenioModificar);
        } catch (DuenioNoExisteException e) {
            System.out.println("Error modificando duenio: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            obtenerUno(id);
            dao.eliminar(id);
        } catch (DuenioNoExisteException e) {
            System.out.println("Error eliminando duenio: " + e.getMessage());
        }
    }
}
