package model.exception;

public class InvalidOperatorException extends InterpreterException {
    public InvalidOperatorException(String operand) {
        super("Invalid operator: " + operand);
    }
}
