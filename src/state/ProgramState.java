package state;

import model.exception.EmptyExecutionStackException;
import model.statement.Statement;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private final ExecutionStack executionStack;
    private final SymbolTable symbolTable;
    private final Out out;
    private final FileTable fileTable;
    private final Heap heap;
    private final int id;

    private static final AtomicInteger lastId = new AtomicInteger(0);

    private static synchronized int getNextId() {
        return lastId.incrementAndGet();
    }

    public ProgramState(ExecutionStack executionStack,
                        SymbolTable symbolTable,
                        Out out,
                        FileTable fileTable,
                        Heap heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNextId();
    }

    public ExecutionStack executionStack() { return executionStack; }
    public SymbolTable symbolTable() { return symbolTable; }
    public Out out() { return out; }
    public FileTable fileTable() { return fileTable; }
    public Heap heap() { return heap; }
    public int id() { return id; }

    @Override
    public String toString() {
        return "\nProgramState (id=" + id + "): " +
                "\nexecutionStack: " + executionStack + ", " +
                "\nsymbolTable: " + symbolTable + ", " +
                "\nout: " + out +
                "\nfileTable: " + fileTable +
                "\nheap: " + heap + "\n";

    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws EmptyExecutionStackException {
        if (executionStack.isEmpty()) {
            throw new EmptyExecutionStackException();
        }
        Statement crtStmt = executionStack.pop();
        return crtStmt.execute(this);
    }
}
