package model.exception;

public class VariableAlreadyDeclaredException extends InterpreterException {
    public VariableAlreadyDeclaredException(String variableName) {
        super("Variable '" + variableName + "' is already declared.");
    }
}
