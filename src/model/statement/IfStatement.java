package model.statement;

import model.exception.InvalidConditionTypeException;
import model.expression.Expression;
import model.value.BooleanValue;
import model.value.Value;
import state.ProgramState;

public record IfStatement
        (Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InvalidConditionTypeException{
        Value value = condition.evaluate(state.symbolTable());
        if (!(value instanceof BooleanValue booleanValue)) {
            throw new InvalidConditionTypeException("If statement is not a boolean");
        }

        Statement chosenStatement =
                booleanValue.value() ? thenStatement : elseStatement;

        state.executionStack().push(chosenStatement);

        return state;
    }
}
