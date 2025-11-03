package model.adt;

import model.value.Value;
import java.util.ArrayList;

public class List<T extends Value> implements ADT<T> {
    private final java.util.List<T> values = new ArrayList<>();

    public void add(T value) {
        values.add(value);
    }

    public T get(int index) {
        return values.get(index);
    }

    public T remove(int index) {
        return values.remove(index);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public ADT<T> copy() {
        List<T> copy = new List<>();
        copy.values.addAll(values);
        return copy;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}