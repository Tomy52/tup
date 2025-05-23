package Model.Interface;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void agregar(T t);
    Optional<T> obtenerUno(int id);
    List<T> obtenerTodos();
    void modificar(int id, T new_data);
    void eliminar(int id);
}
