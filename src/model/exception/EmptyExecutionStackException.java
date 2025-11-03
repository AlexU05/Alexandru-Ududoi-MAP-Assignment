package model.exception;

public class EmptyExecutionStackException extends InterpreterException {
    public EmptyExecutionStackException() {
        super("Execution stack is empty.");
    }
}
