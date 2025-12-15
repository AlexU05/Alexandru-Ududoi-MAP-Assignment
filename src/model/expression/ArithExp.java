package model.expression;

import model.exception.DivisionByZeroException;
import model.exception.InvalidOperatorException;
import model.exception.RuntimeInterpreterException;
import model.exception.TypeCheckException;
import model.type.SimpleType;
import model.type.Type;
import model.value.Value;
import state.SymbolTable;
import state.Heap;
import model.value.IntegerValue;
import model.adt.Dictionary;

public record ArithExp(Expression left, Expression right, String operator) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable) throws DivisionByZeroException, InvalidOperatorException, RuntimeInterpreterException {
        IntegerValue leftValue = (IntegerValue) left.evaluate(symbolTable);
        IntegerValue rightValue = (IntegerValue) right.evaluate(symbolTable);

        if(leftValue.getType() != rightValue.getType()){
            throw new RuntimeInterpreterException("Invalid operand types for arithmetic expression");
        }
        if(operator.equals("/") && rightValue.value()  == 0 ) throw new DivisionByZeroException();
        else
            return switch (operator) {
                case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
                case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
                case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
                case "/" -> new IntegerValue(leftValue.value() / rightValue.value());
                default -> throw new InvalidOperatorException(operator);
            };

    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws DivisionByZeroException, InvalidOperatorException, RuntimeInterpreterException {
        IntegerValue leftValue = (IntegerValue) left.evaluate(symbolTable, heap);
        IntegerValue rightValue = (IntegerValue) right.evaluate(symbolTable, heap);

        if(leftValue.getType() != rightValue.getType()){
            throw new RuntimeInterpreterException("Invalid operand types for arithmetic expression");
        }
        if(operator.equals("/") && rightValue.value()  == 0 ) throw new DivisionByZeroException();
        else
            return switch (operator) {
                case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
                case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
                case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
                case "/" -> new IntegerValue(leftValue.value() / rightValue.value());
                default -> throw new InvalidOperatorException(operator);
            };
    }

    @Override
    public Expression deepCopy() {
        return new ArithExp(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public Type typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        Type t1 = left.typecheck(typeEnv);
        Type t2 = right.typecheck(typeEnv);
        if (!t1.equals(SimpleType.INTEGER))
            throw new TypeCheckException("First operand is not an integer");
        if (!t2.equals(SimpleType.INTEGER))
            throw new TypeCheckException("Second operand is not an integer");
        return SimpleType.INTEGER;
    }
}
