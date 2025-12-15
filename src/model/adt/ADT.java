package model.adt;

public interface ADT<T> {
    int size();
    boolean isEmpty();
    void clear();
    ADT<T> copy();
}
