package Repositorio.Implementaciones;

import Modelo.Implementaciones.ItemBiblioteca;

import java.util.Comparator;

public class OrdenarPorTitulo implements Comparator<ItemBiblioteca> {

    @Override
    public int compare(ItemBiblioteca i1, ItemBiblioteca i2) {
        return i1.getTitulo().compareTo(i2.getTitulo());
    }
}
