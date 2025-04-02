package Repositorio.Interfaces;

import Excepciones.ElementoInexistenteException;
import Excepciones.IdentificadorDuplicadoException;

import java.util.ArrayList;

public interface Repositorio<T> {
    void agregar(T t) throws IdentificadorDuplicadoException;
    T buscarPorId(int id) throws ElementoInexistenteException;
    void eliminar(int id) throws ElementoInexistenteException;
    ArrayList<T> ordenarAlfabeticamente();
    String filtrarPorGenero(String genero);
}
