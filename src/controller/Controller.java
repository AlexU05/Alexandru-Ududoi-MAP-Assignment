package controller;

import model.exception.EmptyExecutionStackException;
import model.exception.InterpreterException;
import model.statement.Statement;
import repository.RepositoryInterface;
import state.ExecutionStack;
import state.ProgramState;

import java.io.IOException;

public class Controller implements ControllerInterface {
    private final RepositoryInterface repositoryInterface;
    private boolean displayFlag;
    public Controller(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
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
    public void allStep(){
        ProgramState programState = repositoryInterface.getProgramState();
        while (!programState.executionStack().isEmpty()) {
            programState = oneStep(programState);
            try {
                var symValues = programState.symbolTable().getContent().values();
                var heapContent = programState.heap().getContent();
                var newHeap = GarbageCollector.safeGarbageCollector(symValues, heapContent);
                programState.heap().setContent(newHeap);
            } catch (Exception e) {
                throw new InterpreterException(e.toString());
            }

            repositoryInterface.setProgramState(programState);
            if(displayFlag) {
                try {
                    repositoryInterface.logProgramState();
                } catch (IOException e) {
                    throw new InterpreterException(e.toString());
                }
                displayCurrentState();
            }
        }
    }

    @Override
    public void displayCurrentState() {
        ProgramState programState = repositoryInterface.getProgramState();
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
