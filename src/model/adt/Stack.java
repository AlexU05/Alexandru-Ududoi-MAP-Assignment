package model.adt;

import model.value.Value;
import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<T extends Value> implements ADT<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    public void push(T value) {
        stack.push(value);
    }

    public T pop() {
        return stack.pop();
    }

    public T peek() {
        return stack.peek();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public ADT<T> copy() {
        Stack<T> copy = new Stack<>();
        copy.stack.addAll(stack);
        return copy;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}