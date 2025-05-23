package Model.Implementation.Turno;

import java.sql.Timestamp;

public class Turno {
    private int id;
    private int idMascota;
    private int idVeterinario;
    private Timestamp fechaHora;
    private EstadoTurno estado;

    public Turno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Turno: \n" +
                "ID: " + getId() + "\n" +
                "ID Mascota: " + getIdMascota() + "\n" +
                "ID Veterinario: " + getIdVeterinario() + "\n" +
                "Fecha y hora: " + getFechaHora() + "\n" +
                "Estado: " + getEstado();
    }
}
