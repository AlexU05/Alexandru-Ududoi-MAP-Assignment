package model.expression;

import model.exception.InvalidOperatorException;
import model.value.Value;
import state.SymbolTable;
import model.value.BooleanValue;
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
}
