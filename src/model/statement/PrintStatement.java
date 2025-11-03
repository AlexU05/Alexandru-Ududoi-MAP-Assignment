package model.statement;

import model.expression.Expression;
import model.value.Value;
import state.ProgramState;

public record PrintStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.evaluate(state.symbolTable());
        state.out().append(value);

        return state;
    }
}
