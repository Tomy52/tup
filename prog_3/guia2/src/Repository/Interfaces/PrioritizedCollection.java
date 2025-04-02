package Repository.Interfaces;

public interface PrioritizedCollection<T> {
    void add(T item, int priorityLevel);
    void remove(T item);
    T peek();
}