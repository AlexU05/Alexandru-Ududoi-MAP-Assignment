package model.statement;

import model.exception.FileAlreadyOpenException;
import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.value.StringValue;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static model.statement.StatementUtil.evaluateToString;

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
}
