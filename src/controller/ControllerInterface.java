package controller;

import state.ProgramState;
import java.util.List;

public interface ControllerInterface {
    void allStep();
    void displayCurrentState();
    void setDisplayFlag(boolean flag);
    boolean getDisplayFlag();
    void oneStep();
    List<ProgramState> getProgramStates();
}
