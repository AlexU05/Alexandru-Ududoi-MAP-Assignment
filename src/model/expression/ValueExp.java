package model.expression;

import model.exception.TypeCheckException;
import model.type.Type;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.adt.Dictionary;

public record ValueExp(Value value) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        return value;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        return value.getType();
    }
}
