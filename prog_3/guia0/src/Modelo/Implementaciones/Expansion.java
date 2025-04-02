package Modelo.Implementaciones;

import java.time.LocalDate;
import java.util.Objects;

public class Expansion extends ItemBiblioteca {
    private LocalDate fechaLanzamiento;

    public Expansion(int id, String genero, String creador, String titulo, LocalDate fechaLanzamiento) {
        super(id, genero, creador, titulo);
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public String toString() {
        return super.toString() + "\nFecha de lanzamiento: " + this.fechaLanzamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expansion expansion)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(fechaLanzamiento, expansion.fechaLanzamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaLanzamiento);
    }
}
