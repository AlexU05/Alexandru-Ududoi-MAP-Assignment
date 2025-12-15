package model.statement;

import model.exception.FileAlreadyOpenException;
import model.exception.InvalidTypeException;
import model.exception.TypeCheckException;
import model.expression.Expression;
import model.value.StringValue;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static model.statement.StatementUtil.evaluateToString;
import model.adt.Dictionary;
import model.type.Type;

public record OpenRFileStatement(Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        String fileName = evaluateToString(expression, state);

        if(state.fileTable().isOpened(fileName))
            throw new FileAlreadyOpenException(fileName);

        try {
            var br = new BufferedReader(new FileReader(fileName));
            state.fileTable().add(fileName, br);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var t = expression.typecheck(typeEnv);
        if (!t.equals(model.type.SimpleType.STRING))
            throw new TypeCheckException("OpenRFile: expression must be a string");
        return typeEnv;
    }
}
