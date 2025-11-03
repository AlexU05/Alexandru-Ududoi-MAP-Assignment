package model.statement;

import state.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState state);
}
