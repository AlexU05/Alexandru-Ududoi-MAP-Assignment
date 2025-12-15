package model.expression;

import model.exception.TypeCheckException;
import model.type.Type;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.adt.Dictionary;

public interface Expression {
    Value evaluate(SymbolTable symbolTable);
    Value evaluate(SymbolTable symbolTable, Heap heap);
    Expression deepCopy();
    Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException;
}
