package Controller;

import Controller.Exception.VeterinarioNoExisteException;
import Model.Implementation.Veterinario.EspecialidadVeterinario;
import Model.Implementation.Veterinario.Veterinario;
import Model.Implementation.Veterinario.VeterinarioDao;

import java.util.List;
import java.util.Optional;

public class VeterinarioController {
    private final VeterinarioDao dao;

    public VeterinarioController() {
        this.dao = new VeterinarioDao();
    }

    public void agregar(String nombre, String matricula, EspecialidadVeterinario especialidad) {
        Veterinario veterinarioAgregar = new Veterinario();

        veterinarioAgregar.setNombre(nombre);
        veterinarioAgregar.setMatricula(matricula);
        veterinarioAgregar.setEspecialidad(especialidad);

        dao.agregar(veterinarioAgregar);
    }

    public Veterinario obtenerUno(int idVeterinario) throws VeterinarioNoExisteException {
        Optional<Veterinario> veterinario = dao.obtenerUno(idVeterinario);

        if (veterinario.isEmpty()) {
            throw new VeterinarioNoExisteException("El veterinario buscado no existe");
        }

        return veterinario.get();
    }

    public List<Veterinario> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public void modificar(int id, String nombre, String matricula, EspecialidadVeterinario especialidad) {
        try {
            obtenerUno(id);
            Veterinario veterinarioModificar = new Veterinario();

            veterinarioModificar.setNombre(nombre);
            veterinarioModificar.setMatricula(matricula);
            veterinarioModificar.setEspecialidad(especialidad);

            dao.modificar(id, veterinarioModificar);
        } catch (VeterinarioNoExisteException e) {
            System.out.println("Error modificando veterinario: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            obtenerUno(id);
            dao.eliminar(id);
        } catch (VeterinarioNoExisteException e) {
            System.out.println("Error eliminando veterinario: " + e.getMessage());
        }
    }

    public List<Veterinario> verPorEspecialidad(EspecialidadVeterinario especialidad) {
        List<Veterinario> veterinarios = dao.obtenerTodos();

        return veterinarios.stream()
                .filter(veterinario -> veterinario.getEspecialidad().equals(especialidad))
                .toList();
    }
}
