package controller;

import model.exception.EmptyExecutionStackException;
import model.statement.Statement;
import repository.Repository;
import state.ExecutionStack;
import state.ProgramState;

public class ControllerImp1 implements Controller {
    private final Repository repository;
    private boolean displayFlag;
    public ControllerImp1(Repository repository) {
        this.repository = repository;
        this.displayFlag = true;
    }

    @Override
    public ProgramState oneStep(ProgramState programState) throws EmptyExecutionStackException {
        ExecutionStack executionStack = programState.executionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyExecutionStackException();
        }
        Statement statement = executionStack.pop();
        return statement.execute(programState);
    }

    @Override
    public void allStep() {
        ProgramState programState = repository.getProgramState();
        while (!programState.executionStack().isEmpty()) {
            oneStep(programState);
            if(displayFlag) {
                displayCurrentState();
            }
        }
    }

    @Override
    public void displayCurrentState() {
        ProgramState programState = repository.getProgramState();
        System.out.println(programState + "\n");
    }

    @Override
    public void setDisplayFlag(boolean flag) {
        displayFlag = flag;
    }

    @Override
    public boolean getDisplayFlag() {
        return displayFlag;
    }
}
