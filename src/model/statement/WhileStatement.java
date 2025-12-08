package model.statement;

import model.exception.InvalidConditionTypeException;
import model.expression.Expression;
import model.value.BooleanValue;
import model.value.Value;
import state.ProgramState;

public record WhileStatement(Expression condition, Statement body) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InvalidConditionTypeException {
        Value value = condition.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof BooleanValue booleanValue)) {
            throw new InvalidConditionTypeException("condition exp is not a boolean");
        }

        if (booleanValue.value()) {
            state.executionStack().push(this);
            state.executionStack().push(body);
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(condition.deepCopy(), body.deepCopy());
    }
}
