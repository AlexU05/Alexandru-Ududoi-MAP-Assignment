package state;

import model.statement.Statement;

public record ProgramState(
        ExecutionStack executionStack,
        SymbolTable symbolTable,
        Out out) {

    @Override
    public String toString() {
        return "\nProgramState: \nexecutionStack=" + executionStack + ", \nsymbolTable=" + symbolTable + ", \nout=" + out;
    }
}
