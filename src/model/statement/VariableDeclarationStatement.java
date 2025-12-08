package model.statement;

import model.exception.VariableAlreadyDeclaredException;
import model.type.Type;
import state.ProgramState;
import state.SymbolTable;

public record VariableDeclarationStatement(Type type, String variableName) implements Statement {


    @Override
    public ProgramState execute(ProgramState state) throws VariableAlreadyDeclaredException {
        SymbolTable symbolTable = state.symbolTable();
        if (symbolTable.isDefined(variableName)) {
            throw new VariableAlreadyDeclaredException(variableName);
        }

        symbolTable.declareVariable(type, variableName);
        return null;
    }
    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(type, variableName);
    }
}
