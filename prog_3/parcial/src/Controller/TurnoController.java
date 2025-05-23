package Controller;

import Controller.Exception.*;
import Model.Implementation.Turno.EstadoTurno;
import Model.Implementation.Turno.Turno;
import Model.Implementation.Turno.TurnoDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class TurnoController {
    private final TurnoDao dao;
    private final MascotaController mascotaController;
    private final VeterinarioController veterinarioController;

    public TurnoController(MascotaController mascotaController, VeterinarioController veterinarioController) {
        this.dao = new TurnoDao();
        this.mascotaController = mascotaController;
        this.veterinarioController = veterinarioController;
    }

    public void agregar(int idMascota, int idVeterinario, Timestamp fechaHora) {
        try {
            Turno turnoAgregar = new Turno();

            turnoAgregar.setIdMascota(idMascota);
            turnoAgregar.setIdVeterinario(idVeterinario);
            turnoAgregar.setFechaHora(fechaHora);
            turnoAgregar.setEstado(EstadoTurno.PENDIENTE);

            validarExistenciaMascota(idMascota);
            validarExistenciaVeterinario(idVeterinario);
            validarFechaPosteriorActual(fechaHora);
            validarFechaNuevaNoSuperpongaOtra(idVeterinario,fechaHora);

            dao.agregar(turnoAgregar);
        } catch (TurnoInvalidoException e) {
            System.out.println("Error agregando turno: " + e.getMessage());
        }

    }

    public Turno obtenerUno(int turnoId) throws TurnoNoExisteException {
        Optional<Turno> turno = dao.obtenerUno(turnoId);

        if (turno.isEmpty()) {
            throw new TurnoNoExisteException("El turno buscado no existe");
        }

        return turno.get();
    }

    public List<Turno> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public void modificar(int id, int idMascota, int idVeterinario, Timestamp fechaHora, EstadoTurno estado) {
        try {
            Turno datosTurnoActual = obtenerUno(id);
            Turno turnoModificar = new Turno();

            turnoModificar.setIdMascota(idMascota);
            turnoModificar.setIdVeterinario(idVeterinario);
            turnoModificar.setFechaHora(fechaHora);
            turnoModificar.setEstado(estado);

            validarExistenciaMascota(idMascota);
            validarExistenciaVeterinario(idVeterinario);
            validarFechaPosteriorActual(fechaHora);
            validarFechaModificadaNoSuperpongaOtra(id,idVeterinario, fechaHora);
            validarCambioDeEstado(datosTurnoActual.getEstado(),estado);

            dao.modificar(id, turnoModificar);
        } catch (TurnoInvalidoException | CambioEstadoInvalidoException | TurnoNoExisteException e) {
            System.out.println("Error modificando turno: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            obtenerUno(id);
            dao.eliminar(id);
        } catch (TurnoNoExisteException e) {
            System.out.println("Error eliminando turno: " + e.getMessage());
        }
    }

    public void finalizarTurno(int id) {
        try {
            Turno turno = obtenerUno(id);
            validarCambioDeEstado(turno.getEstado(),EstadoTurno.REALIZADO);

            modificar(id, turno.getIdMascota(), turno.getIdVeterinario(), turno.getFechaHora(), EstadoTurno.REALIZADO);
        } catch (CambioEstadoInvalidoException | TurnoNoExisteException e) {
            System.out.println("Error finalizando turno: " + e.getMessage());
        }
    }

    public void cancelarTurno(int id) {
        try {
            Turno turno = obtenerUno(id);
            validarCambioDeEstado(turno.getEstado(),EstadoTurno.CANCELADO);

            modificar(id, turno.getIdMascota(), turno.getIdVeterinario(), turno.getFechaHora(), EstadoTurno.CANCELADO);
        } catch (CambioEstadoInvalidoException | TurnoNoExisteException e) {
            System.out.println("Error cancelando turno: " + e.getMessage());
        }
    }

    public List<Turno> verPorEstado(EstadoTurno estado) {
        List<Turno> turnos = obtenerTodos();

        return turnos.stream()
                .filter(turno -> turno.getEstado().equals(estado))
                .toList();
    }

    public List<Turno> verPorMascota(int idMascota) {
        List<Turno> turnos = obtenerTodos();

        return turnos.stream()
                .filter(turno -> turno.getIdMascota() == idMascota)
                .toList();
    }

    private void validarExistenciaVeterinario(int idVeterinario) throws TurnoInvalidoException {
        try {
            veterinarioController.obtenerUno(idVeterinario);
        } catch (VeterinarioNoExisteException e) {
            throw new TurnoInvalidoException("El veterinario no existe");
        }
    }

    private void validarExistenciaMascota(int idMascota) throws TurnoInvalidoException {
        try {
            mascotaController.obtenerUno(idMascota);
        } catch (MascotaNoExisteException e) {
            throw new TurnoInvalidoException("La mascota no existe");
        }
    }

    private void validarFechaPosteriorActual(Timestamp fechaHora) throws TurnoInvalidoException {
        if (fechaHora.before(Timestamp.from(Instant.now()))) {
            throw new TurnoInvalidoException("La fecha del turno debe ser posterior a la actual");
        }
    }

    private void validarFechaNuevaNoSuperpongaOtra(int idVeterinario, Timestamp fechaHora) throws TurnoInvalidoException {
        List<Turno> turnos = obtenerTodos();

        for (Turno turno: turnos) {
            if (idVeterinario == turno.getIdVeterinario() && fechaHora.compareTo(turno.getFechaHora()) == 0
                    && turno.getEstado().equals(EstadoTurno.PENDIENTE)) {
                throw new TurnoInvalidoException("Ya existe un turno pendiente con ese veterinario en la misma fecha y hora");
            }
        }
    }

    private void validarFechaModificadaNoSuperpongaOtra(int idTurno, int idVeterinario, Timestamp fechaHora) throws TurnoInvalidoException {
        List<Turno> turnos = obtenerTodos();

        for (Turno turno: turnos) {
            if (idTurno != turno.getId() && idVeterinario == turno.getIdVeterinario() && fechaHora.compareTo(turno.getFechaHora()) == 0
                    && turno.getEstado().equals(EstadoTurno.PENDIENTE)) {
                throw new TurnoInvalidoException("Ya existe un turno pendiente con ese veterinario en la misma fecha y hora");
            }
        }
    }

    private void validarCambioDeEstado(EstadoTurno estadoActual, EstadoTurno estadoNuevo) throws CambioEstadoInvalidoException {
        boolean valido = false;

        if (estadoActual.equals(EstadoTurno.PENDIENTE) && estadoNuevo.equals(EstadoTurno.PENDIENTE)) valido = true;
        if (estadoActual.equals(EstadoTurno.PENDIENTE) && estadoNuevo.equals(EstadoTurno.REALIZADO)) valido = true;
        if (estadoActual.equals(EstadoTurno.PENDIENTE) && estadoNuevo.equals(EstadoTurno.CANCELADO)) valido = true;

        if (!valido) throw new CambioEstadoInvalidoException("Solo se puede modificar el estado de turnos pendientes");
    }
}
