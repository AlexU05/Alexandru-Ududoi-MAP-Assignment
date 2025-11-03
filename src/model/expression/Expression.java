package model.expression;

import model.value.Value;
import state.SymbolTable;

public interface Expression {
    Value evaluate(SymbolTable symbolTable);
}
