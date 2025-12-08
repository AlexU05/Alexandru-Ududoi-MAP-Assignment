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

public record HeapDeclarationStatement(String variableName, Expression expression) implements Statement {

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

        Value evaluatedValue = expression.evaluate(symbolTable, heap);

        var locationType = refType.getInner();

        if (!evaluatedValue.getType().equals(locationType)) {
            throw new InvalidTypeException("Expression type does not match the reference's location type. " +
                    "Expected: " + locationType + ", Got: " + evaluatedValue.getType());
        }

        int address = heap.allocate(evaluatedValue);

        ReferenceValue newRefValue = new ReferenceValue(address, locationType);
        symbolTable.updateValue(variableName, newRefValue);

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new HeapDeclarationStatement(variableName, expression.deepCopy());
    }
}
