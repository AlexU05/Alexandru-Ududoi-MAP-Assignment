package controller;

import model.exception.EmptyExecutionStackException;
import state.ProgramState;

public interface Controller {
    ProgramState oneStep(ProgramState programState) throws EmptyExecutionStackException;
    void allStep();
    void displayCurrentState();
    void setDisplayFlag(boolean flag);
    boolean getDisplayFlag();
}
