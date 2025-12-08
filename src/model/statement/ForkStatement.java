package model.statement;

import state.ExecutionStack;
import state.ListExecutionStack;
import state.ProgramState;
import state.SymbolTable;

public record ForkStatement(Statement statement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack newStack = new ListExecutionStack();
        newStack.push(statement.deepCopy());

        SymbolTable newSymTable = state.symbolTable().deepCopy();

        ProgramState newPrg = new ProgramState(
                newStack,
                newSymTable,
                state.out(),
                state.fileTable(),
                state.heap()
        );

        return newPrg;
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }
}

