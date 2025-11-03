package model.expression;

import model.value.Value;
import state.SymbolTable;
public record ValueExp(Value value) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        return value;
    }
}
