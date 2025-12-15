package model.expression;

import model.exception.TypeCheckException;
import model.exception.VariableNotDefinedException;
import model.type.Type;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.adt.Dictionary;

public record VarExp(String variableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) throws  VariableNotDefinedException {
        if(!symbolTable.isDefined(variableName)){
            throw new VariableNotDefinedException(variableName);
        }
        return symbolTable.getValue(variableName);
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws VariableNotDefinedException {
        return evaluate(symbolTable);
    }

    @Override
    public Expression deepCopy() {
        return new VarExp(variableName);
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        if(!typeEnv.containsKey(variableName))
            throw new TypeCheckException("Variable %s not declared".formatted(variableName));
        return typeEnv.get(variableName);
    }
}
