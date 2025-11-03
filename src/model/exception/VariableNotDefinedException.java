package model.exception;

public class VariableNotDefinedException extends InterpreterException {
    public VariableNotDefinedException(String variableName) {
        super("Variable '" + variableName + "' is not defined.");
    }
}
