package state;

import model.statement.Statement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListExecutionStack implements ExecutionStack {

    private final LinkedList<Statement> statements = new LinkedList<>();

    @Override
    public void push(Statement statement) {
        statements.addFirst(statement);
    }

    @Override
    public Statement pop() {
        return statements.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return statements.isEmpty();
    }

    @Override
    public List<Statement> getAll() {
        return new ArrayList<>(statements);
    }

    @Override
    public String toString() {
        return "ExeStack" + statements.toString();
    }

}
