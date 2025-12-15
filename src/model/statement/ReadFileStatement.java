package model.statement;

import model.exception.InterpreterException;
import model.type.SimpleType;
import model.type.Type;
import model.exception.FileNotOpenException;
import model.exception.InvalidTypeException;
import model.exception.TypeCheckException;
import model.expression.Expression;
import model.value.IntegerValue;
import state.ProgramState;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.adt.Dictionary;

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

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws TypeCheckException {
        var t = expression.typecheck(typeEnv);
        if (!t.equals(model.type.SimpleType.STRING))
            throw new TypeCheckException("ReadFile: filename must be a string");
        if (!typeEnv.containsKey(variableName))
            throw new TypeCheckException("Variable %s not declared".formatted(variableName));
        if (!typeEnv.get(variableName).equals(SimpleType.INTEGER))
            throw new TypeCheckException("ReadFile: variable must be an integer");
        return typeEnv;
    }
}
