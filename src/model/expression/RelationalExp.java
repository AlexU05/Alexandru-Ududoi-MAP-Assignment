package model.expression;

import model.exception.InvalidOperatorException;
import model.exception.InvalidTypeException;
import model.exception.TypeCheckException;
import model.type.SimpleType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.adt.Dictionary;

public record RelationalExp(Expression left, Expression right, String operator) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable) throws InvalidTypeException, InvalidOperatorException {
        Value leftValue = left.evaluate(symbolTable);
        Value rightValue = right.evaluate(symbolTable);
        if (!(leftValue instanceof IntegerValue) || !(rightValue instanceof IntegerValue))
            throw new InvalidTypeException("RelationalExp requires integer values");
        return switch (operator) {
            case "<" -> new BooleanValue(((IntegerValue) leftValue).value() < ((IntegerValue) rightValue).value());
            case "<=" -> new BooleanValue(((IntegerValue) leftValue).value() <= ((IntegerValue) rightValue).value());
            case "==" -> leftValue.equals(rightValue);
            case "!=" -> new BooleanValue(!leftValue.equals(rightValue).value());
            case ">" -> new BooleanValue(((IntegerValue) leftValue).value() > ((IntegerValue) rightValue).value());
            case ">=" -> new BooleanValue(((IntegerValue) leftValue).value() >= ((IntegerValue) rightValue).value());
            default -> throw new InvalidOperatorException(operator);
        };
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws InvalidTypeException, InvalidOperatorException {
        Value leftValue = left.evaluate(symbolTable, heap);
        Value rightValue = right.evaluate(symbolTable, heap);
        if (!(leftValue instanceof IntegerValue) || !(rightValue instanceof IntegerValue))
            throw new InvalidTypeException("RelationalExp requires integer values");
        return switch (operator) {
            case "<" -> new BooleanValue(((IntegerValue) leftValue).value() < ((IntegerValue) rightValue).value());
            case "<=" -> new BooleanValue(((IntegerValue) leftValue).value() <= ((IntegerValue) rightValue).value());
            case "==" -> leftValue.equals(rightValue);
            case "!=" -> new BooleanValue(!leftValue.equals(rightValue).value());
            case ">" -> new BooleanValue(((IntegerValue) leftValue).value() > ((IntegerValue) rightValue).value());
            case ">=" -> new BooleanValue(((IntegerValue) leftValue).value() >= ((IntegerValue) rightValue).value());
            default -> throw new InvalidOperatorException(operator);
        };
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExp(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        Type t1 = left.typecheck(typeEnv);
        Type t2 = right.typecheck(typeEnv);
        if (!t1.equals(SimpleType.INTEGER))
            throw new TypeCheckException("First operand is not an integer");
        if (!t2.equals(SimpleType.INTEGER))
            throw new TypeCheckException("Second operand is not an integer");
        return SimpleType.BOOLEAN;
    }
}
