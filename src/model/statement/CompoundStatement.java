package model.statement;

import state.ExecutionStack;
import state.ProgramState;

public record CompoundStatement
        (Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack executionStack = state.executionStack();
        executionStack.push(second);
        executionStack.push(first);

        return state;
    }
}
