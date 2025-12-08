package repository;

import state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface {
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> programStates);
    void logPrgStateExec(ProgramState programState) throws IOException;
}
