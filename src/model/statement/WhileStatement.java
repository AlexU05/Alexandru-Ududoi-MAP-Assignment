package model.statement;

import model.exception.InvalidConditionTypeException;
import model.exception.TypeCheckException;
import model.expression.Expression;
import model.value.BooleanValue;
import model.value.Value;
import model.type.SimpleType;
import state.ProgramState;
import model.adt.Dictionary;
import model.type.Type;

public record WhileStatement(Expression condition, Statement body) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InvalidConditionTypeException {
        Value value = condition.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof BooleanValue booleanValue)) {
            throw new InvalidConditionTypeException("condition exp is not a boolean");
        }

        if (booleanValue.value()) {
            state.executionStack().push(this);
            state.executionStack().push(body);
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(condition.deepCopy(), body.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var typeCond = condition.typecheck(typeEnv);
        if (!typeCond.equals(SimpleType.BOOLEAN))
            throw new TypeCheckException("The condition of WHILE has not the type bool");
        body.typecheck((Dictionary<String, Type>) typeEnv.copy());
        return typeEnv;
    }
}
