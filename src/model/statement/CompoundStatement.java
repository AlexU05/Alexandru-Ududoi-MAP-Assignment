package model.statement;

import model.exception.TypeCheckException;
import state.ExecutionStack;
import state.ProgramState;
import model.adt.Dictionary;
import model.type.Type;

public record CompoundStatement
        (Statement first, Statement second) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStack executionStack = state.executionStack();
        executionStack.push(second);
        executionStack.push(first);

        return null;
    }
    @Override
    public Statement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var env1 = first.typecheck(typeEnv);
        return second.typecheck(env1);
    }
}
