package Model.Interface;

import java.util.List;

public interface Dao<T> {
    void agregar(T item);
    void eliminar(int itemId);
    List<T> obtenerTodos();
}
