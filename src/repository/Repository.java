package repository;

import model.statement.Statement;
import state.ProgramState;

public interface Repository {
    ProgramState getProgramState();
}
