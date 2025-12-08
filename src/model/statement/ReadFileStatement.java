package model.statement;

import model.exception.InterpreterException;
import model.type.SimpleType;
import model.type.Type;
import model.exception.FileNotOpenException;
import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.value.IntegerValue;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public record ReadFileStatement(Expression expression, String variableName) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        if(!state.symbolTable().isDefined(variableName) ||
                state.symbolTable().getVariableType(variableName) != SimpleType.INTEGER) {
            throw new InvalidTypeException();
        }

        String fileName = StatementUtil.evaluateToString(expression, state);

        if(!state.fileTable().isOpened(fileName)){
            throw new FileNotOpenException(fileName);
        }

        BufferedReader reader = state.fileTable().getFile(fileName);
        try {
            String line = reader.readLine();
            int readValue = line == null ? 0 : Integer.parseInt(line);
            IntegerValue integerValue = new IntegerValue(readValue);
            state.symbolTable().updateValue(variableName, integerValue);
        } catch (IOException e) {
            throw new InterpreterException(e.toString());
        }

        return null;
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }
}
