package Modelo.Implementaciones;

import java.util.Objects;

public class Juego extends ItemBiblioteca {
    private double nroVersion;

    public Juego(int id, String genero, String creador, String titulo, double nroVersion) {
        super(id, genero, creador, titulo);
        this.nroVersion = nroVersion;
    }

    public double getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(double nroVersion) {
        this.nroVersion = nroVersion;
    }

    @Override
    public String toString() {
        return super.toString() + "\nNumero de version: " + this.nroVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Juego juego)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(nroVersion, juego.nroVersion) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nroVersion);
    }
}
