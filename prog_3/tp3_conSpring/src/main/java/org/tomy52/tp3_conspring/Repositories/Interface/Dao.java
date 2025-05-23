package org.tomy52.tp3_conspring.Repositories.Interface;

import java.util.List;

public interface Dao<T> {
    void agregar(T item);
    void eliminar(int itemId);
    List<T> obtenerTodos();
}
