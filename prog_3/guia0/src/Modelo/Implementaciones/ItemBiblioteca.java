package Modelo.Implementaciones;

import Modelo.Interfaces.Media;

import java.util.Objects;

public abstract class ItemBiblioteca implements Media {
    protected String titulo;
    protected String creador;
    protected String genero;
    protected final int id;

    public ItemBiblioteca(int id, String genero, String creador, String titulo) {
        this.id = id;
        this.genero = genero;
        this.creador = creador;
        this.titulo = titulo;
    }

    @Override
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Titulo: " + titulo + "\n" +
                "Creador: " + creador + "\n" +
                "Genero: " + genero;
    }

    @Override
    public int compareTo(Media item) {
        return this.titulo.compareTo(item.getTitulo());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ItemBiblioteca item)) return false;
        return id == item.id && Objects.equals(titulo, item.titulo) && Objects.equals(creador, item.creador) && Objects.equals(genero, item.genero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, creador, genero, id);
    }
}
