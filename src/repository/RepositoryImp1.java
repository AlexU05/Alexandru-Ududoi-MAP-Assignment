package repository;


import model.statement.Statement;
import state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImp1 implements  Repository {
    private final List<ProgramState> programStates;
    public RepositoryImp1(ProgramState programState) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
    }
    @Override
    public ProgramState getProgramState() {
        return programStates.get(0);
    }
}
