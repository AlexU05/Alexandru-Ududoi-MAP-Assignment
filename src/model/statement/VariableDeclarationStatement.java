package model.statement;

import model.exception.TypeCheckException;
import model.exception.VariableAlreadyDeclaredException;
import model.type.Type;
import state.ProgramState;
import state.SymbolTable;
import model.adt.Dictionary;

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

    @Override
    public Dictionary<String, model.type.Type> typecheck(Dictionary<String, model.type.Type> typeEnv) throws TypeCheckException {
        var envCopy = (Dictionary<String, model.type.Type>) typeEnv.copy();
        envCopy.put(variableName, type);
        return envCopy;
    }
}
