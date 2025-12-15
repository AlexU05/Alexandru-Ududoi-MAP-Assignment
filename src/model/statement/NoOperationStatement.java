package model.statement;

import model.exception.TypeCheckException;
import state.ProgramState;
import model.adt.Dictionary;
import model.type.Type;

public class NoOperationStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }
    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        return typeEnv;
    }
}
