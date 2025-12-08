package model.statement;

import model.exception.InvalidTypeException;
import model.exception.RuntimeInterpreterException;
import model.exception.VariableNotDefinedException;
import model.expression.Expression;
import model.type.ReferenceType;
import model.value.ReferenceValue;
import model.value.Value;
import state.ProgramState;
import state.SymbolTable;
import state.Heap;

public record HeapWriteStatement(String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws VariableNotDefinedException, InvalidTypeException, RuntimeInterpreterException {
        SymbolTable symbolTable = state.symbolTable();
        Heap heap = state.heap();

        if (!symbolTable.isDefined(variableName)) {
            throw new VariableNotDefinedException(variableName);
        }

        var variableType = symbolTable.getVariableType(variableName);
        if (!(variableType instanceof ReferenceType refType)) {
            throw new InvalidTypeException("Variable '" + variableName + "' is not of reference type");
        }

        Value varValue = symbolTable.getValue(variableName);
        if (!(varValue instanceof ReferenceValue refValue)) {
            throw new RuntimeInterpreterException("Variable '" + variableName + "' does not contain a valid reference value");
        }

        int address = refValue.getAddr();

        if (!heap.contains(address)) {
            throw new RuntimeInterpreterException("Address " + address + " is not in the heap");
        }

        Value evaluatedValue = expression.evaluate(symbolTable, heap);

        var locationType = refType.getInner();

        if (!evaluatedValue.getType().equals(locationType)) {
            throw new InvalidTypeException("Expression type does not match the reference's location type. " +
                    "Expected: " + locationType + ", Got: " + evaluatedValue.getType());
        }

        heap.update(address, evaluatedValue);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new HeapWriteStatement(variableName, expression.deepCopy());
    }
}
