package model.adt;

import model.value.Value;

public interface ADT<T extends Value> {
    int size();
    boolean isEmpty();
    void clear();
    ADT<T> copy();
}
