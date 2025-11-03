package model.expression;

import model.exception.DivisionByZeroException;
import model.exception.InvalidOperatorException;
import model.exception.RuntimeInterpreterException;
import model.value.Value;
import state.SymbolTable;
import model.value.IntegerValue;

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
}
