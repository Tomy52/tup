package Repositorio.Implementaciones;

import Excepciones.IdentificadorDuplicadoException;
import Excepciones.ElementoInexistenteException;
import Modelo.Implementaciones.ItemBiblioteca;
import Repositorio.Interfaces.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;

public class RepositorioBiblioteca implements Repositorio<ItemBiblioteca> {
    private final HashMap<Integer, ItemBiblioteca> biblioteca = new HashMap<>();

    @Override
    public void agregar(ItemBiblioteca item) throws IdentificadorDuplicadoException {
        if (biblioteca.containsKey(item.getId())) throw new IdentificadorDuplicadoException();

        biblioteca.put(item.getId(),item);
    }

    @Override
    public void eliminar(int id) throws ElementoInexistenteException {
        if (biblioteca.containsKey(id)) {
            biblioteca.remove(id);
        } else {
            throw new ElementoInexistenteException("No existe el elemento con el ID especificado!");
        }
    }

    @Override
    public ItemBiblioteca buscarPorId(int id) throws ElementoInexistenteException {
        for (ItemBiblioteca item: biblioteca.values()) {
            if (item.getId() == id) return item;
        }
        throw new ElementoInexistenteException("No existe el elemento con el ID especificado!");
    }

    @Override
    public String filtrarPorGenero(String genero) {
        StringBuilder result = new StringBuilder("Filtrados por genero: " + genero + "\n\n");
        for (ItemBiblioteca item: biblioteca.values()) {
            if (item.getGenero().equals(genero)) {
                result.append(item).append("\n\n");
            }
        }
        return result.toString();
    }

    @Override
    public ArrayList<ItemBiblioteca> ordenarAlfabeticamente() {
        ArrayList<ItemBiblioteca> arr = new ArrayList<>(biblioteca.values());

        arr.sort(new OrdenarPorTitulo());

        return arr;
    }

}
