package model.statement;

import model.exception.InvalidAssignmentException;
import model.exception.VariableNotDefinedException;
import model.expression.Expression;
import model.value.Value;
import state.ProgramState;
import state.SymbolTable;

public record AssignmentStatement
        (String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws VariableNotDefinedException {
        SymbolTable symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new VariableNotDefinedException(variableName);
        }

        Value value = expression.evaluate(symbolTable, state.heap());
        if (!value.getType().equals(symbolTable.getVariableType(variableName))) {
            throw new InvalidAssignmentException("Variable " + variableName + " is not assignable to " +
                    symbolTable.getVariableType(variableName));
        }

        symbolTable.updateValue(variableName, value);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(variableName, expression.deepCopy());
    }
}
