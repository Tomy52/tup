package Repositories.Interfaces;

public interface Repository<T> {
    boolean add(T item);
    String toString();
}
