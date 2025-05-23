package Controller;

import Controller.Exception.DuenioNoExisteException;
import Controller.Exception.MascotaInvalidaException;
import Controller.Exception.MascotaNoExisteException;
import Model.Implementation.Duenio.Duenio;
import Model.Implementation.Mascota.Mascota;
import Model.Implementation.Mascota.MascotaDao;

import java.util.List;
import java.util.Optional;

public class MascotaController {
    private final MascotaDao dao;
    private final DuenioController duenioController;

    public MascotaController(DuenioController duenioController) {
        this.duenioController = duenioController;
        this.dao = new MascotaDao();
    }

    public void agregar(String nombre, String especie, String raza, int edad, int idDuenio) {
        Mascota mascotaAgregar = new Mascota();

        try {
            mascotaAgregar.setNombre(nombre);
            mascotaAgregar.setEspecie(especie);
            mascotaAgregar.setRaza(raza);
            mascotaAgregar.setEdad(edad);
            mascotaAgregar.setIdDuenio(idDuenio);

            validarExistenciaDuenio(idDuenio);
            validarEdad(edad);

            dao.agregar(mascotaAgregar);
        } catch (MascotaInvalidaException e) {
            System.out.println("Error agregando mascota: " + e.getMessage());
        }
    }

    public Mascota obtenerUno(int idMascota) throws MascotaNoExisteException {
        Optional<Mascota> mascota = dao.obtenerUno(idMascota);

        if (mascota.isEmpty()) {
            throw new MascotaNoExisteException("La mascota buscada no existe");
        }

        return mascota.get();
    }

    public List<Mascota> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public void modificar(int id, String nombre, String especie, String raza, int edad, int idDuenio) {
        try {
            obtenerUno(id);

            Mascota mascotaModificar = new Mascota();

            mascotaModificar.setNombre(nombre);
            mascotaModificar.setEspecie(especie);
            mascotaModificar.setRaza(raza);
            mascotaModificar.setEdad(edad);
            mascotaModificar.setIdDuenio(idDuenio);

            validarExistenciaDuenio(idDuenio);
            validarEdad(edad);

            dao.modificar(id, mascotaModificar);
        } catch (MascotaInvalidaException | MascotaNoExisteException e) {
            System.out.println("Error modificando mascota: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            obtenerUno(id);
            dao.eliminar(id);
        } catch (MascotaNoExisteException e) {
            System.out.println("Error eliminando mascota: " + e.getMessage());
        }
    }

    public List<Mascota> verPorDuenio(int idDuenio) {
        List<Mascota> mascotas = dao.obtenerTodos();

        return mascotas.stream()
                .filter(mascota -> mascota.getIdDuenio() == idDuenio)
                .toList();
    }

    public String verInfoDuenioMascota(int idDuenio) {
        String mensaje = "";
        try {
            Duenio duenioMascota = duenioController.obtenerUno(idDuenio);
            mensaje = duenioMascota.mostrarNombreYTelefono();
        } catch (DuenioNoExisteException e) {
            System.out.println("Error mostrando informacion del duenio de la mascota: " + e.getMessage());
        }

        return mensaje;
    }

    private void validarExistenciaDuenio(int idDuenio) throws MascotaInvalidaException {
        try {
            duenioController.obtenerUno(idDuenio);
        } catch (DuenioNoExisteException e) {
            throw new MascotaInvalidaException("El duenio no existe");
        }
    }

    private void validarEdad(int edad) throws MascotaInvalidaException {
        if (edad < 0 || edad > 120) {
            throw new MascotaInvalidaException("La edad es invalida");
        }
    }
}
