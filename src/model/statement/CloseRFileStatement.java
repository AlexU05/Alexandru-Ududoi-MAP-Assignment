package model.statement;

import model.exception.FileNotOpenException;
import model.exception.TypeCheckException;
import model.expression.Expression;
import state.ProgramState;
import model.adt.Dictionary;
import model.type.Type;

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

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var t = expression.typecheck(typeEnv);
        if (!t.equals(model.type.SimpleType.STRING))
            throw new TypeCheckException("CloseRFile: expression must be a string");
        return typeEnv;
    }
}
