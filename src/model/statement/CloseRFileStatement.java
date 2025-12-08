package model.statement;

import model.exception.FileNotOpenException;
import model.expression.Expression;
import state.ProgramState;

public record CloseRFileStatement(Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        String fileName = StatementUtil.evaluateToString(expression, state);
        if (!state.fileTable().isOpened(fileName)) {
            throw new FileNotOpenException(fileName);
        }
        state.fileTable().close(fileName);
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }
}
