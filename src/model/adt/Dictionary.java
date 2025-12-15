package model.adt;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<K, V> implements ADT<V> {
    private final Map<K, V> map = new HashMap<>();

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public V remove(K key) {
        return map.remove(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public ADT<V> copy() {
        Dictionary<K, V> copy = new Dictionary<>();
        copy.map.putAll(map);
        return copy;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
