package model.exception;

public class DivisionByZeroException extends InterpreterException {
    public DivisionByZeroException() {
        super("Division by zero is not allowed.");
    }
}
