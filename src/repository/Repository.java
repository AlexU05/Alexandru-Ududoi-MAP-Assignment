package repository;


import state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {
    private List<ProgramState> programStates;
    private final String logFilePath;
    public Repository(ProgramState programState) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
        logFilePath = "logFile.txt";
    }

    public Repository(ProgramState programState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws IOException {
        PrintWriter printWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(logFilePath, true)));
        printWriter.println(programState.toString());
        printWriter.close();
    }
}
