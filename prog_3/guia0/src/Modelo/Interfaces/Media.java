package Modelo.Interfaces;

import Modelo.Implementaciones.ItemBiblioteca;

public interface Media extends Comparable<Media>{
    int getId();
    String getTitulo();
    String getCreador();
    String getGenero();

    int compareTo(Media item);
}
