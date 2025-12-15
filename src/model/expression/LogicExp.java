package model.expression;

import model.exception.InvalidOperatorException;
import model.exception.TypeCheckException;
import model.type.SimpleType;
import model.type.Type;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.value.BooleanValue;
import model.adt.Dictionary;

public record LogicExp( Expression left,Expression right, String operator) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable) throws  InvalidOperatorException {
        Value leftValue = left.evaluate(symbolTable);
        Value rightValue = right.evaluate(symbolTable);

        if(leftValue instanceof BooleanValue(boolean value1) && rightValue instanceof BooleanValue(boolean value2)){

            return switch (operator){
                case "&&" -> new BooleanValue(value1 && value2);
                case "||" -> new BooleanValue(value1 || value2);
                default -> throw new InvalidOperatorException(operator);
            };
        } else {
            throw new InvalidOperatorException(operator);
        }
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws InvalidOperatorException {
        Value leftValue = left.evaluate(symbolTable, heap);
        Value rightValue = right.evaluate(symbolTable, heap);

        if(leftValue instanceof BooleanValue(boolean value1) && rightValue instanceof BooleanValue(boolean value2)){

            return switch (operator){
                case "&&" -> new BooleanValue(value1 && value2);
                case "||" -> new BooleanValue(value1 || value2);
                default -> throw new InvalidOperatorException(operator);
            };
        } else {
            throw new InvalidOperatorException(operator);
        }
    }

    @Override
    public Expression deepCopy() {
        return new LogicExp(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        Type t1 = left.typecheck(typeEnv);
        Type t2 = right.typecheck(typeEnv);
        if (!t1.equals(SimpleType.BOOLEAN))
            throw new TypeCheckException("First operand is not a boolean");
        if (!t2.equals(SimpleType.BOOLEAN))
            throw new TypeCheckException("Second operand is not a boolean");
        return SimpleType.BOOLEAN;
    }
}
