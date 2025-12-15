package model.statement;

import model.exception.TypeCheckException;
import state.ProgramState;
import model.adt.Dictionary;
import model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state);
    Statement deepCopy();
    Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException;
}
