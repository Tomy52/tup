package Repository.Interfaces;

public interface MapCollection<K,V> {
    boolean add(K key, V value);
    V find(K key);
    boolean modify(K key, V newValue);
    boolean delete(K key);
}