package model.exception;

public class EmptyCollectionException extends InterpreterException {
    public EmptyCollectionException() {
        super("Cannot read from an empty collection.");
    }
}
