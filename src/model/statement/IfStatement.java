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

public record IfStatement
        (Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws InvalidConditionTypeException{
        Value value = condition.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof BooleanValue booleanValue)) {
            throw new InvalidConditionTypeException("If statement is not a boolean");
        }

        Statement chosenStatement =
                booleanValue.value() ? thenStatement : elseStatement;

        state.executionStack().push(chosenStatement);

        return null;
    }
    @Override
    public Statement deepCopy() {
        return new IfStatement(condition.deepCopy(),
                thenStatement.deepCopy(),
                elseStatement.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var typeCond = condition.typecheck(typeEnv);
        if (!typeCond.equals(SimpleType.BOOLEAN))
            throw new TypeCheckException("The condition of IF has not the type bool");
        thenStatement.typecheck((Dictionary<String, Type>) typeEnv.copy());
        elseStatement.typecheck((Dictionary<String, Type>) typeEnv.copy());
        return typeEnv;
    }
}
